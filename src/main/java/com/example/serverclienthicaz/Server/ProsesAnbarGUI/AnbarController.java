package com.example.serverclienthicaz.Server.ProsesAnbarGUI;

import com.example.serverclienthicaz.Server.ModelProses.VirtualAnbar;
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

public class AnbarController {
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

    private ProsesClient prosesClient;

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
        prosesClient.sendMessage("GETPROSESANBAR");
        virtualAnbarList = (List<VirtualAnbar>) prosesClient.objectReader();
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
    public void handleKeyReleased(KeyEvent event){
        if (event.getEventType() == KeyEvent.KEY_RELEASED){
            String name = searchField.getText();

            Search(virtualAnbarObservableList,name);
        }
    }

    public void setClient(ProsesClient prosesClient) {
        this.prosesClient = prosesClient;
    }
}
