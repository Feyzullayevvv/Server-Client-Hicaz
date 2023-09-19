package com.example.serverclienthicaz.Server;

import com.example.serverclienthicaz.Server.ModelProses.User;
import com.example.serverclienthicaz.Server.data.AnbarData;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class LoginController{
    @FXML
    private TextField userName;

    @FXML
    private PasswordField passWord;

    @FXML
    private Label wronguserinput;

    private List<User> users;

    private AnbarData ds;



    @FXML
    public void handleExit() {
        System.exit(0);
    }



    public boolean login() {
        ds = new AnbarData();
        ds.open();
        users = ds.queryUsers();
        String username = userName.getText().trim();
        String password = passWord.getText().trim();
        for (User user : users) {
            System.out.println(user.getName());
            System.out.println(user.getPassword());
            if (username.equals(user.getName()) && password.equals(user.getPassword())) {
                ds.close();
                Stage loginstage = (Stage) userName.getScene().getWindow();
                loginstage.close();
                ServerController serverController = new ServerController();
                serverController.showLoggedINUI();
                return true;
            }
        }
        applyShakeAnimation(passWord);
        ds.close();
        return false;
    }

    private void applyShakeAnimation(PasswordField field) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(0), new KeyValue(field.translateXProperty(), 0)),
                new KeyFrame(Duration.millis(100), new KeyValue(field.translateXProperty(), -10)),
                new KeyFrame(Duration.millis(200), new KeyValue(field.translateXProperty(), 10)),
                new KeyFrame(Duration.millis(300), new KeyValue(field.translateXProperty(), -10)),
                new KeyFrame(Duration.millis(400), new KeyValue(field.translateXProperty(), 10)),
                new KeyFrame(Duration.millis(500), new KeyValue(field.translateXProperty(), 0))
        );
        timeline.play();
        timeline.setOnFinished(event -> field.getStyleClass().remove("shake"));
        field.getStyleClass().add("shake");
    }

//    private void startServer() {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/Server.fxml"));
//            Parent root = fxmlLoader.load();
//            Scene scene = new Scene(root);
//            Stage serverStage = new Stage();
//            serverStage.setTitle("Server Application");
//            serverStage.setScene(scene);
//            serverStage.show();
//            Stage loginStage = (Stage) userName.getScene().getWindow();
//            loginStage.close();
//            Server server = new Server();
//            server.start(5555);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    @Override
//    public void start(Stage stage) throws Exception {
//        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/com/example/serverclienthicaz/ServerFXML/login.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 334, 320);
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
//    }
}
