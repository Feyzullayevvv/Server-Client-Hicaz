package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.ModelProses.*;
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
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmeliyyatController {
    @FXML
    private AnchorPane emeliyyatPane;
    @FXML
    private TableView<Emeliyyat> emeliyyatTableView;
    @FXML
    private TableColumn<Emeliyyat,String> nr;
    @FXML
    private TableColumn<Emeliyyat,String> tarix;

    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;

    private Client client;

    private AnchorPane Pane;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");


    private ObservableList<Emeliyyat> emeliyyatObservableList = FXCollections.observableArrayList();
    private List<Emeliyyat> emeliyyatList = new ArrayList<>();


    public void init(){
        nr.setCellValueFactory(new PropertyValueFactory<>("nr"));
        tarix.setCellValueFactory(new PropertyValueFactory<>("date"));

        populate();
        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> shoowTehvilBetweenDates());
        endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> shoowTehvilBetweenDates());
    }

    public void populate(){
        emeliyyatList.clear();
        emeliyyatObservableList.clear();
        client.sendMessage("GETEMELIYYATLIST");
        emeliyyatList= (List<Emeliyyat>) client.objectReader();
        for (Emeliyyat emeliyyat: emeliyyatList){
            emeliyyatObservableList.add(emeliyyat);
        }
        emeliyyatTableView.setItems(emeliyyatObservableList);
    }

    public void createNewEmeliyyat(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/NewEmeliyyatAdmin.fxml"));
            Pane = loader.load();
            NewEmeliyyatAdminControlelr controller = loader.getController();
            controller.setClient(client);
            controller.init();
            setNode(Pane);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            e.printStackTrace();
            alert.setHeaderText("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
    public void shoowTehvilBetweenDates() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (startDate != null && endDate != null) {
            try {
                Date startDateObj = dateFormatter.parse(startDate.toString());
                Date endDateObj = dateFormatter.parse(endDate.toString());

                List<Emeliyyat> filteredSales = emeliyyatList.stream()
                        .filter(emeliyyat -> {
                            try {
                                Date paymentDate = dateFormatter.parse(emeliyyat.getDate());
                                return (paymentDate.equals(startDateObj) || paymentDate.after(startDateObj))
                                        && (paymentDate.before(endDateObj) || paymentDate.equals(endDateObj));
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return false;
                            }
                        })
                        .collect(Collectors.toList());

                emeliyyatObservableList.clear();

                emeliyyatObservableList.addAll(filteredSales);
            } catch (Exception e){
                System.out.println(e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Xəta");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }




    public void handleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            if (emeliyyatTableView.getSelectionModel().getSelectedItem() != null) {
                Emeliyyat selectedItem = emeliyyatTableView.getSelectionModel().getSelectedItem();
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(emeliyyatPane.getScene().getWindow());
                dialog.setTitle("Information");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/EmeliyyatInfoDialogAdmin.fxml"));
                try {
                    dialog.getDialogPane().setContent(fxmlLoader.load());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                dialog.initStyle(StageStyle.UTILITY);
                EmeliyyatInfoAdmin controller = fxmlLoader.getController();
                controller.init(client,selectedItem);
                dialog.show();

                populate();

            }
        }
    }


    public void setClient(Client client) {
        this.client = client;
    }
    private void setNode(Node node) {
        emeliyyatPane.getChildren().clear();
        emeliyyatPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }
    @FXML
    public void handleKeyPressed(KeyEvent keyEvent){
        Emeliyyat selectedItem = emeliyyatTableView.getSelectionModel().getSelectedItem();
        if (selectedItem!=null){
            if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)){
                deleteItem(selectedItem);
            }
        }
    }
    public void  deleteItem(Emeliyyat emeliyyat){
        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Əməliyyatı silmək");
            alert.setHeaderText("Nr : " + emeliyyat.getNr() + "\nTarix: " + emeliyyat.getDate());
            alert.setContentText("Əminsiniz?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                client.sendMessage("GETEMELIYYATINFO");
                client.sendMessage(String.valueOf(emeliyyat.getNr()));
                List<EmeliyyatItem> e=(List<EmeliyyatItem>) client.objectReader();
                client.sendMessage("DELETEEMELIYYAT");
                client.sendMessage(String.valueOf(emeliyyat.getNr()));
                client.sendMessage("UPDATEHAZIRMEHSUL");
                for (EmeliyyatItem emeliyyatItem : e){
                    client.sendMessage(emeliyyatItem.getMal() + "/" + emeliyyatItem.getMiqdar());
                }
                client.sendMessage("DONE");

                for (EmeliyyatItem emeliyyatItem : e){
                    client.sendMessage("GETPROSESANBAR");
                   List<VirtualAnbar> virtualAnbarList = (List<VirtualAnbar>) client.objectReader();
                    client.sendMessage("GETRECEPT");
                    client.sendMessage(String.valueOf(emeliyyatItem.getReceptNr()));
                    Recept recept = (Recept) client.objectReader();
                    client.sendMessage("GETRECEPTINFO");
                    client.sendMessage(String.valueOf(emeliyyatItem.getReceptNr()));
                    List<ReceptItem> receptItemList= (List<ReceptItem>) client.objectReader();
                    for (ReceptItem r : receptItemList){
                        for (VirtualAnbar virtualAnbar: virtualAnbarList){
                            if (r.getMal().equals(virtualAnbar.getMal())){
                                virtualAnbar.setCeki(virtualAnbar.getMiqdar()+(r.getMiqdar()*emeliyyatItem.getMiqdar())/recept.getSonQaliq());
                            }
                        }
                    }
                    client.sendMessage("UPDATEPROSESANBAR");

                    for (VirtualAnbar virtualAnbar : virtualAnbarList){
                        client.sendMessage(virtualAnbar.getMalNr() + "/" + virtualAnbar.getMiqdar());
                    }
                    client.sendMessage("DONE");

                }

                init();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert  alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
