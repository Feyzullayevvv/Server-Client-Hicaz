package com.example.serverclienthicaz.Server.AnbarGUI;


import com.example.serverclienthicaz.Server.ModelAnbar.Mexaric;
import com.example.serverclienthicaz.Server.ModelAnbar.MexaricFaktura;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class MexaricInfoController{
    @FXML
    private AnchorPane mexaricInfoPane;

    @FXML
    private TextField tarixTextField;
    @FXML
    private TextField tehvilAlanTextField;

    @FXML
    private TableView<MexaricFaktura> mexaricInfoTableView;
    @FXML
    private TableColumn<MexaricFaktura,String> MalNr;
    @FXML
    private TableColumn<MexaricFaktura,String> Mal;
    @FXML
    private TableColumn<MexaricFaktura,String> Vahid;
    @FXML
    private TableColumn<MexaricFaktura,String> Ceki;


    private Pane pane;

    private Mexaric mexaric;
    private AnbarClient anbarClient;
    private static ObservableList<MexaricFaktura> mexaricFakturaObservableList = FXCollections.observableArrayList();
    private static List<MexaricFaktura> mexaricFakturaList = new ArrayList<>();



    public void init(AnbarClient anbarClient) {
        this.anbarClient = anbarClient;
        MalNr.setCellValueFactory(new PropertyValueFactory<>("MalNr"));
        Mal.setCellValueFactory(new PropertyValueFactory<>("Mal"));
        Vahid.setCellValueFactory(new PropertyValueFactory<>("Vahid"));
        Ceki.setCellValueFactory(new PropertyValueFactory<>("Ceki"));


        tarixTextField.setText(mexaric.getTarix());
        tehvilAlanTextField.setText(mexaric.getTehvilAlan());
        initList();

    }

    public void setMexaric(Mexaric mexaric){
        this.mexaric =mexaric;
    }

    private void setNode(Node node) {
        mexaricInfoPane.getChildren().clear();
        mexaricInfoPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }
    public void initList(){

        mexaricFakturaObservableList.clear();
        mexaricFakturaList.clear();
        anbarClient.sendMessage("QUERYMEXERICITEMS");
        anbarClient.sendMessage(String.valueOf(mexaric.getNr()));
        mexaricFakturaList = (List<MexaricFaktura>) anbarClient.listReader();
        for (MexaricFaktura mexaricFaktura: mexaricFakturaList){
            mexaricFakturaObservableList.add(mexaricFaktura);
        }
        mexaricInfoTableView.setItems(mexaricFakturaObservableList);
    }


    public void handleBack(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AnbarFxml/Mexaric.fxml"));
            pane = loader.load();
            MexaricController controller = loader.getController();
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

}
