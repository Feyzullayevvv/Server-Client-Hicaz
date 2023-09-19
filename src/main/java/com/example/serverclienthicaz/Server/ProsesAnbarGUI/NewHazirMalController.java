package com.example.serverclienthicaz.Server.ProsesAnbarGUI;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class NewHazirMalController {
    private ProsesClient prosesClient;

    @FXML
    private TextField hazirMehsulName;


    public void setClient(ProsesClient prosesClient) {
        this.prosesClient = prosesClient;
    }

    public TextField getHazirMehsulName() {
        return hazirMehsulName;
    }


    public void insertHazirMal(){
        try {
            prosesClient.sendMessage("INSERTHAZIRMAL");
            prosesClient.sendMessage(hazirMehsulName.getText());
            String r= prosesClient.reader();
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
