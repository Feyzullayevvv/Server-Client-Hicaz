package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.ModelAnbar.MexaricFaktura;
import com.example.serverclienthicaz.Server.ModelProses.ReceptItem;
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

public class NewReceptController {
    @FXML
    private AnchorPane newReceptPane;
    @FXML
    private TextField receptAdiText;
    @FXML
    private TextField itkitext;
    @FXML
    private TextField qaliqText;
    @FXML
    private TextField sonQaliqText;
    @FXML
    private TextField maltext;
    @FXML
    private TextField vahidText;
    @FXML
    private TextField miqdartext;

    @FXML
    private Button elaveEtButton;
    @FXML
    private Button silButton;
    @FXML
    private Button bitirButton;
    @FXML
    private TableView<ReceptItem> receptItemsTableView;
    @FXML
    private TableColumn<ReceptItem,String> mal;
    @FXML
    private TableColumn<ReceptItem,String> vahid;
    @FXML
    private TableColumn<ReceptItem,String> miqdar;

    private ObservableList<ReceptItem> receptItemObservableList = FXCollections.observableArrayList();
    private List<ReceptItem> receptItemList = new ArrayList<>();
    private Client client;
    private Pane pane;

    public void init(Client client){
        this.client=client;
        mal.setCellValueFactory(new PropertyValueFactory<>("mal"));
        vahid.setCellValueFactory(new PropertyValueFactory<>("vahid"));
        miqdar.setCellValueFactory(new PropertyValueFactory<>("miqdar"));
        receptAdiText.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        itkitext.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        qaliqText.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        sonQaliqText.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        maltext.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        vahidText.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        miqdartext.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        itkitext.textProperty().addListener((observable, oldValue, newValue) -> updateSonQaliq());
        qaliqText.textProperty().addListener((observable, oldValue, newValue) -> updateSonQaliq());
        silButton.setDisable(true);
        initList();

    }


    public void createMalSiyahi(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(newReceptPane.getScene().getWindow());
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
            maltext.setText(controller.selectedMal().getAd());
            vahidText.setText(controller.selectedMal().getVahid());
        }

    }
    private void updateElaveEtButtonState() {
        if (maltext.getText().isEmpty() || vahidText.getText().isEmpty() || miqdartext.getText().isEmpty() || receptAdiText.getText().isEmpty()
                || itkitext.getText().isEmpty() || qaliqText.getText().isEmpty() || sonQaliqText.getText().isEmpty()){
            elaveEtButton.setDisable(true);
        }else{
            sonQaliqText.setText(String.valueOf(Double.parseDouble(qaliqText.getText())-(Double.parseDouble(qaliqText.getText())/100*Double.parseDouble(itkitext.getText()))));
            elaveEtButton.setDisable(false);
        }
    }
    private void updateSonQaliq(){
        if (!itkitext.getText().isEmpty() && !qaliqText.getText().isEmpty()){
            sonQaliqText.setText(String.valueOf(Double.parseDouble(qaliqText.getText())-(Double.parseDouble(qaliqText.getText())/100*Double.parseDouble(itkitext.getText()))));
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

    public void onElaveEtClicked(){
        ReceptItem receptItem = new ReceptItem();
        receptItem.setMal(maltext.getText());
        receptItem.setVahid(vahidText.getText());
        receptItem.setMiqdar(Double.parseDouble(miqdartext.getText()));
        receptItemList.add(receptItem);
        initList();
        maltext.clear();
        vahidText.clear();
        miqdartext.clear();
    }

    public void onSilClicked(){
        ReceptItem selectedItem = receptItemsTableView.getSelectionModel().getSelectedItem();
        try {
            receptItemList.remove(selectedItem);
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

    public void initList(){
        receptItemObservableList.clear();
        for (ReceptItem receptItem: receptItemList){
            receptItemObservableList.add(receptItem);
        }
        receptItemsTableView.setItems(receptItemObservableList);
        bitirButton.setDisable(receptItemObservableList.isEmpty());
        silButton.setDisable(receptItemObservableList.isEmpty());
    }
    public void handleBack() {
        try {
            receptItemList.clear();
            receptItemObservableList.clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/ReceptAdmin.fxml"));
            pane = loader.load();
            ReceptAdminController controller = loader.getController();
            controller.init(client);
            setNode(pane);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void handleBitti(){
        client.sendMessage("INSERTRECEPT");
        client.sendMessage(receptAdiText.getText());
        client.sendMessage(itkitext.getText());
        client.sendMessage(qaliqText.getText());
        client.sendMessage(sonQaliqText.getText());
        int nr  = Integer.parseInt(client.reader());
        System.out.println(nr);
        if (nr!=-1){
            client.sendMessage("INSERTRECEPTITEM");
            for (ReceptItem r : receptItemList){
                client.sendMessage(r.getMal() + "/" + r.getVahid() + "/" +  r.getMiqdar() + "/" + nr);
            }
            client.sendMessage("DONE");
            try {
                receptItemList.clear();
                receptItemObservableList.clear();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/ReceptAdmin.fxml"));
                pane = loader.load();
                ReceptAdminController controller = loader.getController();
                controller.init(client);
                setNode(pane);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Xəta");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }

    }
    private void setNode(Node node) {
        newReceptPane.getChildren().clear();
        newReceptPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }


}
