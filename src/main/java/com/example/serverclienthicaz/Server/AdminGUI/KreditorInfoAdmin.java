package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.AnbarGUI.AnbarClient;
import com.example.serverclienthicaz.Server.AnbarGUI.KreditorController;
import com.example.serverclienthicaz.Server.ModelAnbar.Kreditor;
import com.example.serverclienthicaz.Server.ModelAnbar.MedaxilFaktura;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class KreditorInfoAdmin {
    @FXML
    private AnchorPane kreditorInfoPane;
    @FXML
    private TextField kreditorNameTextField;

    @FXML
    private TableView<MedaxilFaktura> kreditorInfoView;
    @FXML
    private TableColumn<MedaxilFaktura,String> tarix;
    @FXML
    private TableColumn<MedaxilFaktura,String> xammalMal;
    @FXML
    private TableColumn<MedaxilFaktura,String> vahid;
    @FXML
    private TableColumn<MedaxilFaktura,String> ceki;
    @FXML
    private TableColumn<MedaxilFaktura,String> mebleg;
    @FXML
    private TableColumn<MedaxilFaktura,String> medaxilNr;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    @FXML
    private TextField search;
    private static ObservableList<MedaxilFaktura> medaxilFakturaObservableList = FXCollections.observableArrayList();
    private static List<MedaxilFaktura> medaxilFakturaList = new ArrayList<>();

    private Pane pane;
    private Client client;
    private Kreditor kreditor;



    public void init(Client client) {
        this.client = client;
        kreditorNameTextField.setText(kreditor.getName());
        tarix.setCellValueFactory(new PropertyValueFactory<>("tarix"));
        xammalMal.setCellValueFactory(new PropertyValueFactory<>("Mal"));
        vahid.setCellValueFactory(new PropertyValueFactory<>("Vahid"));
        ceki.setCellValueFactory(new PropertyValueFactory<>("Ceki"));
        mebleg.setCellValueFactory(new PropertyValueFactory<>("Mebleg"));
        medaxilNr.setCellValueFactory(new PropertyValueFactory<>("MedaxilNr"));
        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> showMedaxilBetweenDates());
        endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> showMedaxilBetweenDates());
        initList();
    }

    public void setKreditor(Kreditor kreditor){
        this.kreditor =kreditor;
    }

    public void handleBack(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/KreditorAdmin.fxml"));
            pane = loader.load();
            KreditorAdmin controller = loader.getController();
            controller.init(client);
            setNode(pane);
        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void handleKeyReleased(KeyEvent event){
        if (event.getEventType() == KeyEvent.KEY_RELEASED){
            String name = search.getText();

            Search(medaxilFakturaObservableList,name);
        }
    }
    private void Search(ObservableList<MedaxilFaktura> medaxilFakturaObservableList, String name) {
        medaxilFakturaObservableList.clear();
        String lowercaseName = name.toLowerCase();
        for (int i = 0; i < medaxilFakturaList.size(); i++) {
            if (medaxilFakturaList.get(i).getMal().toLowerCase().contains(lowercaseName)) {
                medaxilFakturaObservableList.add(medaxilFakturaList.get(i));
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
                headerRow.createCell(0).setCellValue("Tarix");
                headerRow.createCell(1).setCellValue("Mal");
                headerRow.createCell(2).setCellValue("Vahid");
                headerRow.createCell(3).setCellValue("Ceki");
                headerRow.createCell(4).setCellValue("Mebleg");
                headerRow.createCell(5).setCellValue("Medaxil Nr");

                int rowIndex = 1;
                for (MedaxilFaktura medaxilFaktura : medaxilFakturaObservableList) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(medaxilFaktura.getTarix());
                    row.createCell(1).setCellValue(medaxilFaktura.getMal());
                    row.createCell(2).setCellValue(medaxilFaktura.getVahid());
                    row.createCell(3).setCellValue(medaxilFaktura.getCeki());
                    row.createCell(4).setCellValue(medaxilFaktura.getMebleg());
                    row.createCell(5).setCellValue(medaxilFaktura.getMedaxilNr());
                }

                for (int i = 0; i < 6; i++) {
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
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("An error occurred while printing table data to Excel.");
                alert.showAndWait();
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

                List<MedaxilFaktura> filteredSales = medaxilFakturaList.stream()
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

                medaxilFakturaObservableList.clear();

                medaxilFakturaObservableList.addAll(filteredSales);
            } catch (Exception e){
                System.out.println(e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Xəta");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    public void initList(){

        medaxilFakturaObservableList.clear();
        medaxilFakturaList.clear();
        client.sendMessage("QUERYKREDITORMEDAXILNUMS");
        client.sendMessage(kreditor.getName());
        List<Integer> medaxilIds= (List<Integer>) client.listReader();
        for (int i =0;i<medaxilIds.size();i++){
            client.sendMessage("QUERYMEDAXILITEMS");
            client.sendMessage(String.valueOf(medaxilIds.get(i)));
            List<MedaxilFaktura> tempo= (List<MedaxilFaktura>) client.listReader();
            for (MedaxilFaktura m: tempo){
                client.sendMessage("GETMEDAXILTARIX");
                client.sendMessage(String.valueOf(medaxilIds.get(i)));
                String date  = client.reader();
                m.setTarix(date);
                medaxilFakturaList.add(m);
            }
        }

        for (MedaxilFaktura medaxilFaktura: medaxilFakturaList){
            medaxilFakturaObservableList.add(medaxilFaktura);
        }
        kreditorInfoView.setItems(medaxilFakturaObservableList);

    }
    private void setNode(Node node) {
        kreditorInfoPane.getChildren().clear();
        kreditorInfoPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
}
