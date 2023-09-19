package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.ModelAnbar.AnbarItem;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class XammalAnbarEditController {

    @FXML
    private TextField malText;
    @FXML
    private TextField cekiText;
    @FXML
    private TextField meblegText;
    private AnbarItem anbarItem;
    private Client client;

    public void init(Client client,AnbarItem anbarItem){
        this.client=client;
        this.anbarItem=anbarItem;
        malText.setText(anbarItem.getMal());
        cekiText.setText(String.valueOf(anbarItem.getCeki()));
        meblegText.setText(String.valueOf(anbarItem.getMebleg()));

    }

    public TextField getCekiText() {
        return cekiText;
    }

    public TextField getMeblegText() {
        return meblegText;
    }

    public void updateXammal(){
        client.sendMessage("UPDATEXAMMALMAL");
        client.sendMessage(String.valueOf(anbarItem.getNr()));
        client.sendMessage(cekiText.getText());
        client.sendMessage(meblegText.getText());
    }
}
