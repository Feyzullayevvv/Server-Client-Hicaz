package com.example.serverclienthicaz.Server.ProsesAnbarGUI;

import com.example.serverclienthicaz.Server.ModelProses.HazirAnbar;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NewHazirMehsulController {
    @FXML
    private AnchorPane NewHazirAnbar;
    @FXML
    private TextField mehsulText;
    @FXML
    private TextField miqdarText;
    @FXML
    private Button elaveEtButton;
    @FXML
    private Button silButton;
    @FXML
    private Button bittiButton;
    @FXML
    private TableView<HazirAnbar> malTableView;
    @FXML
    private TableColumn<HazirAnbar,String> mal;
    @FXML
    private TableColumn<HazirAnbar,String> vahid;
    @FXML
    private TableColumn<HazirAnbar,String> miqdar;
    private Pane pane;
    private ProsesClient prosesClient;

    private List<HazirAnbar> hazirAnbarList ;
    private ObservableList<HazirAnbar> hazirAnbarObservableList;

    public void init(){
        hazirAnbarList = new ArrayList<>();
        hazirAnbarObservableList = FXCollections.observableArrayList();
        mal.setCellValueFactory(new PropertyValueFactory<>("mal"));
        vahid.setCellValueFactory(new PropertyValueFactory<>("vahid"));
        miqdar.setCellValueFactory(new PropertyValueFactory<>("miqdar"));
        mehsulText.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        miqdarText.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        hazirAnbarList.clear();
        hazirAnbarObservableList.clear();
        initList();
        elaveEtButton.setDisable(true);
    }
    public void initList(){
        hazirAnbarObservableList.clear();
        for (HazirAnbar hazirAnbar:hazirAnbarList){
            hazirAnbarObservableList.add(hazirAnbar);
        }
        malTableView.setItems(hazirAnbarObservableList);
        if (hazirAnbarObservableList.isEmpty()) {
            bittiButton.setDisable(true);
        } else {
            bittiButton.setDisable(false);
        }
    }
    public void onElaveEtClicked(){
        HazirAnbar hazirAnbar = new HazirAnbar();
        hazirAnbar.setMal(mehsulText.getText());
        hazirAnbar.setVahid("kg");
        hazirAnbar.setId(0);
        hazirAnbar.setMiqdar(Double.parseDouble(miqdarText.getText()));
        hazirAnbarList.add(hazirAnbar);
        mehsulText.clear();
        miqdarText.clear();
        initList();
    }
    private void updateElaveEtButtonState() {
        if (mehsulText.getText().isEmpty() || miqdarText.getText().isEmpty() || !areFieldsValid(miqdarText)){
            elaveEtButton.setDisable(true);
        }else{
            elaveEtButton.setDisable(false);
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

    public void handleBack(){
        try {
            hazirAnbarList.clear();
            hazirAnbarObservableList.clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/ProsesFxml/HazirMehsulAnbar.fxml"));
            pane = loader.load();
            pane.setStyle("-fx-background-color: #e6e1e1;");
            HazirMehsulAnbarController hazirMehsulAnbarController = loader.getController();
            hazirMehsulAnbarController.setClient(prosesClient);
            hazirMehsulAnbarController.init();
            setNode(pane);
        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void handleDelete(){
        HazirAnbar selectedItem = malTableView.getSelectionModel().getSelectedItem();
        try {
            hazirAnbarList.remove(selectedItem);
            initList();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            Alert  alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    private void setNode(Node node) {
        NewHazirAnbar.getChildren().clear();
        NewHazirAnbar.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }
    public void setClient(ProsesClient prosesClient){
        this.prosesClient = prosesClient;
    }

    public void handleBitti(){
            try {
                prosesClient.sendMessage("INSERTANBARNEWMEHSUL");
                for (HazirAnbar hazirAnbar : hazirAnbarList){
                    prosesClient.sendMessage(hazirAnbar.getMal() + "/" + hazirAnbar.getMiqdar());
                }
                prosesClient.sendMessage("DONE");

                hazirAnbarList.clear();
                hazirAnbarObservableList.clear();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/ProsesFxml/HazirMehsulAnbar.fxml"));
                pane = loader.load();
                pane.setStyle("-fx-background-color: #e6e1e1;");
                HazirMehsulAnbarController hazirMehsulAnbarController = loader.getController();
                hazirMehsulAnbarController.setClient(prosesClient);
                hazirMehsulAnbarController.init();
                setNode(pane);
            }catch (Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Xəta");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }


    }
    public void createMalSiyahi(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(NewHazirAnbar.getScene().getWindow());
        dialog.setTitle("Mal seçmək");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/ProsesFxml/HazirMalSiyahiDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
            HazirMalSechimiDialog controller = fxmlLoader.getController();
            controller.setClient(prosesClient);
            controller.init();
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            Optional<ButtonType> result = dialog.showAndWait();
            if ((result.isPresent() && result.get() == ButtonType.OK) && controller.selectedMal() != null) {
                mehsulText.setText(controller.selectedMal().getName());
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }



}

