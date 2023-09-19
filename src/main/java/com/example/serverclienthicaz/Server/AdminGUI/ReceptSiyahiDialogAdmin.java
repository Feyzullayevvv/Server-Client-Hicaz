package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.ModelProses.Recept;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReceptSiyahiDialogAdmin {
    @FXML
    private AnchorPane receptPane;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Recept> receptTableView;
    @FXML
    private TableColumn<Recept,String> nr;
    @FXML
    private TableColumn<Recept,String> name;
    @FXML
    private TableColumn<Recept,String> itki;
    @FXML
    private TableColumn<Recept,String> qaliq;
    private ObservableList<Recept> receptObservableList = FXCollections.observableArrayList();
    private List<Recept> receptList = new ArrayList<>();

    private Client client;



    public void init(){
        nr.setCellValueFactory(new PropertyValueFactory<>("nr"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        itki.setCellValueFactory(new PropertyValueFactory<>("itki"));
        qaliq.setCellValueFactory(new PropertyValueFactory<>("qaliq"));
        populate();
    }

    public void populate(){
        receptObservableList.clear();
        receptList.clear();
        client.sendMessage("GETRECEPTLIST");
        receptList = (List<Recept>) client.objectReader();
        for (Recept r : receptList){
            receptObservableList.add(r);
        }
        receptTableView.setItems(receptObservableList);
    }
    public void handleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            if (receptTableView.getSelectionModel().getSelectedItem() != null) {
                Recept selectedItem = receptTableView.getSelectionModel().getSelectedItem();
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(receptPane.getScene().getWindow());
                dialog.setTitle("Information");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/ReceptInfoDialogAdmin.fxml"));
                try {
                    dialog.getDialogPane().setContent(fxmlLoader.load());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                dialog.initStyle(StageStyle.TRANSPARENT);
                ReceptInfoDialogAdmin controller = fxmlLoader.getController();
                controller.init(client,selectedItem);
                dialog.show();

                populate();

            }
        }
    }
    private void Search(ObservableList<Recept> receptObservableList, String malName) {
        receptObservableList.clear();
        String lowercaseName = malName.toLowerCase();
        for (int i = 0; i < receptList.size(); i++) {
            if (receptList.get(i).getName().toLowerCase().contains(lowercaseName)) {
                receptObservableList.add(receptList.get(i));
            }
        }
    }

    public void handleKeyReleased(KeyEvent event){
        if (event.getEventType() == KeyEvent.KEY_RELEASED){
            String name = searchField.getText();

            Search(receptObservableList,name);
        }
    }

    public void setClient(Client Client) {
        this.client = Client;
    }

    public Recept selectedRecept(){
        return receptTableView.getSelectionModel().getSelectedItem();
    }
}
