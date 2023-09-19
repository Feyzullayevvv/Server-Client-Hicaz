package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.ModelProses.HazirAnbar;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class HazirAnbarEditAdminController {
    @FXML
    private TextField cekiText;
    @FXML
    private TextField malText;
    private Client client;
    private HazirAnbar hazirAnbarMal;
    public void  init(Client client, HazirAnbar hazirAnbar){
        this.client = client;
        this.hazirAnbarMal=hazirAnbar;
        malText.setText(hazirAnbar.getMal());
        cekiText.setText(String.valueOf(hazirAnbar.getMiqdar()));
    }

    public TextField getCekiText() {
        return cekiText;
    }

    public void updateAnbarMal(){
        client.sendMessage("UPDATEHAZIRANBARAMOUNT");
        client.sendMessage(String.valueOf(hazirAnbarMal.getId()));
        client.sendMessage(String.valueOf(cekiText.getText()));
    }
}
