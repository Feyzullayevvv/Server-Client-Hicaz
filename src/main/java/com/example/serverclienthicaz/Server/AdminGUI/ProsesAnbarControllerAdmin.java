package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.ModelProses.VirtualAnbar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProsesAnbarControllerAdmin {
    @FXML
    private TableView<VirtualAnbar> anbarTableView;
    @FXML
    private TableColumn<VirtualAnbar,String> nr;
    @FXML
    private TableColumn<VirtualAnbar,String> malNr;
    @FXML
    private TableColumn<VirtualAnbar,String> mal;
    @FXML
    private TableColumn<VirtualAnbar,String> vahid;
    @FXML
    private TableColumn<VirtualAnbar,String> miqdar;
    @FXML
    private TextField searchField;

    private Client client;

    private ObservableList<VirtualAnbar> virtualAnbarObservableList = FXCollections.observableArrayList();
    private List<VirtualAnbar> virtualAnbarList = new ArrayList<>();

    public void  init(){
        nr.setCellValueFactory(new PropertyValueFactory<>("nr"));
        malNr.setCellValueFactory(new PropertyValueFactory<>("malNr"));
        mal.setCellValueFactory(new PropertyValueFactory<>("mal"));
        vahid.setCellValueFactory(new PropertyValueFactory<>("vahid"));
        miqdar.setCellValueFactory(new PropertyValueFactory<>("miqdar"));
        populate();
    }

    public void populate(){
        virtualAnbarObservableList.clear();
        virtualAnbarList.clear();
        client.sendMessage("GETPROSESANBAR");
        virtualAnbarList = (List<VirtualAnbar>) client.objectReader();
        for (VirtualAnbar a : virtualAnbarList){
            virtualAnbarObservableList.add(a);
        }
        anbarTableView.setItems(virtualAnbarObservableList);
    }
    private void Search(ObservableList<VirtualAnbar> virtualAnbarObservableList, String malName) {
        virtualAnbarObservableList.clear();
        String lowercaseName = malName.toLowerCase();
        for (int i = 0; i < virtualAnbarList.size(); i++) {
            if (virtualAnbarList.get(i).getMal().toLowerCase().contains(lowercaseName)) {
                virtualAnbarObservableList.add(virtualAnbarList.get(i));
            }
        }
    }
    public void handleSifirla(){
        client.sendMessage("RESETPROSESANBAR");
        populate();
    }
    public void handleKeyReleased(KeyEvent event){
        if (event.getEventType() == KeyEvent.KEY_RELEASED){
            String name = searchField.getText();

            Search(virtualAnbarObservableList,name);
        }
    }
    public void handleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            if (anbarTableView.getSelectionModel().getSelectedItem() != null) {
                VirtualAnbar selectedItem = anbarTableView.getSelectionModel().getSelectedItem();
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(anbarTableView.getScene().getWindow());
                dialog.setTitle("Admin");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/VirtualAnbarEditAdmin.fxml"));
                try {
                    dialog.getDialogPane().setContent(fxmlLoader.load());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.initStyle(StageStyle.UTILITY);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
                VirtualAnbarEditController controller = fxmlLoader.getController();
                controller.init(client,selectedItem);

                Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setDisable(true);

                controller.getMiqdarText().textProperty().addListener((observable, oldValue, newValue) -> {
                    updateOkButtonDisableProperty(okButton,controller.getMiqdarText());
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

    public void setClient(Client client) {
        this.client = client;
    }

}
