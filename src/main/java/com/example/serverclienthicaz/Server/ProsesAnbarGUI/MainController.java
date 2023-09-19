package com.example.serverclienthicaz.Server.ProsesAnbarGUI;

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
    private ProsesClient prosesClient;

    public void setClient(ProsesClient prosesClient) {
        this.prosesClient = prosesClient;
    }


    @FXML
    private AnchorPane holdPane;
    private AnchorPane Pane;
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
    public void createMalSiyahi(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/ProsesFxml/MalSiyahi.fxml"));
            Pane = loader.load();
            MalSiyahiController malSiyahiController = loader.getController();
            malSiyahiController.setClient(prosesClient);
            malSiyahiController.init();
            setNode(Pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            e.printStackTrace();
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void createHazirMalSiyahi(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/ProsesFxml/HazirMalSiyahi.fxml"));
            Pane = loader.load();
            HazirMalSiyahiController hazirMalSiyahiController = loader.getController();
            hazirMalSiyahiController.setClient(prosesClient);
            hazirMalSiyahiController.init();
            setNode(Pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            e.printStackTrace();
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void createHazirMehsulAnbar(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/ProsesFxml/HazirMehsulAnbar.fxml"));
            Pane = loader.load();
            HazirMehsulAnbarController hazirMehsulAnbarController = loader.getController();
            hazirMehsulAnbarController.setClient(prosesClient);
            hazirMehsulAnbarController.init();
            setNode(Pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            e.printStackTrace();
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void createTehvilMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/ProsesFxml/Tehvil.fxml"));
            Pane = loader.load();
            TehvilController tehvilController= loader.getController();
            tehvilController.setClient(prosesClient);
            tehvilController.init();
            setNode(Pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            e.printStackTrace();
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void createReceptMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/ProsesFxml/Recept.fxml"));
            Pane = loader.load();
            ReceptController receptController= loader.getController();
            receptController.setClient(prosesClient);
            receptController.init();
            setNode(Pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            e.printStackTrace();
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void createProsesAnbar(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/ProsesFxml/Anbar.fxml"));
            Pane = loader.load();
            AnbarController controller= loader.getController();
            controller.setClient(prosesClient);
            controller.init();
            setNode(Pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            e.printStackTrace();
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void createEmeliyyatMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/ProsesFxml/Emeliyyat.fxml"));
            Pane = loader.load();
            EmeliyyatController controller= loader.getController();
            controller.setClient(prosesClient);
            controller.init();
            setNode(Pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            e.printStackTrace();
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void handleExit(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Çıxmağa Əminsiniz?");
        prosesClient.sendMessage("EXIT");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()==ButtonType.OK){
            System.exit(0);
        }
    }
}
