package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.ModelProses.Recept;
import com.example.serverclienthicaz.Server.ModelProses.ReceptItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReceptInfoDialogAdmin {
    @FXML
    private AnchorPane receptItemPane;
    @FXML
    private TableView<ReceptItem> receptItemTableView;
    @FXML
    private TableColumn<ReceptItem,String> nr;
    @FXML
    private TableColumn<ReceptItem,String> mal;
    @FXML
    private TableColumn<ReceptItem,String> vahid;
    @FXML
    private TableColumn<ReceptItem,String> miqdar;


    private Client client;
    private Recept recept;

    private ObservableList<ReceptItem> receptItemObservableList = FXCollections.observableArrayList();
    private List<ReceptItem> receptItemList= new ArrayList<>();

    public void init(Client client, Recept recept){
        this.client = client;
        this.recept=recept;
        nr.setCellValueFactory(new PropertyValueFactory<>("nr"));
        mal.setCellValueFactory(new PropertyValueFactory<>("mal"));
        vahid.setCellValueFactory(new PropertyValueFactory<>("vahid"));
        miqdar.setCellValueFactory(new PropertyValueFactory<>("miqdar"));
        populate();
    }
    public void handleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            if (receptItemTableView.getSelectionModel().getSelectedItem() != null) {
                ReceptItem selectedItem = receptItemTableView.getSelectionModel().getSelectedItem();
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(receptItemPane.getScene().getWindow());
                dialog.setTitle("Information");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/editReceptItem.fxml"));
                try {
                    dialog.getDialogPane().setContent(fxmlLoader.load());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.initStyle(StageStyle.UTILITY);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
                dialog.initStyle(StageStyle.UTILITY);
                EditRecepItemController controller = fxmlLoader.getController();
                controller.init(client,selectedItem);
                Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setDisable(true);

                controller.getMiqdarText().textProperty().addListener((observable, oldValue, newValue) -> {
                    updateOkButtonDisableProperty(okButton,controller.getMiqdarText());
                });

                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    controller.updateItem();
                }
                populate();


            }
        }
    }
    private void updateOkButtonDisableProperty(Button okButton, TextField textField) {
        if (textField.getText().isEmpty() || !areFieldsValid(textField)){
            okButton.setDisable(true);
        }else{
            okButton.setDisable(false);
        }
    }
    public boolean areFieldsValid(TextField n) {

        String itemPrice = n.getText();

        if (itemPrice.isEmpty()) {
            return false;
        }

        try {
            double itemValue = Double.parseDouble(itemPrice);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void populate(){
        receptItemObservableList.clear();
        receptItemList.clear();
        client.sendMessage("GETRECEPTINFO");
        client.sendMessage(String.valueOf(recept.getNr()));
        receptItemList= (List<ReceptItem>) client.objectReader();
        for (ReceptItem r: receptItemList){
            receptItemObservableList.add(r);
        }
        receptItemTableView.setItems(receptItemObservableList);
    }

    public void yeniButtonClicked(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(receptItemPane.getScene().getWindow());
        dialog.setTitle("Information");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/addNewReceptItemInfoPane.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.initStyle(StageStyle.UTILITY);
        AddNewItemReceptInfoPane controller = fxmlLoader.getController();
        controller.init(client,recept);
        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);

        controller.getMiqdarTextField().textProperty().addListener((observable, oldValue, newValue) -> {
            updateOkButtonDisableProperty(okButton,controller.getMiqdarTextField(),controller.getMalTextField());
        });
        controller.getMalTextField().textProperty().addListener((observable, oldValue, newValue) -> {
            updateOkButtonDisableProperty(okButton,controller.getMiqdarTextField(),controller.getMalTextField());
        });

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            controller.updateRecept();
        }
        populate();


    }

    private void updateOkButtonDisableProperty(Button okButton, TextField miqdarTextField, TextField malTextField) {
        if (miqdarTextField.getText().isEmpty() || !areFieldsValid(miqdarTextField) || malTextField.getText().isEmpty()){
            okButton.setDisable(true);
        }else{
            okButton.setDisable(false);
        }
    }

}
