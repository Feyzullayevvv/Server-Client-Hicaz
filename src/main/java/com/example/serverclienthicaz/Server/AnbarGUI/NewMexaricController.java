package com.example.serverclienthicaz.Server.AnbarGUI;


import com.example.serverclienthicaz.Server.ModelAnbar.MexaricFaktura;
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
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NewMexaricController {

    @FXML
    private TableView<MexaricFaktura> newMexaricTableView;
    @FXML
    private TableColumn<MexaricFaktura,String> MalNr;
    @FXML
    private TableColumn<MexaricFaktura,String> Mal;
    @FXML
    private TableColumn<MexaricFaktura,String> Vahid;
    @FXML
    private TableColumn<MexaricFaktura,String> Ceki;


    @FXML
    private AnchorPane newMexaricPane;

    @FXML
    private TextField malText;
    @FXML
    private TextField cekitext;

    @FXML
    private TextField vahidText;
    @FXML
    private TextField anbarText;


    @FXML
    private TextField tehvilAlanText;

    @FXML
    private Button elaveEtButton;
    @FXML
    private Button bittiButton;
    private AnbarClient anbarClient;



    private Pane pane;

    private static ObservableList<MexaricFaktura> mexaricFakturaObservableList = FXCollections.observableArrayList();
    private static List<MexaricFaktura> mexaricFakturaList = new ArrayList<>();





    public void init(AnbarClient anbarClient) {
        this.anbarClient = anbarClient;
        MalNr.setCellValueFactory(new PropertyValueFactory<>("MalNr"));
        Mal.setCellValueFactory(new PropertyValueFactory<>("Mal"));
        Vahid.setCellValueFactory(new PropertyValueFactory<>("Vahid"));
        Ceki.setCellValueFactory(new PropertyValueFactory<>("Ceki"));

        initList();
        malText.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        cekitext.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        tehvilAlanText.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
    }


    public void createMalSiyahi(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(newMexaricPane.getScene().getWindow());
        dialog.setTitle("Mal seçmək");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/AnbarFxml/MalSiyahiDiaglog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        MalSechimiDialog controller = fxmlLoader.getController();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        controller.init(anbarClient);
        Optional<ButtonType> result= dialog.showAndWait();
        if ((result.isPresent() && result.get()== ButtonType.OK) && controller.selectedMal()!=null){
            malText.setText(controller.selectedMal().getAd());
            vahidText.setText(controller.selectedMal().getVahid());
            anbarClient.sendMessage("GETMOVCUDCEKI");
            anbarClient.sendMessage(controller.selectedMal().getAd());
            double ceki = Double.parseDouble(anbarClient.reader());
            anbarText.setText(String.valueOf(ceki));
        }

    }

    public void initList(){
        mexaricFakturaObservableList.clear();
        for (MexaricFaktura mexaricFaktura: mexaricFakturaList){
            mexaricFakturaObservableList.add(mexaricFaktura);
        }
        newMexaricTableView.setItems(mexaricFakturaObservableList);
        if (mexaricFakturaObservableList.isEmpty()) {
            bittiButton.setDisable(true);
        } else {
            bittiButton.setDisable(false);
        }
    }

    private void updateElaveEtButtonState() {
        if (malText.getText().isEmpty() || cekitext.getText().isEmpty()  || tehvilAlanText.getText().isEmpty() || Double.parseDouble(cekitext.getText())>Double.parseDouble(anbarText.getText())){
            elaveEtButton.setDisable(true);
        }else{
            elaveEtButton.setDisable(false);
        }
    }





    public void onElaveEtClicked(){
        MexaricFaktura mexaricFaktura= new MexaricFaktura();
        anbarClient.sendMessage("GETXAMMALMALINFO");
        anbarClient.sendMessage(malText.getText());
        XammalMal xammalMal = (XammalMal) anbarClient.objectReader();
        mexaricFaktura.setMalNr(xammalMal.getNr());
        mexaricFaktura.setMal(xammalMal.getAd());
        mexaricFaktura.setVahid(xammalMal.getVahid());
        mexaricFaktura.setCeki(Double.parseDouble(cekitext.getText()));

        mexaricFakturaList.add(mexaricFaktura);
        initList();
        malText.clear();
        cekitext.clear();
        vahidText.clear();
    }

    public void handleDelete(){
        MexaricFaktura selectedItem = newMexaricTableView.getSelectionModel().getSelectedItem();
        try {
            mexaricFakturaList.remove(selectedItem);
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

    public void handleBitti(){
        anbarClient.sendMessage("INSERTMEXARIC");
        anbarClient.sendMessage(tehvilAlanText.getText());
        int n = Integer.parseInt(anbarClient.reader());
        System.out.println(n);
        anbarClient.sendMessage("INSERTTEHVIL");
        anbarClient.sendMessage(tehvilAlanText.getText());
        anbarClient.sendMessage(String.valueOf(n));
        int nr = Integer.parseInt(anbarClient.reader());
        System.out.println(nr);
        if(n!=-1 && nr!=-1){
            anbarClient.sendMessage("INSERTMEXARICFAKTURA");
            for (MexaricFaktura mexaricFaktura: mexaricFakturaList){
                anbarClient.sendMessage(mexaricFaktura.getMal() +"/"+mexaricFaktura.getCeki()+"/"+n);
            }
            anbarClient.sendMessage("DONE");
            anbarClient.sendMessage("INSERTEHVILITEMS");
            for (MexaricFaktura mexaricFaktura: mexaricFakturaList){
                anbarClient.sendMessage(mexaricFaktura.getMal() +"/"+mexaricFaktura.getCeki()+"/"+nr);
            }
            anbarClient.sendMessage("DONE");
        }

        try {
            mexaricFakturaList.clear();
            mexaricFakturaObservableList.clear();
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
    private void setNode(Node node) {
        newMexaricPane.getChildren().clear();
        newMexaricPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }

    public void handleBack(){
        try {
            mexaricFakturaList.clear();
            mexaricFakturaObservableList.clear();
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
