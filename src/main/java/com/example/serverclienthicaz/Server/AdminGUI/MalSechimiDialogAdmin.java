package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.AnbarGUI.AnbarClient;
import com.example.serverclienthicaz.Server.ModelAnbar.XammalMal;
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

public class MalSechimiDialogAdmin {
    @FXML
    private TableView<XammalMal> malSiyahiTableView;
    @FXML
    private TableColumn<XammalMal,String> MalNr;
    @FXML
    private TableColumn<XammalMal,String> Mal;
    @FXML
    private TableColumn<XammalMal,String> Vahid;

    @FXML
    private TextField search;

    private Client client;



    private static ObservableList<XammalMal> xammalMalObservableList = FXCollections.observableArrayList();
    private static List<XammalMal> xammalMalList = new ArrayList<>();


    public void init(Client client) {
        this.client = client;
        MalNr.setCellValueFactory(new PropertyValueFactory<>("Nr"));
        Mal.setCellValueFactory(new PropertyValueFactory<>("Ad"));
        Vahid.setCellValueFactory(new PropertyValueFactory<>("Vahid"));
        initMalList();
        malSiyahiTableView.setItems(xammalMalObservableList);

    }


    public void handleKeyReleased(KeyEvent event){
        if (event.getEventType() == KeyEvent.KEY_RELEASED){
            String name = search.getText();

            Search(xammalMalObservableList,name);
        }
    }
    private void Search(ObservableList<XammalMal> xammalMalObservableList, String name) {
        xammalMalObservableList.clear();
        String lowercaseName = name.toLowerCase();
        for (int i = 0; i < xammalMalList.size(); i++) {
            if (xammalMalList.get(i).getAd().toLowerCase().contains(lowercaseName)) {
                xammalMalObservableList.add(xammalMalList.get(i));
            }
        }
    }

    public XammalMal selectedMal(){
        return malSiyahiTableView.getSelectionModel().getSelectedItem();
    }

    public void initMalList(){
        xammalMalList.clear();
        xammalMalObservableList.clear();
        client.sendMessage("QUERYALLXAMMAL");
        xammalMalList =(List<XammalMal>) client.listReader();
        for (XammalMal xammalMal : xammalMalList){
            xammalMalObservableList.add(xammalMal);

        }

    }
}
