package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.ModelProses.HazirMal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class HazirMalSechimiDialogAdmin {
    @FXML
    private TableView<HazirMal> malSiyahiTableView;
    @FXML
    TableColumn<HazirMal,String> id;
    @FXML
    TableColumn<HazirMal,String> name;

    @FXML
    private TextField search;

    private Client client;




    private static ObservableList<HazirMal> malObservableList= FXCollections.observableArrayList();
    private static List<HazirMal> malList = new ArrayList<>();



    public void init() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        initMalList();
    }


    public void handleKeyReleased(KeyEvent event){
        if (event.getEventType() == KeyEvent.KEY_RELEASED){
            String name = search.getText();

            Search(malObservableList,name);
        }
    }
    private void Search(ObservableList<HazirMal> malObservableList, String name) {
        malObservableList.clear();
        String lowercaseName = name.toLowerCase();
        for (int i = 0; i < malList.size(); i++) {
            if (malList.get(i).getName().toLowerCase().contains(lowercaseName)) {
                malObservableList.add(malList.get(i));
            }
        }
    }

    public HazirMal selectedMal(){
        return malSiyahiTableView.getSelectionModel().getSelectedItem();
    }

    public void initMalList(){
        client.sendMessage("GETHAZIRMALSIYAHI");
        malList.clear();
        malObservableList.clear();
        malList = (List<HazirMal>) client.objectReader();
        for (HazirMal hazirMal:malList){
            malObservableList.add(hazirMal);
        }
        malSiyahiTableView.setItems(malObservableList);

    }
    public void setClient (Client client){
        this.client = client;
    }

}

