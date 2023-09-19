package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.ModelProses.ReceptItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;

public class EditRecepItemController {
    @FXML
    private AnchorPane editReceptPane;
    @FXML
    private TextField nrText;
    @FXML
    private TextField malText;
    @FXML
    private TextField vahidText;
    @FXML
    private TextField miqdarText;
    private Client client;

    private ReceptItem receptItem;

    public void init(Client client, ReceptItem receptItem){
        this.client=client;
        this.receptItem=receptItem;
        nrText.setText(String.valueOf(receptItem.getNr()));
        malText.setText(receptItem.getMal());
        vahidText.setText(receptItem.getVahid());
        miqdarText.setText(String.valueOf(receptItem.getMiqdar()));
    }

    public TextField getNrText() {
        return nrText;
    }

    public TextField getMalText() {
        return malText;
    }

    public TextField getVahidText() {
        return vahidText;
    }

    public TextField getMiqdarText() {
        return miqdarText;
    }

    public void updateItem(){
        client.sendMessage("DELETEEDITRECEPTITEM");
        client.sendMessage(nrText.getText());
        client.sendMessage("INSEERTEDITRECEPTITEMS");
        client.sendMessage(nrText.getText());
        client.sendMessage(malText.getText());
        client.sendMessage(vahidText.getText());
        client.sendMessage(miqdarText.getText());
        client.sendMessage(String.valueOf(receptItem.getReceptNr()));
    }

    public void createMalSiyahi(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(editReceptPane.getScene().getWindow());
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
            malText.clear();
            vahidText.clear();
            miqdarText.clear();
            malText.setText(controller.selectedMal().getAd());
            vahidText.setText(controller.selectedMal().getVahid());

        }

    }

}
