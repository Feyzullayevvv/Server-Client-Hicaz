package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.ModelProses.Tehvil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TehvilController {
    @FXML
    private AnchorPane tehvilPane;
    @FXML
    private TableView<Tehvil> tehvilTableView;
    @FXML
    private TableColumn<Tehvil,String> nr;
    @FXML
    private TableColumn<Tehvil,String> tarix;
    @FXML
    private TableColumn<Tehvil,String> tehvilci;
    @FXML
    private TableColumn<Tehvil,String> mexaricNr;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ToggleButton toggleButton;
    private Client client;


    private ObservableList<Tehvil> tehvilObservableList = FXCollections.observableArrayList();
    private List<Tehvil> tehvilList = new ArrayList<>();
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");



    public void init(){
        nr.setCellValueFactory(new PropertyValueFactory<>("nr"));
        tarix.setCellValueFactory(new PropertyValueFactory<>("Date"));
        tehvilci.setCellValueFactory(new PropertyValueFactory<>("tehvilci"));
        mexaricNr.setCellValueFactory(new PropertyValueFactory<>("mexaricNr"));
        populate();
        toggleButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            showTodaysTehvil(newValue);
        });
        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> shoowTehvilBetweenDates());
        endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> shoowTehvilBetweenDates());
    }

    private void populate(){
        tehvilObservableList.clear();
        tehvilList.clear();
        client.sendMessage("GETTEHVILLIST");
        tehvilList= (List<Tehvil>) client.objectReader();
        for (Tehvil tehvil : tehvilList){
            tehvilObservableList.add(tehvil);
        }
        tehvilTableView.setItems(tehvilObservableList);
    }
    public void showTodaysTehvil(boolean showTodaySales) {
        try {
            LocalDate today = LocalDate.now();
            tehvilObservableList.clear();
            if (showTodaySales) {
                List<Tehvil> filteredSales = tehvilList.stream()
                        .filter(tehvil -> {
                            try {
                                Date saleDate = dateFormatter.parse(tehvil.getDate());
                                LocalDate saleLocalDate = saleDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                return saleLocalDate.isEqual(today);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return false;
                            }
                        })
                        .collect(Collectors.toList());

                tehvilObservableList.addAll(filteredSales);
            } else {
                tehvilObservableList.addAll(tehvilList);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void shoowTehvilBetweenDates() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (startDate != null && endDate != null) {
            try {
                Date startDateObj = dateFormatter.parse(startDate.toString());
                Date endDateObj = dateFormatter.parse(endDate.toString());

                List<Tehvil> filteredSales = tehvilList.stream()
                        .filter(tehvil -> {
                            try {
                                Date paymentDate = dateFormatter.parse(tehvil.getDate());
                                return (paymentDate.equals(startDateObj) || paymentDate.after(startDateObj))
                                        && (paymentDate.before(endDateObj) || paymentDate.equals(endDateObj));
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return false;
                            }
                        })
                        .collect(Collectors.toList());

                tehvilObservableList.clear();

                tehvilObservableList.addAll(filteredSales);
            } catch (Exception e){
                System.out.println(e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Xəta");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
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
                headerRow.createCell(2).setCellValue("Tehvil Alan");


                int rowIndex = 1;
                for (Tehvil tehvil : tehvilObservableList) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(tehvil.getNr());
                    row.createCell(1).setCellValue(tehvil.getDate());
                    row.createCell(2).setCellValue(tehvil.getTehvilci());

                }

                for (int i = 0; i < 3; i++) {
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
            if (tehvilTableView.getSelectionModel().getSelectedItem() != null) {
                Tehvil selectedItem = tehvilTableView.getSelectionModel().getSelectedItem();
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(tehvilPane.getScene().getWindow());
                dialog.setTitle("Information");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/TehvilInfoDialogAdmin.fxml"));
                try {
                    dialog.getDialogPane().setContent(fxmlLoader.load());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                dialog.setResizable(false);
                TehvilInfoDialogAdmin controller = fxmlLoader.getController();
                controller.init(client,selectedItem);
                dialog.show();

                populate();

            }
        }
    }
    public void  deleteItem(Tehvil tehvil){
        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Məxarici silmək");
            alert.setHeaderText("Tarix: " + tehvil.getDate()+ "\nTəhvil alan : " + tehvil.getTehvilci());
            alert.setContentText("Əminsiniz?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                client.sendMessage("DELETETEHVIL");
                client.sendMessage(String.valueOf(tehvil.getNr()));
                client.sendMessage(String.valueOf(tehvil.getMexaricNr()));
                init();
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
        Tehvil selectedItem = tehvilTableView.getSelectionModel().getSelectedItem();
        if (selectedItem!=null){
            if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)){
                deleteItem(selectedItem);
            }
        }
    }

    public void setClient(Client client) {
        this.client = client;
    }


}
