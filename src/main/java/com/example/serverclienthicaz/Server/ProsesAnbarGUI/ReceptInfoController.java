package com.example.serverclienthicaz.Server.ProsesAnbarGUI;

import com.example.serverclienthicaz.Server.ModelProses.Recept;
import com.example.serverclienthicaz.Server.ModelProses.ReceptItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class ReceptInfoController {
    @FXML
    private TableView<ReceptItem> receptItemTableView;
    @FXML
    private TableColumn<ReceptItem,String> nr;
    @FXML
    private TableColumn<ReceptItem,String> mal;
    @FXML
    private TableColumn<ReceptItem,String> vahid;
    @FXML
    private TableColumn<ReceptItem,String> miqdar;

    private ProsesClient prosesClient;
    private Recept recept;

    private ObservableList<ReceptItem> receptItemObservableList = FXCollections.observableArrayList();
    private List<ReceptItem> receptItemList= new ArrayList<>();

    public void init(ProsesClient prosesClient, Recept recept){
        this.prosesClient = prosesClient;
        this.recept=recept;
        nr.setCellValueFactory(new PropertyValueFactory<>("nr"));
        mal.setCellValueFactory(new PropertyValueFactory<>("mal"));
        vahid.setCellValueFactory(new PropertyValueFactory<>("vahid"));
        miqdar.setCellValueFactory(new PropertyValueFactory<>("miqdar"));
        populate();
    }

    public void populate(){
        receptItemObservableList.clear();
        receptItemList.clear();
        prosesClient.sendMessage("GETRECEPTINFO");
        prosesClient.sendMessage(String.valueOf(recept.getNr()));
        receptItemList= (List<ReceptItem>) prosesClient.objectReader();
        for (ReceptItem r: receptItemList){
            receptItemObservableList.add(r);
        }
        receptItemTableView.setItems(receptItemObservableList);
    }


}
