package com.example.serverclienthicaz.Server.AdminGUI;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class NewHazirMalAdminController {
    private Client client;

    @FXML
    private TextField hazirMehsulName;


    public void setClient(Client client) {
        this.client = client;
    }

    public TextField getHazirMehsulName() {
        return hazirMehsulName;
    }


    public void insertHazirMal(){
        try {
            client.sendMessage("INSERTHAZIRMAL");
            client.sendMessage(hazirMehsulName.getText());
            String r= client.reader();
            if (r.equals("-1")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Xəta");
                alert.showAndWait();
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
