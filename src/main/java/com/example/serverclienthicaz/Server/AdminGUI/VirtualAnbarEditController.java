package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.ModelProses.VirtualAnbar;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class VirtualAnbarEditController {
    @FXML
    private TextField miqdarText;
    @FXML
    private TextField malText;
    @FXML
    private TextField vahidText;
    @FXML
    private TextField malNrText;

    private Client client;
    private VirtualAnbar item;
    public void  init(Client client, VirtualAnbar item){
        this.client = client;
        this.item=item;
        malNrText.setText(String.valueOf(item.getMalNr()));
        vahidText.setText(item.getVahid());
        malText.setText(item.getMal());
        miqdarText.setText(String.valueOf(item.getMiqdar()));
    }

    public TextField getMiqdarText() {
        return miqdarText;
    }

    public void updateAnbarMal(){
        client.sendMessage("UPDATEPROSESANBARBYID");
        client.sendMessage(String.valueOf(item.getMalNr()));
        client.sendMessage(String.valueOf(miqdarText.getText()));
    }
}
