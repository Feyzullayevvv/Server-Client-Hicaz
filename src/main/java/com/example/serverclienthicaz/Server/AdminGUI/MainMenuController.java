package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.AnbarGUI.MalSiyahiController;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.Optional;

public class MainMenuController {
    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }


    @FXML
    private AnchorPane holdPane;
    private AnchorPane pane;

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
    public void createDashBoardMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/dashboard.fxml"));
            pane = loader.load();
            DashBoardController controller = loader.getController();
            controller.init(client);
            setNode(pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            e.printStackTrace();
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void createAnbarMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/AnbarAdmin.fxml"));
            pane = loader.load();
            AnbarControllerAdmin controller = loader.getController();
            controller.init(client);
            setNode(pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            e.printStackTrace();
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void createMedaxilMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/MedaxilAdmin.fxml"));
            pane = loader.load();
            MedaxilAdminController controller = loader.getController();
            controller.init(client);
            setNode(pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            e.printStackTrace();
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void createMexaricMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/MexaricAdmin.fxml"));
            pane = loader.load();
            MexaricAdminController controller = loader.getController();
            controller.init(client);
            setNode(pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            e.printStackTrace();
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void createKreditorMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/KreditorAdmin.fxml"));
            pane = loader.load();
            KreditorAdmin controller = loader.getController();
            controller.init(client);
            setNode(pane);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/EmeliyyatAdmin.fxml"));
            pane = loader.load();
            EmeliyyatController controller = loader.getController();
            controller.setClient(client);
            controller.init();
            setNode(pane);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/Tehvil.fxml"));
            pane = loader.load();
            TehvilController controller = loader.getController();
            controller.setClient(client);
            controller.init();
            setNode(pane);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/ProsesAnbaradmin.fxml"));
            pane = loader.load();
            ProsesAnbarControllerAdmin controller = loader.getController();
            controller.setClient(client);
            controller.init();
            setNode(pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            e.printStackTrace();
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void createHazirMalSiyahiMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/HazirMalSiyahiAdmin.fxml"));
            pane = loader.load();
            HazirMalSiyahiAdminController controller = loader.getController();
            controller.setClient(client);
            controller.init();
            setNode(pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            e.printStackTrace();
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void createXammalMalSiyahi(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/XammalMalSiyahi.fxml"));
            pane = loader.load();
            XammalMalSiyahiController controller = loader.getController();
            controller.init(client);
            setNode(pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    public void createHazirMehsulAnbar(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/HazirMehsulAnbarAdmin.fxml"));
            pane = loader.load();
            HazirMehsulAdminController controller = loader.getController();
            controller.init(client);
            setNode(pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void createReceptMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/ReceptAdmin.fxml"));
            pane = loader.load();
            ReceptAdminController controller = loader.getController();
            controller.init(client);
            setNode(pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }



    public void handleExit(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Çıxmağa Əminsiniz?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()==ButtonType.OK){
            client.sendMessage("EXIT");
            System.exit(0);
        }
    }
}
