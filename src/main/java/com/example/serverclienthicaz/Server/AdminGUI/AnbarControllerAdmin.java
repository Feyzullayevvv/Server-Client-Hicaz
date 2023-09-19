package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.ModelAnbar.AnbarItem;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnbarControllerAdmin {
    @FXML
    private AnchorPane anbarPane;
    @FXML
    private TextField searchBar;

    @FXML
    private TableView<AnbarItem> anbarTableView;
    @FXML
    private TableColumn<AnbarItem,String> Nr;
    @FXML
    private TableColumn<AnbarItem,String> xammalMal;
    @FXML
    private TableColumn<AnbarItem,String> vahid;
    @FXML
    private TableColumn<AnbarItem,String> miqdar;
    @FXML
    private TableColumn<AnbarItem,String> mebleg;

    private javafx.scene.layout.Pane Pane;
    private Client client;



    private static ObservableList<AnbarItem> anbarItemObservableList= FXCollections.observableArrayList();
    private static List<AnbarItem> anbarItemList= new ArrayList<>();

    public void init(Client client) {
        this.client = client;
        Nr.setCellValueFactory(new PropertyValueFactory<>("Nr"));
        xammalMal.setCellValueFactory(new PropertyValueFactory<>("Mal"));
        vahid.setCellValueFactory(new PropertyValueFactory<>("Vahid"));
        miqdar.setCellValueFactory(new PropertyValueFactory<>("Ceki"));
        mebleg.setCellValueFactory(new PropertyValueFactory<>("mebleg"));

        initList();
    }

    public void initList(){
        anbarItemObservableList.clear();
        anbarItemList.clear();
        client.sendMessage("QUERYXAMMALANBARITEMS");
        anbarItemList= (List<AnbarItem>) client.listReader();
        for (AnbarItem anbarItem: anbarItemList){
            anbarItemObservableList.add(anbarItem);
        }
        anbarTableView.setItems(anbarItemObservableList);
    }
    private void setNode(Node node) {
        anbarPane.getChildren().clear();
        anbarPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }
    public void handleKeyReleased(KeyEvent event){
        if (event.getEventType() == KeyEvent.KEY_RELEASED){
            String name = searchBar.getText();

            Search(anbarItemObservableList,name);
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
                headerRow.createCell(1).setCellValue("Mal");
                headerRow.createCell(2).setCellValue("Vahid");
                headerRow.createCell(3).setCellValue("Ceki");
                headerRow.createCell(4).setCellValue("Mebleg");

                int rowIndex = 1;
                for (AnbarItem anbarItem : anbarItemObservableList) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(anbarItem.getNr());
                    row.createCell(1).setCellValue(anbarItem.getMal());
                    row.createCell(2).setCellValue(anbarItem.getVahid());
                    row.createCell(3).setCellValue(anbarItem.getCeki());
                    row.createCell(4).setCellValue(anbarItem.getMebleg());
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
    public void createNewInsertAnbar(){
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/insertAnbarAdmin.fxml"));
            Pane = loader.load();
            insertAnbarAdmin controller= loader.getController();
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
    public void handleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            if (anbarTableView.getSelectionModel().getSelectedItem() != null) {
                AnbarItem selectedItem = anbarTableView.getSelectionModel().getSelectedItem();
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(anbarPane.getScene().getWindow());
                dialog.setTitle("Admin");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/XammalAnbarEdit.fxml"));
                try {
                    dialog.getDialogPane().setContent(fxmlLoader.load());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.initStyle(StageStyle.UTILITY);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
                XammalAnbarEditController controller = fxmlLoader.getController();
                controller.init(client,selectedItem);

                Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setDisable(true);

                controller.getCekiText().textProperty().addListener((observable, oldValue, newValue) -> {
                    updateOkButtonDisableProperty(okButton,controller.getCekiText(),controller.getMeblegText());
                });
                controller.getMeblegText().textProperty().addListener((observable, oldValue, newValue) -> {
                    updateOkButtonDisableProperty(okButton,controller.getCekiText(),controller.getMeblegText());
                });

                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    controller.updateXammal();
                }
                initList();

            }
        }
    }
    private void updateOkButtonDisableProperty(Button okButton, TextField cekiText,TextField meblegText) {
        if (cekiText.getText().isEmpty() || !areFieldsValid(cekiText) || !areFieldsValid(meblegText) || meblegText.getText().isEmpty()){
            okButton.setDisable(true);
        }else{
            okButton.setDisable(false);
        }
    }
    public boolean areFieldsValid(TextField n) {

        String itemPrice = n.getText();

        if (itemPrice.isEmpty()) {
            return false;
        }

        try {
            double itemValue = Double.parseDouble(itemPrice);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private void Search(ObservableList<AnbarItem> anbarItemObservableList, String name) {
        anbarItemObservableList.clear();
        String lowercaseName = name.toLowerCase();
        for (int i = 0; i < anbarItemList.size(); i++) {
            if (anbarItemList.get(i).getMal().toLowerCase().contains(lowercaseName)) {
                anbarItemObservableList.add(anbarItemList.get(i));
            }
        }
    }
}
