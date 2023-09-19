package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.ModelProses.Emeliyyat;
import com.example.serverclienthicaz.Server.ModelProses.EmeliyyatItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmeliyyatInfoAdmin {
    @FXML
    private AnchorPane emeliyyatPane;
    @FXML
    private TableView<EmeliyyatItem> emeliyyatTableView;
    @FXML
    private TableColumn<EmeliyyatItem,String> emeliyyatNr;
    @FXML
    private TableColumn<EmeliyyatItem,String> emeliyyatMal;
    @FXML
    private TableColumn<EmeliyyatItem,String> emeliyyatVahid;
    @FXML
    private TableColumn<EmeliyyatItem,String> emeliyyatMiqdar;
    @FXML
    private TableColumn<EmeliyyatItem,String> emeliyyatReceptNr;

    @FXML
    private TableColumn<EmeliyyatItem,String> emeliyyatEmeliyyatNr;


    @FXML
    private TextField dateField;

    private Client client;
    private Emeliyyat emeliyyat;
    private ObservableList<EmeliyyatItem> emeliyyatItemObservableList = FXCollections.observableArrayList();
    private List<EmeliyyatItem> emeliyyatItemList = new ArrayList<>();



    public void init(Client client, Emeliyyat emeliyyat){
        emeliyyatNr.setCellValueFactory(new PropertyValueFactory<>("nr"));
        emeliyyatMal.setCellValueFactory(new PropertyValueFactory<>("mal"));
        emeliyyatVahid.setCellValueFactory(new PropertyValueFactory<>("vahid"));
        emeliyyatMiqdar.setCellValueFactory(new PropertyValueFactory<>("miqdar"));
        emeliyyatReceptNr.setCellValueFactory(new PropertyValueFactory<>("receptNr"));
        emeliyyatEmeliyyatNr.setCellValueFactory(new PropertyValueFactory<>("emeliyyatNr"));

        this.client = client;
        this.emeliyyat=emeliyyat;
        dateField.setText(emeliyyat.getDate());
        populate();
    }

    public void populate(){
        client.sendMessage("GETEMELIYYATINFO");
        client.sendMessage(String.valueOf(emeliyyat.getNr()));
        emeliyyatItemList.clear();
        emeliyyatItemObservableList.clear();
        emeliyyatItemList=(List<EmeliyyatItem>) client.objectReader();
        for (EmeliyyatItem emeliyyatItem:emeliyyatItemList){
            emeliyyatItemObservableList.add(emeliyyatItem);
        }
        emeliyyatTableView.setItems(emeliyyatItemObservableList);
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
                headerRow.createCell(1).setCellValue("Mal");
                headerRow.createCell(2).setCellValue("Vahid");
                headerRow.createCell(3).setCellValue("Miqdar");
                headerRow.createCell(4).setCellValue("ReceptNr");



                int rowIndex = 1;
                for (EmeliyyatItem emeliyyatItem : emeliyyatItemObservableList) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(emeliyyatItem.getNr());
                    row.createCell(1).setCellValue(emeliyyatItem.getMal());
                    row.createCell(2).setCellValue(emeliyyatItem.getVahid());
                    row.createCell(3).setCellValue(emeliyyatItem.getMiqdar());
                    row.createCell(4).setCellValue(emeliyyatItem.getReceptNr());


                }

                for (int i = 0; i < 5; i++) {
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
            if (emeliyyatTableView.getSelectionModel().getSelectedItem() != null) {
                EmeliyyatItem selectedItem = emeliyyatTableView.getSelectionModel().getSelectedItem();
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(emeliyyatPane.getScene().getWindow());
                dialog.setTitle("Information");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/EmeliyyatItemInfoDialogAdmin.fxml"));
                try {
                    dialog.getDialogPane().setContent(fxmlLoader.load());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                dialog.initStyle(StageStyle.UTILITY);
                EmeliyyatItemInfoDialogAdmin controller = fxmlLoader.getController();
                controller.init(client,selectedItem);
                dialog.show();

                populate();

            }
        }
    }
}
