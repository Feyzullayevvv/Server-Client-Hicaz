package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.ModelProses.Tehvil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TehvilSechimiDialogAdmin {
    @FXML
    private AnchorPane tehvilPane;
    @FXML
    private TableView<Tehvil> tehvilTableView;
    @FXML
    private TableColumn<Tehvil,String> nr;
    @FXML
    private TableColumn<Tehvil,String> tarix;
    @FXML
    private TableColumn<Tehvil,String> tehvilci;
    @FXML
    private ToggleButton toggleButton;
    private ObservableList<Tehvil> tehvilObservableList = FXCollections.observableArrayList();
    private List<Tehvil> tehvilList = new ArrayList<>();
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");


    private Client client;


    public void init(){
        nr.setCellValueFactory(new PropertyValueFactory<>("nr"));
        tarix.setCellValueFactory(new PropertyValueFactory<>("Date"));
        tehvilci.setCellValueFactory(new PropertyValueFactory<>("tehvilci"));
        populate();
        toggleButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            showTodaysTehvil(newValue);
        });

    }
    private void populate(){
        tehvilObservableList.clear();
        tehvilList.clear();
        client.sendMessage("GETTEHVILLIST");
        tehvilList= (List<Tehvil>) client.objectReader();
        for (Tehvil tehvil : tehvilList){
            tehvilObservableList.add(tehvil);
        }
        tehvilTableView.setItems(tehvilObservableList);
    }

    public void showTodaysTehvil(boolean showTodaySales) {
        try {
            LocalDate today = LocalDate.now();
            tehvilObservableList.clear();
            if (showTodaySales) {
                List<Tehvil> filteredSales = tehvilList.stream()
                        .filter(tehvil -> {
                            try {
                                Date saleDate = dateFormatter.parse(tehvil.getDate());
                                LocalDate saleLocalDate = saleDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                return saleLocalDate.isEqual(today);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return false;
                            }
                        })
                        .collect(Collectors.toList());

                tehvilObservableList.addAll(filteredSales);
            } else {
                tehvilObservableList.addAll(tehvilList);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void handleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            if (tehvilTableView.getSelectionModel().getSelectedItem() != null) {
                Tehvil selectedItem = tehvilTableView.getSelectionModel().getSelectedItem();
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(tehvilPane.getScene().getWindow());
                dialog.setTitle("Information");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/serverclienthicaz/AdminFXML/TehvilInfoDialogAdmin.fxml"));
                try {
                    dialog.getDialogPane().setContent(fxmlLoader.load());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                dialog.setResizable(false);
                TehvilInfoDialogAdmin controller = fxmlLoader.getController();
                controller.init(client,selectedItem);
                dialog.show();

                populate();

            }
        }
    }

    public Tehvil getSelectedTehvil(){
        return tehvilTableView.getSelectionModel().getSelectedItem();
    }





    public void setClient(Client client) {
        this.client = client;
    }
}
