package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.AnbarGUI.AnbarClient;
import com.example.serverclienthicaz.Server.ModelAnbar.Kreditor;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class newKreditorAdmin {
    private Kreditor kreditor;

    @FXML
    private TextField kreditorName;


    private Client client;




    public void init(Client client) {
        this.client = client;
    }

    public TextField getKreditorName() {
        return kreditorName;
    }


    public void insertCustomer(){
        try {
            client.sendMessage("INSERTKREDITOR");
            client.sendMessage(kreditorName.getText());
        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
