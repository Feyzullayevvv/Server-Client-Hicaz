package com.example.serverclienthicaz.Server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/serverclienthicaz/ServerFXML/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }

    public static void main(String[] args) {
        launch(args);
    }

}
