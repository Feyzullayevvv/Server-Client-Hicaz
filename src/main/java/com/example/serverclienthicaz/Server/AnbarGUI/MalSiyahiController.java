package com.example.serverclienthicaz.Server.AnbarGUI;


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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MalSiyahiController{
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

    private AnbarClient anbarClient;

    private Pane pane;

    private static ObservableList<XammalMal> xammalMalObservableList = FXCollections.observableArrayList();
    private static List<XammalMal> xammalMalList = new ArrayList<>();



    public void init(AnbarClient anbarClient) {
        this.anbarClient = anbarClient;
        Nr.setCellValueFactory(new PropertyValueFactory<>("Nr"));
        malName.setCellValueFactory(new PropertyValueFactory<>("Ad"));
        Vahid.setCellValueFactory(new PropertyValueFactory<>("Vahid"));
        ortaQiymet.setCellValueFactory(new PropertyValueFactory<>("OrtaGiymet"));
        populate();
    }

    public void populate(){
        anbarClient.sendMessage("QUERYALLXAMMAL");
        xammalMalList.clear();
        xammalMalObservableList.clear();
        xammalMalList = (List<XammalMal>) anbarClient.listReader();
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
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AnbarFxml/NewMal.fxml"));
            pane = loader.load();
            NewMalController controller= loader.getController();
            controller.init(anbarClient);
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
                anbarClient.sendMessage("DELETEXAMMALFROMSIYAHI");
                anbarClient.sendMessage(String.valueOf(xammalMal.getNr()));
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
