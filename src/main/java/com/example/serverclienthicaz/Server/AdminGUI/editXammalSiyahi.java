package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.ModelAnbar.XammalMal;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class editXammalSiyahi {
    @FXML
    private TextField ortaQiymetText;
    @FXML
    private TextField malText;
    @FXML
    private TextField vahidText;
    private Client client;
    private XammalMal mal;
    public void init(Client client, XammalMal mal){
        this.client=client;
        this.mal=mal;
        malText.setText(mal.getAd());
        vahidText.setText(mal.getVahid());
        ortaQiymetText.setText(String.valueOf(mal.getOrtaGiymet()));
    }

    public TextField getOrtaQiymetText() {
        return ortaQiymetText;
    }
    public void updateXammal(){
        client.sendMessage("UPDATEXAMMALSIYAHI");
        client.sendMessage(String.valueOf(mal.getNr()));
        client.sendMessage(ortaQiymetText.getText());
    }

}
