package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.ModelAnbar.XammalMal;
import com.example.serverclienthicaz.Server.ModelProses.Recept;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;

public class AddNewItemReceptInfoPane {
    @FXML
    private AnchorPane yeniReceptpane;
    private Client client;
    private Recept recept;
    private XammalMal mal;
    @FXML
    private TextField miqdarTextField;
    @FXML
    private TextField malTextField;


    public void init(Client client,Recept recept){
        this.client=client;
        this.recept=recept;
    }

    public TextField getMiqdarTextField() {
        return miqdarTextField;
    }

    public TextField getMalTextField() {
        return malTextField;
    }

    public void updateRecept(){
        client.sendMessage("INSERTRECEPTITEM");
        client.sendMessage(mal.getAd() + "/" + mal.getVahid() + "/" +  miqdarTextField.getText() + "/" + recept.getNr());
        client.sendMessage("DONE");
    }

    public void createMalSiyahi(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(yeniReceptpane.getScene().getWindow());
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
            malTextField.clear();
            malTextField.setText(controller.selectedMal().getAd());
        }

    }
}
