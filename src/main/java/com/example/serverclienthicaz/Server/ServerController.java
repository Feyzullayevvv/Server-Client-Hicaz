package com.example.serverclienthicaz.Server;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ServerController {
    @FXML
    private ToggleButton toggleButton;



    private Thread serverThread;
    public void showLoggedINUI(){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/Server.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),161,95);
            fxmlLoader.setController(this);
            Stage stage = new Stage();
            stage.setResizable(true);
            stage.setTitle("SERVER");
            stage.setScene(scene);
            stage.show();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }


    public void startServer() {
        serverThread = new Thread(() -> {
            Server server = new Server();
            server.start(5555);
        });
        serverThread.setDaemon(true);
        serverThread.start();

        toggleButton.setDisable(true);

    }

    public void stopServer() {
        System.exit(0);
    }
}





