package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.ModelProses.EmeliyyatItem;
import com.example.serverclienthicaz.Server.ModelProses.ReceptItem;
import com.example.serverclienthicaz.Server.ModelProses.TehvilItems;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class EmeliyyatItemInfoDialogAdmin {

    @FXML
    private TableView<ReceptItem> receptItemTableView;
    @FXML
    private TableColumn<ReceptItem,String> receptNr;
    @FXML
    private TableColumn<ReceptItem,String> receptMal;
    @FXML
    private TableColumn<ReceptItem,String> receptVahid;
    @FXML
    private TableColumn<ReceptItem,String> receptMiqdar;


    @FXML
    private TextField receptNRTextField;

    private Client client;
    private EmeliyyatItem emeliyyatItem;
    private ObservableList<ReceptItem> receptItemObservableList = FXCollections.observableArrayList();
    private List<ReceptItem> receptItemList= new ArrayList<>();
    private ObservableList<TehvilItems> tehvilItemsObservableList = FXCollections.observableArrayList();
    private List<TehvilItems> tehvilItemsList = new ArrayList<>();

    public void init(Client client, EmeliyyatItem emeliyyatItem){
        this.client = client;
        this.emeliyyatItem=emeliyyatItem;
        receptNr.setCellValueFactory(new PropertyValueFactory<>("nr"));
        receptMal.setCellValueFactory(new PropertyValueFactory<>("mal"));
        receptVahid.setCellValueFactory(new PropertyValueFactory<>("vahid"));
        receptMiqdar.setCellValueFactory(new PropertyValueFactory<>("miqdar"));

        receptNRTextField.setText(String.valueOf(emeliyyatItem.getReceptNr()));
        populate();
    }
    public void populate(){
        receptItemObservableList.clear();
        receptItemList.clear();
        client.sendMessage("GETRECEPTINFO");
        client.sendMessage(String.valueOf(emeliyyatItem.getReceptNr()));
        receptItemList= (List<ReceptItem>) client.objectReader();
        for (ReceptItem r: receptItemList){
            receptItemObservableList.add(r);
        }
        receptItemTableView.setItems(receptItemObservableList);
        tehvilItemsObservableList.clear();
        tehvilItemsList.clear();

    }
}
