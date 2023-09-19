package com.example.serverclienthicaz.Server.ProsesAnbarGUI;

import com.example.serverclienthicaz.Server.ModelProses.Tehvil;
import com.example.serverclienthicaz.Server.ModelProses.TehvilItems;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TehvilInfoDialog {
    @FXML
    private TableView<TehvilItems> tehvilInfoTableView;
    @FXML
    private TableColumn<TehvilItems,String> nr;
    @FXML
    private TableColumn<TehvilItems,String> mal;
    @FXML
    private TableColumn<TehvilItems,String> miqdar;
    @FXML
    private TableColumn<TehvilItems,String> tehvilNr;
    private ProsesClient prosesClient;
    private Tehvil tehvil;

    private ObservableList<TehvilItems> tehvilItemsObservableList = FXCollections.observableArrayList();
    private List<TehvilItems> tehvilItemsList = new ArrayList<>();


    public void init(ProsesClient prosesClient, Tehvil tehvil){
        this.prosesClient = prosesClient;
        this.tehvil=tehvil;
        nr.setCellValueFactory(new PropertyValueFactory<>("Nr"));
        mal.setCellValueFactory(new PropertyValueFactory<>("Mal"));
        miqdar.setCellValueFactory(new PropertyValueFactory<>("Ceki"));
        tehvilNr.setCellValueFactory(new PropertyValueFactory<>("TehvilNr"));
        populate();
    }

    public void populate(){
        tehvilItemsObservableList.clear();
        tehvilItemsList.clear();
        prosesClient.sendMessage("GETTEHVININFO");
        prosesClient.sendMessage(String.valueOf(tehvil.getNr()));
        tehvilItemsList = (List<TehvilItems>) prosesClient.objectReader();
        for (TehvilItems tehvilItems :tehvilItemsList){
            tehvilItemsObservableList.add(tehvilItems);
        }
        tehvilInfoTableView.setItems(tehvilItemsObservableList);
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
                headerRow.createCell(1).setCellValue("MalNr");
                headerRow.createCell(2).setCellValue("Mal");
                headerRow.createCell(3).setCellValue("Miqdar");
                headerRow.createCell(4).setCellValue("TehvilNr");



                int rowIndex = 1;
                for (TehvilItems tehvil : tehvilItemsObservableList) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(tehvil.getNr());
                    row.createCell(1).setCellValue(tehvil.getMalNr());
                    row.createCell(2).setCellValue(tehvil.getMal());
                    row.createCell(3).setCellValue(tehvil.getCeki());
                    row.createCell(4).setCellValue(tehvil.getTehvilNr());

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

}
