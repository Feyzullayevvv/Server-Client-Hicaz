package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.AnbarGUI.AnbarClient;
import com.example.serverclienthicaz.Server.AnbarGUI.MalSiyahiController;
import com.example.serverclienthicaz.Server.ModelAnbar.XammalMal;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class XammalNewMalController {
    @FXML
    private AnchorPane newMalPane;
    @FXML
    private TableView<XammalMal> newMalTableView;
    @FXML
    private TableColumn<XammalMal,String> malName;
    @FXML
    private TableColumn<XammalMal,String> malVahid;
    @FXML
    private TableColumn<XammalMal,String> malOrtaGiymet;

    @FXML
    private TextField malAdiTextField;
    @FXML
    private TextField vahidTextField;
    @FXML
    private TextField ortaQiymetTextField;
    @FXML
    private Button addButton;

    @FXML
    private Button bitirButton;
    private Pane pane;
    private Client client;


    private static ObservableList<XammalMal> xammalMalObservableList = FXCollections.observableArrayList();
    private static List<XammalMal> xammalMalList = new ArrayList<>();

    public void init(Client client) {
        this.client = client;
        malName.setCellValueFactory(new PropertyValueFactory<>("Ad"));
        malVahid.setCellValueFactory(new PropertyValueFactory<>("Vahid"));
        malOrtaGiymet.setCellValueFactory(new PropertyValueFactory<>("OrtaGiymet"));
        malAdiTextField.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        vahidTextField.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        ortaQiymetTextField.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        initList();
    }



    public void onElaveEtClicked(){
        XammalMal xammalMal = new XammalMal();
        xammalMal.setAd(malAdiTextField.getText());
        xammalMal.setVahid(vahidTextField.getText());
        xammalMal.setOrtaGiymet(Double.parseDouble(ortaQiymetTextField.getText()));
        xammalMalList.add(xammalMal);
        initList();
    }
    public boolean areFieldsValid() {
        String qiymetText = ortaQiymetTextField.getText();

        if (qiymetText.isEmpty()) {
            return false;
        }

        try {
            double qiymetValue = Double.parseDouble(qiymetText);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private void updateElaveEtButtonState() {
        if (malAdiTextField.getText().isEmpty() || vahidTextField.getText().isEmpty() || ortaQiymetTextField.getText().isEmpty() || !areFieldsValid()){
            addButton.setDisable(true);
        }else{

            addButton.setDisable(false);
        }
    }
    public void initList(){
        xammalMalObservableList.clear();
        for (XammalMal xammalMal : xammalMalList){
            xammalMalObservableList.add(xammalMal);
        }
        newMalTableView.setItems(xammalMalObservableList);
        if (xammalMalObservableList.isEmpty()) {
            bitirButton.setDisable(true);
        } else {
            bitirButton.setDisable(false);
        }
    }
    public void handleBitti(){
        client.sendMessage("INSERTNEWMAL");
        for (XammalMal xammalMal : xammalMalObservableList){
            client.sendMessage(xammalMal.getAd()+"/"+xammalMal.getVahid()+"/"+ xammalMal.getOrtaGiymet());
        }
        client.sendMessage("DONE");

        try {
            xammalMalList.clear();
            xammalMalObservableList.clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/XammalMalSiyahi.fxml"));
            pane = loader.load();
            XammalMalSiyahiController controller = loader.getController();
            controller.init(client);
            setNode(pane);
        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void handleBack(){
        try {
            xammalMalList.clear();
            xammalMalObservableList.clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/XammalMalSiyahi.fxml"));
            pane = loader.load();
            XammalMalSiyahiController controller = loader.getController();
            controller.init(client);
            setNode(pane);
        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void handleDelete(){
        XammalMal selectedItem = newMalTableView.getSelectionModel().getSelectedItem();
        try {
            xammalMalList.remove(selectedItem);
            initList();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            Alert  alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    private void setNode(Node node) {
        newMalPane.getChildren().clear();
        newMalPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }
}
