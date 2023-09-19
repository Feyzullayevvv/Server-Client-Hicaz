package com.example.serverclienthicaz.Server.ProsesAnbarGUI;

import com.example.serverclienthicaz.Server.ModelProses.User;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class PasswordDialog {
    @FXML
    private TextField userName;

    @FXML
    private PasswordField passWord;


    @FXML
    private Label wronguserinput;

    @FXML
    private ProsesClient prosesClient;








    @FXML
    public void handleExit(){
        Stage loginStage = (Stage) userName.getScene().getWindow();
        loginStage.close();
    }



    public boolean login(){
        prosesClient.sendMessage("GETADMINPASSWORD");
        List<User> users= (List<User>) prosesClient.objectReader();
        String username= userName.getText().trim();
        String password= passWord.getText().trim();
        for (User user : users){
            if (user.getName().equals("admin")){
                if (username.equals(user.getName()) && password.equals(user.getPassword())){
                    Stage loginStage = (Stage) userName.getScene().getWindow();
                    loginStage.close();
                    return true;
                }
            }
        }
        applyShakeAnimation(passWord);
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
    public void setClient(ProsesClient prosesClient) {
        this.prosesClient = prosesClient;
    }

}
