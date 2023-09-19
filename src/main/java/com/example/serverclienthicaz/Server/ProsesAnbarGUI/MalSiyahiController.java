package com.example.serverclienthicaz.Server.ProsesAnbarGUI;

import com.example.serverclienthicaz.Server.ModelAnbar.XammalMal;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class MalSiyahiController {
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
    private ProsesClient prosesClient;

    @FXML
    private TextField searchField;



    private Pane pane;

    private static ObservableList<XammalMal> xammalMalObservableList = FXCollections.observableArrayList();
    private static List<XammalMal> xammalMalList = new ArrayList<>();




    public void init()  {
        Nr.setCellValueFactory(new PropertyValueFactory<>("Nr"));
        malName.setCellValueFactory(new PropertyValueFactory<>("Ad"));
        Vahid.setCellValueFactory(new PropertyValueFactory<>("Vahid"));
        ortaQiymet.setCellValueFactory(new PropertyValueFactory<>("OrtaGiymet"));
        populate();

    }
    public void populate(){
        xammalMalList.clear();
        xammalMalObservableList.clear();
        prosesClient.sendMessage("GETMALSIYAHI");
        xammalMalList = (List<XammalMal>) prosesClient.objectReader();
        for (XammalMal xammalMal : xammalMalList){
            xammalMalObservableList.add(xammalMal);
        }
        malTableView.setItems(xammalMalObservableList);
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
    private void Search(ObservableList<XammalMal> malObservableList, String malName) {
        malObservableList.clear();
        String lowercaseName = malName.toLowerCase();
        for (int i = 0; i < xammalMalList.size(); i++) {
            if (xammalMalList.get(i).getAd().toLowerCase().contains(lowercaseName)) {
                malObservableList.add(xammalMalList.get(i));
            }
        }
    }
    public void handleKeyReleased(KeyEvent event){
        if (event.getEventType() == KeyEvent.KEY_RELEASED){
            String name = searchField.getText();

            Search(xammalMalObservableList,name);
        }
    }


    public void setClient(ProsesClient prosesClient) {
        this.prosesClient = prosesClient;
    }



}
