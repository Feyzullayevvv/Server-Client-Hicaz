package com.example.serverclienthicaz.Server.AnbarGUI;


import com.example.serverclienthicaz.Server.ModelAnbar.Medaxil;
import com.example.serverclienthicaz.Server.ModelAnbar.MedaxilFaktura;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class MedaxilinFoController {
    @FXML
    private AnchorPane medaxilInfoPane;

    @FXML
    private TextField tarixTextField;
    @FXML
    private TextField kreditorTextField;
    @FXML
    private TextField meblegTextField;
    @FXML
    private TableView<MedaxilFaktura> medaxilInfoTableView;
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

    private Pane pane;

    private Medaxil medaxil;
    private AnbarClient anbarClient;
    private static ObservableList<MedaxilFaktura> medaxilFakturaObservableList= FXCollections.observableArrayList();
    private static List<MedaxilFaktura> medaxilFakturaList= new ArrayList<>();



    public void init(AnbarClient anbarClient) {
        this.anbarClient = anbarClient;
        MalNr.setCellValueFactory(new PropertyValueFactory<>("MalNr"));
        Mal.setCellValueFactory(new PropertyValueFactory<>("Mal"));
        Vahid.setCellValueFactory(new PropertyValueFactory<>("Vahid"));
        Ceki.setCellValueFactory(new PropertyValueFactory<>("Ceki"));
        Mebleg.setCellValueFactory(new PropertyValueFactory<>("Mebleg"));

        tarixTextField.setText(medaxil.getTarix());
        kreditorTextField.setText(medaxil.getKreditor());
        meblegTextField.setText(String.valueOf(medaxil.getMebleg()));
        initList();

    }

    public void setMedaxil(Medaxil medaxil){
        this.medaxil=medaxil;
    }

    private void setNode(Node node) {
        medaxilInfoPane.getChildren().clear();
        medaxilInfoPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }
    public void initList(){
        medaxilFakturaObservableList.clear();
        medaxilFakturaList.clear();
        anbarClient.sendMessage("GETMEDAXILINFO");
        anbarClient.sendMessage(String.valueOf(medaxil.getNr()));
        medaxilFakturaList=(List<MedaxilFaktura>) anbarClient.listReader();

        for (MedaxilFaktura medaxilFaktura:medaxilFakturaList){
            medaxilFakturaObservableList.add(medaxilFaktura);
        }
        medaxilInfoTableView.setItems(medaxilFakturaObservableList);
    }


    public void handleBack(){
        try {
            medaxilFakturaList.clear();
            medaxilFakturaObservableList.clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AnbarFxml/Medaxil.fxml"));
            pane = loader.load();
            MedaxilController controller = loader.getController();
            controller.init(anbarClient);
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
