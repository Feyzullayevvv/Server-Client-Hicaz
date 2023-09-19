package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.ModelAnbar.XammalMal;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class XammalMalSiyahiController {
    @FXML
    private AnchorPane malSiyahiPane;

    @FXML
    private TableView<XammalMal> malTableView;
    @FXML
    private TableColumn<XammalMal,String> Nr;
    @FXML
    private TableColumn<XammalMal,String> malName;
    @FXML
    private TableColumn<XammalMal,String> Vahid;
    @FXML
    private TableColumn<XammalMal,String> ortaQiymet;

    @FXML
    private TextField searchField;

    private Client client;

    private Pane pane;

    private static ObservableList<XammalMal> xammalMalObservableList = FXCollections.observableArrayList();
    private static List<XammalMal> xammalMalList = new ArrayList<>();



    public void init(Client client) {
        this.client = client;
        Nr.setCellValueFactory(new PropertyValueFactory<>("Nr"));
        malName.setCellValueFactory(new PropertyValueFactory<>("Ad"));
        Vahid.setCellValueFactory(new PropertyValueFactory<>("Vahid"));
        ortaQiymet.setCellValueFactory(new PropertyValueFactory<>("OrtaGiymet"));
        populate();
    }

    public void populate(){
        client.sendMessage("QUERYALLXAMMAL");
        xammalMalList.clear();
        xammalMalObservableList.clear();
        xammalMalList = (List<XammalMal>) client.listReader();
        for (XammalMal xammalMal : xammalMalList){
            xammalMalObservableList.add(xammalMal);
        }
        malTableView.setItems(xammalMalObservableList);
    }
    private void Search(ObservableList<XammalMal> xammalMalObservableList, String malName) {
        xammalMalObservableList.clear();
        String lowercaseName = malName.toLowerCase();
        for (int i = 0; i < xammalMalList.size(); i++) {
            if (xammalMalList.get(i).getAd().toLowerCase().contains(lowercaseName)) {
                xammalMalObservableList.add(xammalMalList.get(i));
            }
        }
    }
    public void handleKeyReleased(KeyEvent event){
        if (event.getEventType() == KeyEvent.KEY_RELEASED){
            String name = searchField.getText();

            Search(xammalMalObservableList,name);
        }
    }

    public void createYeniMal(){
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/NewMalAdmin.fxml"));
            pane = loader.load();
            XammalNewMalController controller= loader.getController();
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
    @FXML
    public void handleKeyPressed(KeyEvent keyEvent){
        XammalMal selectedItem = malTableView.getSelectionModel().getSelectedItem();
        if (selectedItem!=null){
            if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)){
                deleteItem(selectedItem);
            }
        }
    }
    public void  deleteItem(XammalMal xammalMal){
        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Malı silmək");
            alert.setHeaderText("Mal nömrəsi: " + xammalMal.getNr()+"\n Mal adı: " + xammalMal + "\n Orta məbləğ: " + xammalMal.getOrtaGiymet());
            alert.setContentText("Əminsiniz?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                client.sendMessage("DELETEXAMMALFROMSIYAHI");
                client.sendMessage(String.valueOf(xammalMal.getNr()));
                populate();
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert  alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void handleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            if (malTableView.getSelectionModel().getSelectedItem() != null) {
                XammalMal selectedItem = malTableView.getSelectionModel().getSelectedItem();
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(malTableView.getScene().getWindow());
                dialog.setTitle("Admin");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/XammalSiyahiEdit.fxml"));
                try {
                    dialog.getDialogPane().setContent(fxmlLoader.load());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.initStyle(StageStyle.UTILITY);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
                editXammalSiyahi controller = fxmlLoader.getController();
                controller.init(client,selectedItem);

                Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setDisable(true);

                controller.getOrtaQiymetText().textProperty().addListener((observable, oldValue, newValue) -> {
                    updateOkButtonDisableProperty(okButton,controller.getOrtaQiymetText());
                });

                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    controller.updateXammal();
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

    private void setNode(Node node) {
        malSiyahiPane.getChildren().clear();
        malSiyahiPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }
}
