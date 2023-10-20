package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.ModelAnbar.XammalMal;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;

public class newProsesItemController {
    @FXML
    private AnchorPane newProsesAnbar;
    @FXML
    private TextField malText;
    @FXML
    private TextField cekiText;
    private Client client;
    private XammalMal mal;
    public void init(Client client){
        this.client=client;
    }

    public TextField getMalText() {
        return malText;
    }
    public TextField getCekiText() {
        return cekiText;
    }

    public void insertMal(){
        client.sendMessage("INSERTPROSESANBAR");
        client.sendMessage(String.valueOf(mal.getNr()));
        client.sendMessage(mal.getAd());
        client.sendMessage(mal.getVahid());
        client.sendMessage(cekiText.getText());
    }
    public void createMalSiyahi(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(newProsesAnbar.getScene().getWindow());
        dialog.setTitle("Mal seçmək");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/MalSiyahiDiaglogAdmin.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        MalSechimiDialogAdmin controller = fxmlLoader.getController();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        controller.init(client);
        Optional<ButtonType> result= dialog.showAndWait();
        if ((result.isPresent() && result.get()== ButtonType.OK) && controller.selectedMal()!=null){
            mal=controller.selectedMal();
            malText.setText(controller.selectedMal().getAd());

        }

    }
}
