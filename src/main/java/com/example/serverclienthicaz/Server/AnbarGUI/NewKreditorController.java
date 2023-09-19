package com.example.serverclienthicaz.Server.AnbarGUI;


import com.example.serverclienthicaz.Server.ModelAnbar.Kreditor;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class NewKreditorController{
    private Kreditor kreditor;

    @FXML
    private TextField kreditorName;


    private AnbarClient anbarClient;




    public void init(AnbarClient anbarClient) {
        this.anbarClient = anbarClient;
    }

    public TextField getKreditorName() {
        return kreditorName;
    }


    public void insertCustomer(){
        try {
            anbarClient.sendMessage("INSERTKREDITOR");
            anbarClient.sendMessage(kreditorName.getText());
        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
