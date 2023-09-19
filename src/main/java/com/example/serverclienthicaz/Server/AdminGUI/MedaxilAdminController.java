package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.AnbarGUI.AnbarClient;
import com.example.serverclienthicaz.Server.AnbarGUI.MedaxilinFoController;
import com.example.serverclienthicaz.Server.AnbarGUI.NewMedaxilController;
import com.example.serverclienthicaz.Server.ModelAnbar.Medaxil;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MedaxilAdminController {
    @FXML
    private AnchorPane medaxilPanel;
    @FXML
    private TableView<Medaxil> medaxilView;
    @FXML
    private TableColumn<Medaxil,String> Nr;
    @FXML
    private TableColumn<Medaxil,String> Tarix;
    @FXML
    private TableColumn<Medaxil,String> Kreditor;
    @FXML
    private TableColumn<Medaxil,String> Mebleg;

    @FXML
    private TextField search;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ToggleButton toggleButton;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    private AnchorPane Pane;



    private static ObservableList<Medaxil> medaxilObservableList= FXCollections.observableArrayList();

    private static List<Medaxil> medaxilList= new ArrayList<>();

    private Client client;



    public void init(Client client) {
        this.client = client;
        Nr.setCellValueFactory(new PropertyValueFactory<>("Nr"));
        Tarix.setCellValueFactory(new PropertyValueFactory<>("Tarix"));
        Kreditor.setCellValueFactory(new PropertyValueFactory<>("Kreditor"));
        Mebleg.setCellValueFactory(new PropertyValueFactory<>("Mebleg"));
        initMedaxilList();
        medaxilView.setItems(medaxilObservableList);
        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> showMedaxilBetweenDates());
        endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> showMedaxilBetweenDates());
        toggleButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            showTodaysSales(newValue);
        });


    }


    public void initMedaxilList(){
        client.sendMessage("GETQUERYMEDAXIL");
        medaxilList.clear();
        medaxilObservableList.clear();
        medaxilList=(List<Medaxil>) client.listReader();
        for (Medaxil medaxil: medaxilList){
            medaxilObservableList.add(medaxil);
        }
    }

    public void handleKeyReleased(KeyEvent event){
        if (event.getEventType() == KeyEvent.KEY_RELEASED){
            String name = search.getText();

            Search(medaxilObservableList,name);
        }
    }
    private void Search(ObservableList<Medaxil> medaxilObservableList, String name) {
        medaxilObservableList.clear();
        String lowercaseName = name.toLowerCase();
        for (int i = 0; i < medaxilList.size(); i++) {
            if (medaxilList.get(i).getKreditor().toLowerCase().contains(lowercaseName)) {
                medaxilObservableList.add(medaxilList.get(i));
            }
        }
    }
    public void showMedaxilBetweenDates() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (startDate != null && endDate != null) {
            try {
                Date startDateObj = dateFormatter.parse(startDate.toString());
                Date endDateObj = dateFormatter.parse(endDate.toString());

                List<Medaxil> filteredSales = medaxilList.stream()
                        .filter(medaxil -> {
                            try {
                                Date paymentDate = dateFormatter.parse(medaxil.getTarix());
                                return (paymentDate.equals(startDateObj) || paymentDate.after(startDateObj))
                                        && (paymentDate.before(endDateObj) || paymentDate.equals(endDateObj));
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return false;
                            }
                        })
                        .collect(Collectors.toList());

                medaxilObservableList.clear();

                medaxilObservableList.addAll(filteredSales);
            } catch (Exception e){
                System.out.println(e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Xəta");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    public void showTodaysSales(boolean showTodaySales) {
        try {
            LocalDate today = LocalDate.now();
            medaxilObservableList.clear();
            if (showTodaySales) {
                List<Medaxil> filteredSales = medaxilList.stream()
                        .filter(medaxil -> {
                            try {
                                Date saleDate = dateFormatter.parse(medaxil.getTarix());
                                LocalDate saleLocalDate = saleDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                return saleLocalDate.isEqual(today);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return false;
                            }
                        })
                        .collect(Collectors.toList());

                medaxilObservableList.addAll(filteredSales);
            } else {
                medaxilObservableList.addAll(medaxilList);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert  alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    private void setNode(Node node) {
        medaxilPanel.getChildren().clear();
        medaxilPanel.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }

    public void createNewMedaxil(){
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/NewMedaxilAdmin.fxml"));
            Pane = loader.load();
            NewMedaxilAdminController controller= loader.getController();
            controller.init(client);
            setNode(Pane);
        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    public void  deleteItem(Medaxil medaxil){
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mədaxili silmək");
            alert.setHeaderText("Kreditor adı: " + medaxil.getKreditor() + "\n Məbləğ: " + medaxil.getMebleg());
            alert.setContentText("Əminsiniz?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                client.sendMessage("DELETEMEDAXIL");
                client.sendMessage(String.valueOf(medaxil.getNr()));
                initMedaxilList();
                return;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert  alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    public void handleKeyPressed(KeyEvent keyEvent){
        Medaxil selectedItem = medaxilView.getSelectionModel().getSelectedItem();
        if (selectedItem!=null){
            if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)){
                deleteItem(selectedItem);
            }
        }
    }
    public void handlePrintButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Table Data");

                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Nr");
                headerRow.createCell(1).setCellValue("Tarix");
                headerRow.createCell(2).setCellValue("Kreditor");
                headerRow.createCell(3).setCellValue("Mebleg");

                int rowIndex = 1;
                for (Medaxil medaxil : medaxilObservableList) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(medaxil.getNr());
                    row.createCell(1).setCellValue(medaxil.getTarix());
                    row.createCell(2).setCellValue(medaxil.getKreditor());
                    row.createCell(3).setCellValue(medaxil.getMebleg());
                }

                for (int i = 0; i < 4; i++) {
                    sheet.autoSizeColumn(i);
                }

                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    workbook.write(fileOut);
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Print");
                alert.setHeaderText(null);
                alert.setContentText("Table data printed to Excel successfully.");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("An error occurred while printing table data to Excel.");
                alert.showAndWait();
            }
        }
    }
    public void handleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            if (medaxilView.getSelectionModel().getSelectedItem() != null) {
                Medaxil selectedItem = medaxilView.getSelectionModel().getSelectedItem();
                MedaxilInfoAdmin medaxilinFoController = new MedaxilInfoAdmin();
                medaxilinFoController.setMedaxil(selectedItem);
                createMedaxilInfo(medaxilinFoController);
            }
        }
    }
    public void createMedaxilInfo(MedaxilInfoAdmin controller) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AnbarFxml/MedaxilInfo.fxml"));
            loader.setController(controller);
            Parent root = loader.load();
            controller.init(client);
            setNode(root);
        } catch (Exception e){
            System.out.println(e.getMessage());
            Alert  alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }



}
