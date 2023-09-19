package com.example.serverclienthicaz.Server.AnbarGUI;


import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.Optional;

public class MainController {
    @FXML
    private AnchorPane holdPane;
    private AnchorPane Pane;
    @FXML
    private AnbarClient anbarClient;





    public void handleExit(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Çıxmağa Əminsiniz?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()==ButtonType.OK){
            System.exit(0);
            anbarClient.sendMessage("EXIT");
        }
    }



    private void setNode(Node node) {
        holdPane.getChildren().clear();
        holdPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }

    public void createMedaxil(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AnbarFxml/Medaxil.fxml"));
            Pane = loader.load();
            MedaxilController controller =  loader.getController();
            controller.init(anbarClient);
            setNode(Pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
    public void createMexaric(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AnbarFxml/Mexaric.fxml"));
            Pane = loader.load();
            MexaricController controller = loader.getController();
            controller.init(anbarClient);
            setNode(Pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void createMalSiyahi(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AnbarFxml/MalSiyahi.fxml"));
            Pane = loader.load();
            MalSiyahiController controller = loader.getController();
            controller.init(anbarClient);
            setNode(Pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void createAnbar(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AnbarFxml/Anbar.fxml"));
            Pane = loader.load();
            AnbarController controller = loader.getController();
            controller.init(anbarClient);
            setNode(Pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void createKreditor(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AnbarFxml/Kreditor.fxml"));
            Pane = loader.load();
            KreditorController controller= loader.getController();
            controller.init(anbarClient);
            setNode(Pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void createDashBoard(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hicaz/FXML/dashboard.fxml"));
            Pane = loader.load();
            setNode(Pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void setClient(AnbarClient anbarClient) {
        this.anbarClient = anbarClient;
    }
}
