package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.AnbarGUI.AnbarClient;
import com.example.serverclienthicaz.Server.ModelAnbar.Kreditor;
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

public class KreditorSechimiDialogAdmin {
    @FXML
    private TableView<Kreditor> KreditorSiyahiTableView;
    @FXML
    private TableColumn<Kreditor,String> nr;
    @FXML
    private TableColumn<Kreditor,String> name;

    @FXML
    private TextField search;



    private Client client;

    private static ObservableList<Kreditor> kreditorObservableList = FXCollections.observableArrayList();
    private static List<Kreditor> kreditorList = new ArrayList<>();



    public void initialize(Client client) {
        this.client = client;
        nr.setCellValueFactory(new PropertyValueFactory<>("nr"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        initMalList();
        KreditorSiyahiTableView.setItems(kreditorObservableList);

    }

    public Kreditor selectedKreditor(){
        return KreditorSiyahiTableView.getSelectionModel().getSelectedItem();
    }
    public void handleKeyReleased(KeyEvent event){
        if (event.getEventType() == KeyEvent.KEY_RELEASED){
            String name = search.getText();

            Search(kreditorObservableList,name);
        }
    }
    private void Search(ObservableList<Kreditor> kreditorObservableList, String name) {
        kreditorObservableList.clear();
        String lowercaseName = name.toLowerCase();
        for (int i = 0; i < kreditorList.size(); i++) {
            if (kreditorList.get(i).getName().toLowerCase().contains(lowercaseName)) {
                kreditorObservableList.add(kreditorList.get(i));
            }
        }
    }

    public void initMalList(){
        kreditorList.clear();
        kreditorObservableList.clear();
        client.sendMessage("QUERYKREDITORS");
        kreditorList =(List<Kreditor>) client.listReader();
        for (Kreditor kreditor : kreditorList){
            kreditorObservableList.add(kreditor);

        }

    }
}
