package com.example.serverclienthicaz.Server.ProsesAnbarGUI;

import com.example.serverclienthicaz.Server.ModelProses.HazirMal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HazirMalSiyahiController {
    @FXML
    TableView<HazirMal> hazirmalsiyahiTableView;
    @FXML
    TableColumn<HazirMal,String> id;
    @FXML
    TableColumn<HazirMal,String> name;
    @FXML
    private TextField searchBar;

    @FXML
    private AnchorPane hazirMalSiyahiPane;

    private List<HazirMal> hazirMalList = new ArrayList<>();
    private ObservableList<HazirMal> hazirMalObservableList =  FXCollections.observableArrayList();
    private ProsesClient prosesClient;


    public void setClient(ProsesClient prosesClient) {
        this.prosesClient = prosesClient;
    }

    public void init() throws IOException, ClassNotFoundException {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        populate();
    }

    private void populate(){
        prosesClient.sendMessage("GETHAZIRMALSIYAHI");
        hazirMalList.clear();
        hazirMalObservableList.clear();
        hazirMalList = (List<HazirMal>) prosesClient.objectReader();
        for (HazirMal hazirMal:hazirMalList){
            hazirMalObservableList.add(hazirMal);
        }
        hazirmalsiyahiTableView.setItems(hazirMalObservableList);
    }
    @FXML
    public void handleKeyPressed(KeyEvent keyEvent){
        HazirMal selectedItem = hazirmalsiyahiTableView.getSelectionModel().getSelectedItem();
        if (selectedItem!=null){
            if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)){
                deleteItem(selectedItem);
            }
        }
    }
    public void  deleteItem(HazirMal hazirMal){
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Məhsulu silmək");
            alert.setHeaderText("Məhsul adı: " + hazirMal.getName() );
            alert.setContentText("Əminsiniz?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                prosesClient.sendMessage("DELETEHAZIRMEHSUL");
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


    public void createNewHazirMal(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(hazirMalSiyahiPane.getScene().getWindow());
        dialog.setTitle("Yeni Mal");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/ProsesFxml/NewHazirMaldialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        NewHazirMalController controller = fxmlLoader.getController();
        controller.setClient(prosesClient);

        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);

        controller.getHazirMehsulName().textProperty().addListener((observable, oldValue, newValue) -> {
            updateOkButtonDisableProperty(okButton,controller.getHazirMehsulName().getText());
        });

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            controller.insertHazirMal();
            populate();
        }


    }
    private void updateOkButtonDisableProperty(Button okButton, String mehsulName) {
        boolean disableButton = mehsulName.isEmpty();
        okButton.setDisable(disableButton);
    }
    private void Search(ObservableList<HazirMal> hazirMalObservableList, String malName) {
        hazirMalObservableList.clear();
        String lowercaseName = malName.toLowerCase();
        for (int i = 0; i < hazirMalList.size(); i++) {
            if (hazirMalList.get(i).getName().toLowerCase().contains(lowercaseName)) {
                hazirMalObservableList.add(hazirMalList.get(i));
            }
        }
    }
    public void handleKeyReleased(KeyEvent event){
        if (event.getEventType() == KeyEvent.KEY_RELEASED){
            String name = searchBar.getText();

            Search(hazirMalObservableList,name);
        }
    }

}
