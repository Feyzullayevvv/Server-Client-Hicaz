package com.example.serverclienthicaz.Server.ProsesAnbarGUI;

import com.example.serverclienthicaz.Server.ModelProses.Emeliyyat;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML
    private TextField searchField;

    private ProsesClient prosesClient;

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
        prosesClient.sendMessage("GETEMELIYYATLIST");
        emeliyyatList= (List<Emeliyyat>) prosesClient.objectReader();
        for (Emeliyyat emeliyyat: emeliyyatList){
            emeliyyatObservableList.add(emeliyyat);
        }
        emeliyyatTableView.setItems(emeliyyatObservableList);
    }

    public void createNewEmeliyyat(){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/serverclienthicaz/ProsesFxml/NewEmeliyyat.fxml"));
                Pane = loader.load();
                NewEmeliyyatController controller = loader.getController();
                controller.setClient(prosesClient);
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
                fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/ProsesFxml/EmeliyyatInfoDialog.fxml"));
                try {
                    dialog.getDialogPane().setContent(fxmlLoader.load());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                dialog.initStyle(StageStyle.UTILITY);
                EmeliyyatInfoDialog controller = fxmlLoader.getController();
                controller.init(prosesClient,selectedItem);
                dialog.show();

                populate();

            }
        }
    }


    public void setClient(ProsesClient prosesClient) {
        this.prosesClient = prosesClient;
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
}
