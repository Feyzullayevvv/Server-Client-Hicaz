package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.ModelProses.Recept;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReceptAdminController {
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
    @FXML
    private TableColumn<Recept,String> sonQaliq;
    private ObservableList<Recept> receptObservableList = FXCollections.observableArrayList();
    private List<Recept> receptList = new ArrayList<>();

    private Client client;
    private Pane pane;



    public void init(Client client){
        this.client=client;
        nr.setCellValueFactory(new PropertyValueFactory<>("nr"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        itki.setCellValueFactory(new PropertyValueFactory<>("itki"));
        qaliq.setCellValueFactory(new PropertyValueFactory<>("qaliq"));
        sonQaliq.setCellValueFactory(new PropertyValueFactory<>("sonQaliq"));
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
                dialog.initStyle(StageStyle.UTILITY);
                ReceptInfoDialogAdmin controller = fxmlLoader.getController();
                controller.init(client,selectedItem);
                dialog.show();

                populate();

            }
        }
    }

    public void createNewRecept(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/NewRecept.fxml"));
            pane = loader.load();
            NewReceptController controller = loader.getController();
            controller.init(client);
            setNode(pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
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
    @FXML
    public void handleKeyPressed(KeyEvent keyEvent){
        Recept selectedItem = receptTableView.getSelectionModel().getSelectedItem();
        if (selectedItem!=null){
            if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)){
                deleteItem(selectedItem);
            }
        }
    }
    public void  deleteItem(Recept recept){
        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Məxarici silmək");
            alert.setHeaderText("Ad: " + recept.getName()+ "\nQaliq : " + recept.getQaliq() + "\nItki :" + recept.getItki());
            alert.setContentText("Əminsiniz?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                client.sendMessage("DELETERECEPT");
                client.sendMessage(String.valueOf(recept.getNr()));
                populate();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert  alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    private void setNode(Node node) {
        receptPane.getChildren().clear();
        receptPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }

}
