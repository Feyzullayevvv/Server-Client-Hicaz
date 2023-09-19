package com.example.serverclienthicaz.Server.ProsesAnbarGUI;

import com.example.serverclienthicaz.Server.ModelProses.HazirAnbar;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
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

public class HazirMehsulAnbarController {

    @FXML
    private AnchorPane hazirAnbarPane;

    @FXML
    private ToggleButton switchButton;
    @FXML
    private Circle indicatorCircle;
    @FXML
    TableView<HazirAnbar> hazirMehsulTableView;
    @FXML
    TableColumn<HazirAnbar,String> id;
    @FXML
    TableColumn<HazirAnbar,String> mal;
    @FXML
    TableColumn<HazirAnbar,String> vahid;
    @FXML
    TableColumn<HazirAnbar,String> miqdar;
    @FXML
    private Button sifirlaButton;
    @FXML
    private TextField search;


    private List<HazirAnbar> hazirAnbarList;
    private ObservableList<HazirAnbar> hazirAnbarObservableList;

    private ProsesClient prosesClient;
    private AnchorPane Pane;

    private boolean admin= false;



    private boolean isSwitchOn = false;

    public void init() {
        hazirAnbarList= new ArrayList<>();
        hazirAnbarObservableList = FXCollections.observableArrayList();
        sifirlaButton.setDisable(true);
        switchButton.setOnAction(this::handleSwitchButtonAction);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        mal.setCellValueFactory(new PropertyValueFactory<>("mal"));
        vahid.setCellValueFactory(new PropertyValueFactory<>("vahid"));
        miqdar.setCellValueFactory(new PropertyValueFactory<>("miqdar"));
        populate();
    }

    public void populate(){
        prosesClient.sendMessage("GETHAZIRANBAR");
        hazirAnbarList.clear();
        hazirAnbarObservableList.clear();
        hazirAnbarList= (List<HazirAnbar>) prosesClient.objectReader();
        for (HazirAnbar hazirAnbar :hazirAnbarList){
            hazirAnbarObservableList.add(hazirAnbar);
        }
        hazirMehsulTableView.setItems(hazirAnbarObservableList);
    }

    @FXML
    private void handleSwitchButtonAction(ActionEvent event) {
        if (admin) {
            isSwitchOn = !isSwitchOn;
            if (!isSwitchOn) {
                moveIndicatorToOnPosition();
            } else {
                moveIndicatorToOffPosition();
                admin=false;

            }
        } else {
            createPassword();
        }
    }





    private void moveIndicatorToOnPosition() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.2), indicatorCircle);
        double circleOffset = (switchButton.getWidth() - indicatorCircle.getRadius())* 0.7;
        transition.setToX(circleOffset);
        transition.play();
        sifirlaButton.setDisable(false);

    }

    private void moveIndicatorToOffPosition() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.2), indicatorCircle);
        transition.setToX(0);
        transition.play();
        sifirlaButton.setDisable(true);

    }



    public void setClient(ProsesClient prosesClient) {
        this.prosesClient = prosesClient;
    }
    public void createNewHazirMehsul(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/ProsesFxml/NewHazirMehsul.fxml"));
            Pane = loader.load();
            NewHazirMehsulController hazirMehsulAnbarController = loader.getController();
            hazirMehsulAnbarController.setClient(prosesClient);
            hazirMehsulAnbarController.init();
            setNode(Pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            e.printStackTrace();
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void createPassword() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(hazirAnbarPane.getScene().getWindow());
        dialog.setTitle("Admin");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/ProsesFxml/login.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
            PasswordDialog controller = fxmlLoader.getController();
            controller.setClient(prosesClient);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.showAndWait();
            if (controller.login()) {
                populate();
                admin = true;
                moveIndicatorToOnPosition();

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    private void setNode(Node node) {
        hazirAnbarPane.getChildren().clear();
        hazirAnbarPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }
    @FXML
    public void handleKeyPressed(KeyEvent keyEvent){
        HazirAnbar selectedItem = hazirMehsulTableView.getSelectionModel().getSelectedItem();
        if (selectedItem!=null && admin==true){
            if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)){
                deleteItem(selectedItem);
            }
        }
    }
    public void  deleteItem(HazirAnbar hazirMal){
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Məhsulu silmək");
            alert.setHeaderText("Məhsul adı: " + hazirMal.getMal() );
            alert.setContentText("Əminsiniz?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                prosesClient.sendMessage("DELETEANBARMEHSUL");
                prosesClient.sendMessage(String.valueOf(hazirMal.getId()));
            }
            populate();

        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert  alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void handleSifirla(){
        prosesClient.sendMessage("RESETHAZIRANBAR");
        populate();
    }
    public void handleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            if (hazirMehsulTableView.getSelectionModel().getSelectedItem() != null && admin==true) {
                HazirAnbar selectedItem = hazirMehsulTableView.getSelectionModel().getSelectedItem();
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(hazirAnbarPane.getScene().getWindow());
                dialog.setTitle("Admin");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/ProsesFxml/HazirAnbarEdit.fxml"));
                try {
                    dialog.getDialogPane().setContent(fxmlLoader.load());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.initStyle(StageStyle.UNDECORATED);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
                HazirAnbarEditController controller = fxmlLoader.getController();
                controller.init(prosesClient,selectedItem);

                Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setDisable(true);

                controller.getCekiText().textProperty().addListener((observable, oldValue, newValue) -> {
                    updateOkButtonDisableProperty(okButton,controller.getCekiText());
                });

                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    controller.updateAnbarMal();
                }
                populate();

            }
        }
    }
    private void updateOkButtonDisableProperty(Button okButton, TextField cekiText) {
        if (cekiText.getText().isEmpty() || !areFieldsValid(cekiText)){
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
    public void handleKeyReleased(KeyEvent event){
        if (event.getEventType() == KeyEvent.KEY_RELEASED){
            String name = search.getText();
            Search(hazirAnbarObservableList,name);
        }
    }
    private void Search(ObservableList<HazirAnbar> hazirAnbarObservableList, String name) {
        hazirAnbarObservableList.clear();
        String lowercaseName = name.toLowerCase();
        for (int i = 0; i < hazirAnbarList.size(); i++) {
            if (hazirAnbarList.get(i).getMal().toLowerCase().contains(lowercaseName)) {
                hazirAnbarObservableList.add(hazirAnbarList.get(i));
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
                headerRow.createCell(0).setCellValue("Id");
                headerRow.createCell(1).setCellValue("Mal");
                headerRow.createCell(2).setCellValue("Vahid");
                headerRow.createCell(3).setCellValue("Miqdar");

                int rowIndex = 1;
                for (HazirAnbar hazirAnbar : hazirAnbarObservableList) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(hazirAnbar.getId());
                    row.createCell(1).setCellValue(hazirAnbar.getMal());
                    row.createCell(2).setCellValue(hazirAnbar.getVahid());
                    row.createCell(3).setCellValue(hazirAnbar.getMiqdar());
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

