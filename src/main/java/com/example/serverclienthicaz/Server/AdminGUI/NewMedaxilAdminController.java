package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.AnbarGUI.AnbarClient;
import com.example.serverclienthicaz.Server.AnbarGUI.KreditorSechimiDialog;
import com.example.serverclienthicaz.Server.AnbarGUI.MalSechimiDialog;
import com.example.serverclienthicaz.Server.AnbarGUI.MedaxilController;
import com.example.serverclienthicaz.Server.ModelAnbar.MedaxilFaktura;
import com.example.serverclienthicaz.Server.ModelAnbar.XammalMal;
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

public class NewMedaxilAdminController {

    @FXML
    private TableView<MedaxilFaktura> newMedaxilTableView;
    @FXML
    private TableColumn<MedaxilFaktura,String> MalNr;
    @FXML
    private TableColumn<MedaxilFaktura,String> Mal;
    @FXML
    private TableColumn<MedaxilFaktura,String> Vahid;
    @FXML
    private TableColumn<MedaxilFaktura,String> Ceki;
    @FXML
    private TableColumn<MedaxilFaktura,String> Mebleg;

    @FXML
    private AnchorPane newMedaxilPane;

    @FXML
    private TextField malText;
    @FXML
    private TextField cekitext;

    @FXML
    private TextField vahidText;
    @FXML
    private TextField qiymetText;

    @FXML
    private TextField meblegText;

    @FXML
    private TextField kreditorText;

    @FXML
    private Button elaveEtButton;
    @FXML
    private Button bittiButton;

    private Client client;


    private Pane pane;

    private static ObservableList<MedaxilFaktura> medaxilFakturaObservableList= FXCollections.observableArrayList();
    private static List<MedaxilFaktura> medaxilFakturaList= new ArrayList<>();





    public void init(Client client) {
        this.client = client;
        MalNr.setCellValueFactory(new PropertyValueFactory<>("MalNr"));
        Mal.setCellValueFactory(new PropertyValueFactory<>("Mal"));
        Vahid.setCellValueFactory(new PropertyValueFactory<>("Vahid"));
        Ceki.setCellValueFactory(new PropertyValueFactory<>("Ceki"));
        Mebleg.setCellValueFactory(new PropertyValueFactory<>("Mebleg"));
        initList();
        malText.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        cekitext.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        qiymetText.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        kreditorText.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
    }


    public void createMalSiyahi(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(newMedaxilPane.getScene().getWindow());
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
            malText.setText(controller.selectedMal().getAd());
            vahidText.setText(controller.selectedMal().getVahid());
        }

    }
    public void createKreditorSiyahi(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(newMedaxilPane.getScene().getWindow());
        dialog.setTitle("Kreditor Seçimi");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/KreditorSiyahiAdmin.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        KreditorSechimiDialogAdmin controller = fxmlLoader.getController();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        controller.initialize(client);
        Optional<ButtonType> result= dialog.showAndWait();
        if ((result.isPresent() && result.get()== ButtonType.OK) && controller.selectedKreditor()!=null){
            kreditorText.setText(controller.selectedKreditor().getName());

        }

    }

    public void initList(){
        medaxilFakturaObservableList.clear();
        for (MedaxilFaktura medaxilFaktura:medaxilFakturaList){
            medaxilFakturaObservableList.add(medaxilFaktura);
        }
        newMedaxilTableView.setItems(medaxilFakturaObservableList);
        if (medaxilFakturaObservableList.isEmpty()) {
            bittiButton.setDisable(true);
        } else {
            bittiButton.setDisable(false);
        }
    }

    private void updateElaveEtButtonState() {
        if (malText.getText().isEmpty() || cekitext.getText().isEmpty() || qiymetText.getText().isEmpty() || kreditorText.getText().isEmpty() || !areFieldsValid(cekitext)){
            elaveEtButton.setDisable(true);
        }else{
            meblegText.setText(String.valueOf(Double.parseDouble(cekitext.getText())*Double.parseDouble(qiymetText.getText())));
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




    public void onElaveEtClicked(){
        MedaxilFaktura medaxilFaktura= new MedaxilFaktura();
        client.sendMessage("GETXAMMALMALINFO");
        client.sendMessage(malText.getText());
        XammalMal xammalMal = (XammalMal) client.objectReader();

        medaxilFaktura.setMalNr(xammalMal.getNr());
        medaxilFaktura.setMal(xammalMal.getAd());
        medaxilFaktura.setVahid(xammalMal.getVahid());
        medaxilFaktura.setCeki(Double.parseDouble(cekitext.getText()));
        medaxilFaktura.setMebleg(Double.parseDouble(meblegText.getText()));
        medaxilFakturaList.add(medaxilFaktura);
        initList();
        malText.clear();
        cekitext.clear();
        vahidText.clear();
        qiymetText.clear();
        meblegText.clear();
    }

    public void handleDelete(){
        MedaxilFaktura selectedItem = newMedaxilTableView.getSelectionModel().getSelectedItem();
        try {
            medaxilFakturaList.remove(selectedItem);
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

    public void handleBitti(){
        client.sendMessage("INSERTMEDAXIL");
        client.sendMessage(kreditorText.getText());
        int n = Integer.parseInt(client.reader());
        if(n!=-1){
            client.sendMessage("INSERTMEDAXILFAKTURA");
            for (MedaxilFaktura medaxilFaktura: medaxilFakturaList){
                client.sendMessage(medaxilFaktura.getMal()+ "/" + medaxilFaktura.getCeki()+ "/" +medaxilFaktura.getMebleg()+ "/" +n);
            }
            client.sendMessage("DONE");

        }

        try {
            medaxilFakturaList.clear();
            medaxilFakturaObservableList.clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/MedaxilAdmin.fxml"));
            pane = loader.load();
            MedaxilAdminController controller = loader.getController();
            controller.init(client);
            setNode(pane);
        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    private void setNode(Node node) {
        newMedaxilPane.getChildren().clear();
        newMedaxilPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }

    public void handleBack(){
        try {
            medaxilFakturaList.clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/MedaxilAdmin.fxml"));
            pane = loader.load();
            MedaxilAdminController controller = loader.getController();
            controller.init(client);
            setNode(pane);
        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


}
