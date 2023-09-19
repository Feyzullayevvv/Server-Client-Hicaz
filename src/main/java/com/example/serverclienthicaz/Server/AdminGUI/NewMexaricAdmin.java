package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.AnbarGUI.AnbarClient;
import com.example.serverclienthicaz.Server.AnbarGUI.MalSechimiDialog;
import com.example.serverclienthicaz.Server.AnbarGUI.MexaricController;
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

public class NewMexaricAdmin {
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
    private Client client;



    private Pane pane;

    private static ObservableList<MexaricFaktura> mexaricFakturaObservableList = FXCollections.observableArrayList();
    private static List<MexaricFaktura> mexaricFakturaList = new ArrayList<>();





    public void init(Client client) {
        this.client = client;
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
        fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/MalSiyahiDiaglogAdmin.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        MalSechimiDialogAdmin controller = fxmlLoader.getController();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        controller.init(client);
        Optional<ButtonType> result= dialog.showAndWait();
        if ((result.isPresent() && result.get()== ButtonType.OK) && controller.selectedMal()!=null){
            malText.setText(controller.selectedMal().getAd());
            vahidText.setText(controller.selectedMal().getVahid());
            client.sendMessage("GETMOVCUDCEKI");
            client.sendMessage(controller.selectedMal().getAd());
            double ceki = Double.parseDouble(client.reader());
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
        client.sendMessage("GETXAMMALMALINFO");
        client.sendMessage(malText.getText());
        XammalMal xammalMal = (XammalMal) client.objectReader();
        mexaricFaktura.setMalNr(xammalMal.getNr());
        mexaricFaktura.setMal(xammalMal.getAd());
        mexaricFaktura.setVahid(xammalMal.getVahid());
        mexaricFaktura.setCeki(Double.parseDouble(cekitext.getText()));

        mexaricFakturaList.add(mexaricFaktura);
        initList();
        malText.clear();
        cekitext.clear();
        anbarText.clear();
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
        client.sendMessage("INSERTMEXARIC");
        client.sendMessage(tehvilAlanText.getText());
        int n = Integer.parseInt(client.reader());
        client.sendMessage("INSERTTEHVIL");
        client.sendMessage(tehvilAlanText.getText());
        client.sendMessage(String.valueOf(n));
        int nr = Integer.parseInt(client.reader());
        System.out.println(nr);
        if(n!=-1 && nr!=-1){
            client.sendMessage("INSERTMEXARICFAKTURA");
            for (MexaricFaktura mexaricFaktura: mexaricFakturaList){
                client.sendMessage(mexaricFaktura.getMal() +"/"+mexaricFaktura.getCeki()+"/"+n);
            }
            client.sendMessage("DONE");
            client.sendMessage("INSERTEHVILITEMS");
            for (MexaricFaktura mexaricFaktura: mexaricFakturaList){
                client.sendMessage(mexaricFaktura.getMal() +"/"+mexaricFaktura.getCeki()+"/"+nr);
            }
            client.sendMessage("DONE");
        }

        try {
            mexaricFakturaList.clear();
            mexaricFakturaObservableList.clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/MexaricAdmin.fxml"));
            pane = loader.load();
            MexaricAdminController controller = loader.getController();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/MexaricAdmin.fxml"));
            pane = loader.load();
            MexaricAdminController controller = loader.getController();
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


}
