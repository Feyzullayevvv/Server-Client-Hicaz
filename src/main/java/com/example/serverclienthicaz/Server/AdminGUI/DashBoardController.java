package com.example.serverclienthicaz.Server.AdminGUI;

import com.example.serverclienthicaz.Server.ModelAnbar.AnbarItem;
import com.example.serverclienthicaz.Server.ModelAnbar.Mexaric;
import com.example.serverclienthicaz.Server.ModelAnbar.MexaricFaktura;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DashBoardController {
    @FXML
    private TextField anbarQaliq;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TableView<AnbarItem> anbarTableView;
    @FXML
    private TableColumn<AnbarItem,String> Nr;
    @FXML
    private TableColumn<AnbarItem,String> xammalMal;
    @FXML
    private TableColumn<AnbarItem,String> vahid;
    @FXML
    private TableColumn<AnbarItem,String> miqdar;
    @FXML
    private TableView<MexaricFaktura> mexaricInfoTableView;
    @FXML
    private TableColumn<MexaricFaktura,String> MalNr;
    @FXML
    private TableColumn<MexaricFaktura,String> Mal;
    @FXML
    private TableColumn<MexaricFaktura,String> Vahid;
    @FXML
    private TableColumn<MexaricFaktura,String> Ceki;

    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField kgText;
    @FXML
    private TextField metrText;
    @FXML
    private TextField litrText;

    @FXML
    private Label kgLabel;
    @FXML
    private Label metrLabel;
    @FXML
    private Label litrLabel;


    private  double anbarValue=0;
    private double kgValue=0;
    private double litrValue=0;
    private double metrValue=0;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private Client client;
    private static ObservableList<AnbarItem> anbarItemObservableList= FXCollections.observableArrayList();
    private static List<AnbarItem> anbarItemList= new ArrayList<>();
    private static ObservableList<MexaricFaktura> mexaricFakturaObservableList = FXCollections.observableArrayList();
    private static List<MexaricFaktura> mexaricFakturaList = new ArrayList<>();
    private static List<Mexaric> mexaricList = new ArrayList<>();


    public void init(Client client) {
        this.client=client;
        Nr.setCellValueFactory(new PropertyValueFactory<>("Nr"));
        xammalMal.setCellValueFactory(new PropertyValueFactory<>("Mal"));
        vahid.setCellValueFactory(new PropertyValueFactory<>("Vahid"));
        miqdar.setCellValueFactory(new PropertyValueFactory<>("Ceki"));
        MalNr.setCellValueFactory(new PropertyValueFactory<>("MalNr"));
        Mal.setCellValueFactory(new PropertyValueFactory<>("Mal"));
        Vahid.setCellValueFactory(new PropertyValueFactory<>("Vahid"));
        Ceki.setCellValueFactory(new PropertyValueFactory<>("Ceki"));
        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> showMexaricBetweenDates());
        endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> showMexaricBetweenDates());

        initFields();
        choiceBox.getItems().addAll("Hesablama", "Tükənən Mallar");
        choiceBox.setValue("Hesablama");
        choiceBox.setOnAction(event -> handleChoiceBoxAction());
    }

    private void handleChoiceBoxAction() {
        String selectedOption = choiceBox.getValue();
        if ("Hesablama".equals(selectedOption)) {
            filterHesablama();
        } else if ("Tükənən Mallar".equals(selectedOption)) {
            danger();
        }
    }
    private void initFields(){
        client.sendMessage("QUERYXAMMALANBARITEMS");
        List<AnbarItem> anbarItemList= (List<AnbarItem>) client.listReader();
        for (AnbarItem anbarItem : anbarItemList){
            anbarValue+=anbarItem.getMebleg();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedValue = decimalFormat.format(anbarValue);
        anbarQaliq.setText(formattedValue);

    }

    private void danger(){
        disableHesablama();
        anbarItemObservableList.clear();
        anbarItemList.clear();
        client.sendMessage("QUERYXAMMALANBARITEMS");
        anbarItemList= (List<AnbarItem>) client.listReader();
        for (AnbarItem anbarItem: anbarItemList){
            if (anbarItem.getVahid().equals("metr")){
                if (anbarItem.getCeki()<=1000){
                    anbarItemObservableList.add(anbarItem);
                }
            }if (anbarItem.getVahid().equals("kg")){
                if (anbarItem.getCeki()<=100){
                    anbarItemObservableList.add(anbarItem);
                }
            }if (anbarItem.getVahid().equals("litr")){
                if (anbarItem.getCeki()<=100){
                    anbarItemObservableList.add(anbarItem);
                }
            }if (anbarItem.getVahid().equals("eded")){
                if (anbarItem.getCeki()<=1000){
                    anbarItemObservableList.add(anbarItem);
                }
            }

        }
        if (!anbarItemObservableList.isEmpty()){
            anbarTableView.setVisible(true);
        }
        anbarTableView.setItems(anbarItemObservableList);
    }

    private void disableHesablama(){
        mexaricList.clear();
        mexaricFakturaList.clear();
        mexaricFakturaObservableList.clear();
        mexaricInfoTableView.getItems().clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        kgText.clear();
        metrText.clear();
        litrText.clear();
        mexaricInfoTableView.setVisible(false);
        startDatePicker.setVisible(false);
        endDatePicker.setVisible(false);
        kgText.setVisible(false);
        metrText.setVisible(false);
        litrText.setVisible(false);
        kgLabel.setVisible(false);
        litrLabel.setVisible(false);
        metrLabel.setVisible(false);
    }
    private void ableHesablama(){
        mexaricInfoTableView.setVisible(true);
        startDatePicker.setVisible(true);
        endDatePicker.setVisible(true);
        kgText.setVisible(true);
        metrText.setVisible(true);
        litrText.setVisible(true);


    }
    private void disableDanger(){
        anbarTableView.setVisible(false);
    }

    public void filterHesablama(){
        disableDanger();
        ableHesablama();
    }


    private void showMexaricBetweenDates() {
        queryMexaric();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (startDate != null && endDate != null) {
            try {
                Date startDateObj = dateFormatter.parse(startDate.toString());
                Date endDateObj = dateFormatter.parse(endDate.toString());
                System.out.println(startDate);
                List<Mexaric> filteredSales = mexaricList.stream()
                        .filter(mexaric -> {
                            try {
                                Date date = dateFormatter.parse(mexaric.getTarix());
                                return (date.equals(startDateObj) || date.after(startDateObj))
                                        && (date.before(endDateObj) || date.equals(endDateObj));
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return false;
                            }
                        })
                        .collect(Collectors.toList());

                Map<String, Double> itemNameToQuantity = new HashMap<>();
                Map<String, Integer> itemNameToMalNr = new HashMap<>();

                for (Mexaric m : filteredSales) {
                    System.out.println("hi");
                    client.sendMessage("QUERYMEXERICITEMS");
                    client.sendMessage(String.valueOf(m.getNr()));
                    mexaricFakturaList = (List<MexaricFaktura>) client.listReader();
                    for (MexaricFaktura mexaricFaktura : mexaricFakturaList) {

                        if (mexaricFaktura.getVahid().equals("metr")) {
                            metrValue += mexaricFaktura.getCeki();
                        }
                        if (mexaricFaktura.getVahid().equals("kg")) {
                            kgValue += mexaricFaktura.getCeki();
                        }
                        if (mexaricFaktura.getVahid().equals("litr")) {
                            litrValue += mexaricFaktura.getCeki();
                        }
                        String itemName = mexaricFaktura.getMal();
                        double itemQuantity = mexaricFaktura.getCeki();
                        if (itemNameToQuantity.containsKey(itemName)) {
                            double currentQuantity = itemNameToQuantity.get(itemName);
                            itemNameToQuantity.put(itemName, currentQuantity + itemQuantity);
                        } else {
                            itemNameToQuantity.put(itemName, itemQuantity);
                        }

                        itemNameToMalNr.put(itemName, mexaricFaktura.getMalNr());
                    }
                }

                mexaricInfoTableView.getItems().clear();

                for (Map.Entry<String, Double> entry : itemNameToQuantity.entrySet()) {
                    String itemName = entry.getKey();
                    double itemQuantity = entry.getValue();
                    MexaricFaktura groupedItem = new MexaricFaktura();
                    groupedItem.setMal(itemName);
                    groupedItem.setCeki(itemQuantity);

                    groupedItem.setMalNr(itemNameToMalNr.get(itemName));
                    mexaricFakturaObservableList.add(groupedItem);
                }

                mexaricInfoTableView.setItems(mexaricFakturaObservableList);
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String formattedValue = decimalFormat.format(metrValue);
                metrText.setText(formattedValue);
                String formattedValue2 = decimalFormat.format(kgValue);
                kgText.setText(formattedValue2);
                String formattedValue3 = decimalFormat.format(litrValue);
                litrText.setText(formattedValue3);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Xəta");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }



    private void queryMexaric(){
        mexaricList.clear();
        client.sendMessage("QUERYMEXARIC");
        mexaricList = (List<Mexaric>) client.listReader();
    }

}


