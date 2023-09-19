package com.example.serverclienthicaz.Server.ProsesAnbarGUI;

import com.example.serverclienthicaz.Server.ModelProses.*;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NewEmeliyyatController {
    @FXML
    private AnchorPane newEmeliyyatPane;
    @FXML
    private TableView<VirtualAnbar> virtualAnbarTableView;
    @FXML
    private TableColumn<VirtualAnbar,String> nr;
    @FXML
    private TableColumn<VirtualAnbar,String> malNr;
    @FXML
    private TableColumn<VirtualAnbar,String> mal;
    @FXML
    private TableColumn<VirtualAnbar,String> vahid;
    @FXML
    private TableColumn<VirtualAnbar,String> virtualMiqdar;
    @FXML
    private TextField receptInfoText;
    @FXML
    private TextField receptInfoNrText;
    @FXML
    private TextField receptInfoItkiText;
    @FXML
    private TextField receptInfoQaliqText;
    @FXML
    private TextField malText;

    @FXML
    private TextField miqdarText;



    @FXML
    private TableView<ReceptItem> receptTableView;
    @FXML
    private TableColumn<ReceptItem,String> receptNr;
    @FXML
    private TableColumn<ReceptItem,String> receptMal;
    @FXML
    private TableColumn<ReceptItem,String> receptVahid;
    @FXML
    private TableColumn<ReceptItem,String> receptMiqdar;
    @FXML
    private TableColumn<ReceptItem,String> receptReceptNr;

    @FXML
    private TableView<EmeliyyatItem> emeliyyatTableView;
    @FXML
    private TableColumn<EmeliyyatItem,String> emeliyyatMal;
    @FXML
    private TableColumn<EmeliyyatItem,String> emeliyyatVahid;
    @FXML
    private TableColumn<EmeliyyatItem,String> emeliyyatMiqdar;
    @FXML
    private TableColumn<EmeliyyatItem,String> emeliyyatReceptNr;

    @FXML
    private Button silButton;
    @FXML
    private Button elaveEtButton;
    @FXML
    private Button bittiButton;
    @FXML
    private Button geriButton;

    @FXML
    private DatePicker datePicker;

    private Pane pane;

    private HazirMal hazirMal;

    private Recept recept;


    private ObservableList<ReceptItem> receptItemObservableList= FXCollections.observableArrayList();
    private List<ReceptItem> receptItemList= new ArrayList<>();

    private ObservableList<EmeliyyatItem> emeliyyatItemObservableList = FXCollections.observableArrayList();
    private List<EmeliyyatItem> emeliyyatItemList = new ArrayList<>();
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    private ObservableList<VirtualAnbar> virtualAnbarObservableList = FXCollections.observableArrayList();
    private List<VirtualAnbar> virtualAnbarList = new ArrayList<>();


    private ProsesClient prosesClient;



    public void init(){
        nr.setCellValueFactory(new PropertyValueFactory<>("nr"));
        malNr.setCellValueFactory(new PropertyValueFactory<>("malNr"));
        mal.setCellValueFactory(new PropertyValueFactory<>("mal"));
        vahid.setCellValueFactory(new PropertyValueFactory<>("vahid"));
        virtualMiqdar.setCellValueFactory(new PropertyValueFactory<>("miqdar"));
        receptNr.setCellValueFactory(new PropertyValueFactory<>("nr"));
        receptMal.setCellValueFactory(new PropertyValueFactory<>("mal"));
        receptVahid.setCellValueFactory(new PropertyValueFactory<>("vahid"));
        receptMiqdar.setCellValueFactory(new PropertyValueFactory<>("miqdar"));
        receptReceptNr.setCellValueFactory(new PropertyValueFactory<>("receptNr"));
        emeliyyatMal.setCellValueFactory(new PropertyValueFactory<>("mal"));
        emeliyyatVahid.setCellValueFactory(new PropertyValueFactory<>("vahid"));
        emeliyyatMiqdar.setCellValueFactory(new PropertyValueFactory<>("miqdar"));
        emeliyyatReceptNr.setCellValueFactory(new PropertyValueFactory<>("receptNr"));
        elaveEtButton.setDisable(true);
        bittiButton.setDisable(true);

        receptInfoText.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        receptInfoNrText.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        receptInfoItkiText.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        receptInfoQaliqText.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        malText.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());

        miqdarText.textProperty().addListener((observable, oldValue, newValue) -> updateElaveEtButtonState());
        initList();
        gettvirtualAnbar();
        datePicker.setValue(LocalDate.now());
    }
    private void updateElaveEtButtonState() {
        if (receptInfoText.getText().isEmpty() || receptInfoNrText.getText().isEmpty() || receptInfoItkiText.getText().isEmpty() || receptInfoQaliqText.getText().isEmpty() || malText.getText().isEmpty() ||
                miqdarText.getText().isEmpty() || !areFieldsValid(miqdarText) || !checkRecept()) {
            elaveEtButton.setDisable(true);
        } else {
            elaveEtButton.setDisable(false);
        }
    }

    private boolean checkRecept(){
        for (ReceptItem receptItem : receptItemList) {
            boolean found = false;
            for (VirtualAnbar item : virtualAnbarList) {
                if (item.getMal().equals(receptItem.getMal()) && item.getVahid().equals(receptItem.getVahid())) {
                    found = true;
                    break;

                }
            }

            if (!found) {
                return false;
            }
        }

        return true;
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


    public void createMalSiyahi(){
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(newEmeliyyatPane.getScene().getWindow());
            dialog.setTitle("Mal seçmək");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/ProsesFxml/HazirMalSiyahiDialog.fxml"));
            try {
                dialog.getDialogPane().setContent(fxmlLoader.load());
                HazirMalSechimiDialog controller = fxmlLoader.getController();
                controller.setClient(prosesClient);
                controller.init();
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.initStyle(StageStyle.UTILITY);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                Optional<ButtonType> result = dialog.showAndWait();
                if ((result.isPresent() && result.get() == ButtonType.OK) && controller.selectedMal() != null) {
                    hazirMal=controller.selectedMal();
                    malText.setText(controller.selectedMal().getName());
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Xəta");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

    }
    public void gettvirtualAnbar(){
        virtualAnbarList.clear();
        virtualAnbarObservableList.clear();
        prosesClient.sendMessage("GETPROSESANBAR");
        virtualAnbarList = (List<VirtualAnbar>) prosesClient.objectReader();
        for (VirtualAnbar a : virtualAnbarList){
            virtualAnbarObservableList.add(a);
        }
        virtualAnbarTableView.setItems(virtualAnbarObservableList);
    }


    public void createReceptSiyahi(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(newEmeliyyatPane.getScene().getWindow());
        dialog.setTitle("Reçept seçmək");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/ProsesFxml/ReceptSiyahiDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
            ReceptSiyahiDialog controller = fxmlLoader.getController();
            controller.setClient(prosesClient);
            controller.init();
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            Optional<ButtonType> result = dialog.showAndWait();
            if ((result.isPresent() && result.get() == ButtonType.OK) && controller.selectedRecept() != null) {
                receptItemList.clear();
                receptItemObservableList.clear();
                recept=controller.selectedRecept();
                receptInfoText.setText(recept.getName());
                receptInfoNrText.setText(String.valueOf(recept.getNr()));
                receptInfoItkiText.setText(String.valueOf(recept.getItki()));
                receptInfoQaliqText.setText(String.valueOf(recept.getQaliq()));
                prosesClient.sendMessage("GETRECEPTINFO");
                prosesClient.sendMessage(String.valueOf(recept.getNr()));
                receptItemList= (List<ReceptItem>) prosesClient.objectReader();
                for (ReceptItem r: receptItemList){
                    receptItemObservableList.add(r);
                }
                receptTableView.setItems(receptItemObservableList);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void handleBack(){
        try {
            receptItemList.clear();
            receptItemObservableList.clear();
           virtualAnbarList.clear();
           virtualAnbarObservableList.clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/ProsesFxml/Emeliyyat.fxml"));
            pane = loader.load();
            pane.setStyle("-fx-background-color: #d1d0d0;");
            EmeliyyatController controller = loader.getController();
            controller.setClient(prosesClient);
            controller.init();
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
        newEmeliyyatPane.getChildren().clear();
        newEmeliyyatPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }

    public void handleElaveEt(){
        try {
            emeliyyatItemObservableList.clear();
            EmeliyyatItem emeliyyatItem = new EmeliyyatItem();
            emeliyyatItem.setMal(hazirMal.getName());
            emeliyyatItem.setVahid("kg");
            emeliyyatItem.setMiqdar(Double.parseDouble(miqdarText.getText()));
            emeliyyatItem.setReceptNr(recept.getNr());
            emeliyyatItemList.add(emeliyyatItem);
            for (EmeliyyatItem e: emeliyyatItemList){
                emeliyyatItemObservableList.add(e);
            }
            emeliyyatTableView.setItems(emeliyyatItemObservableList);

            for (ReceptItem receptItem : receptItemList){
                for (VirtualAnbar virtualAnbar: virtualAnbarList){
                    if (receptItem.getMal().equals(virtualAnbar.getMal())){
                        virtualAnbar.setCeki(virtualAnbar.getMiqdar()-(receptItem.getMiqdar()*Double.parseDouble(miqdarText.getText()))/recept.getSonQaliq());
                    }
                }
            }
            virtualAnbarObservableList.clear();
            for (VirtualAnbar v : virtualAnbarList){
                virtualAnbarObservableList.add(v);
            }

            virtualAnbarTableView.setItems(virtualAnbarObservableList);
            updateElaveEtButtonState();
            clearAllComponents();
            initList();

        }catch (Exception e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
    public void clearAllComponents() {
        receptItemList.clear();
        receptItemObservableList.clear();
        receptInfoText.clear();
        receptInfoNrText.clear();
        receptInfoItkiText.clear();
        receptInfoQaliqText.clear();
        malText.clear();
        miqdarText.clear();
        receptTableView.getItems().clear();
        elaveEtButton.setDisable(true);
        bittiButton.setDisable(true);
    }
    public void initList(){
        emeliyyatItemObservableList.clear();
        for (EmeliyyatItem emeliyyatItem:emeliyyatItemList){
            emeliyyatItemObservableList.add(emeliyyatItem);
        }
        emeliyyatTableView.setItems(emeliyyatItemObservableList);
        if (emeliyyatItemObservableList.isEmpty()) {
            bittiButton.setDisable(true);
        } else {
            bittiButton.setDisable(false);
        }
    }
    public void handleDelete(){
        EmeliyyatItem selectedItem = emeliyyatTableView.getSelectionModel().getSelectedItem();
        try {
            emeliyyatItemList.remove(selectedItem);
            prosesClient.sendMessage("GETRECEPTINFO");
            prosesClient.sendMessage(String.valueOf(selectedItem.getReceptNr()));
            List<ReceptItem> temporaryList= (List<ReceptItem>) prosesClient.objectReader();
            for (ReceptItem receptItem : temporaryList){
                for (VirtualAnbar v: virtualAnbarList){
                    if (receptItem.getMal().equals(v.getMal())){
                        v.setCeki(v.getMiqdar()+(receptItem.getMiqdar()*selectedItem.getMiqdar())/recept.getSonQaliq());
                    }
                }
            }
            virtualAnbarObservableList.clear();
            for (VirtualAnbar v :virtualAnbarList){
                virtualAnbarObservableList.add(v);
            }
            virtualAnbarTableView.setItems(virtualAnbarObservableList);
            initList();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            Alert  alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    public void handleBitti(){
        try {
            prosesClient.sendMessage("INSERTEMELIYYAT");
            prosesClient.sendMessage(datePicker.getValue().toString());
            int emeliyyatNr = Integer.parseInt(prosesClient.reader());
            prosesClient.sendMessage("INSERTEMELIYYATITEM");
            for (EmeliyyatItem emeliyyatItem : emeliyyatItemList){
                prosesClient.sendMessage(emeliyyatItem.getMal() + "/" + emeliyyatItem.getVahid() + "/"
                        +emeliyyatItem.getMiqdar() + "/" + emeliyyatItem.getReceptNr() + "/"  + emeliyyatNr);
            }
            prosesClient.sendMessage("DONE");
            prosesClient.sendMessage("UPDATEPROSESANBAR");
            for (VirtualAnbar virtualAnbar : virtualAnbarList){
                prosesClient.sendMessage(virtualAnbar.getMalNr() + "/" + virtualAnbar.getMiqdar());
            }
            prosesClient.sendMessage("DONE");
            prosesClient.sendMessage("INCREASEHAZIRMEHSULANBAR");
            for (EmeliyyatItem emeliyyatItem : emeliyyatItemList){
                prosesClient.sendMessage(emeliyyatItem.getMal() + "/" + emeliyyatItem.getMiqdar());
            }
            prosesClient.sendMessage("DONE");
            receptItemList.clear();
            receptItemObservableList.clear();
            virtualAnbarList.clear();
            virtualAnbarObservableList.clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/ProsesFxml/Emeliyyat.fxml"));
            pane = loader.load();
            pane.setStyle("-fx-background-color: #d1d0d0;");
            EmeliyyatController controller = loader.getController();
            controller.setClient(prosesClient);
            controller.init();
            setNode(pane);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }





    public void setClient(ProsesClient prosesClient) {
        this.prosesClient = prosesClient;
    }
}
