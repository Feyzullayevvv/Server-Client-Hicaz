package com.example.serverclienthicaz.Server.ProsesAnbarGUI;

import  com.example.serverclienthicaz.Server.ModelProses.HazirAnbar;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class HazirAnbarEditController {
    @FXML
    private TextField cekiText;
    @FXML
    private TextField malText;
    private ProsesClient prosesClient;
    private HazirAnbar hazirAnbarMal;
    public void  init(ProsesClient prosesClient, HazirAnbar hazirAnbar){
        this.prosesClient = prosesClient;
        this.hazirAnbarMal=hazirAnbar;
        malText.setText(hazirAnbar.getMal());
        cekiText.setText(String.valueOf(hazirAnbar.getMiqdar()));
    }

    public TextField getCekiText() {
        return cekiText;
    }

    public void updateAnbarMal(){
        prosesClient.sendMessage("UPDATEHAZIRANBARAMOUNT");
        prosesClient.sendMessage(String.valueOf(hazirAnbarMal.getId()));
        prosesClient.sendMessage(String.valueOf(cekiText.getText()));
    }
}
