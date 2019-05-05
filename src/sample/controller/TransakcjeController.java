package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import sample.Main;
import sample.entity.Bilety;
import sample.entity.Transakcje;
import sample.jdbc.DatabaseConnector;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TransakcjeController implements Initializable {

    @FXML
    private TextField transaction_actual_page;
    @FXML
    private Label transaction_max_page;


    @FXML
    private TextField tickets_actual_page;
    @FXML
    private Label tickets_max_page;


    @FXML
    private RadioMenuItem sprzedażViewMenuItem;
    
    @FXML
    private ListView transactionListView;
    @FXML
    private ListView ticketListView;

    private List<Transakcje> transakcjeList;
    private Transakcje actualTransaction;
    private ObservableList<String> transakcjeStringList;
    private int transakcjeMaxPage;

    private List<Bilety> biletyList;
    private Bilety actualBilet;
    private ObservableList<String> biletyStringList;
    private int biletyMaxPage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wczytajTransakcje();
        obserwujZmianęStronyBiletów();
        obserwujZmianęAktualnejTransakcji();
        obserwujZmianęAktualnegoBiletu();

        sprzedażViewMenuItem.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/sample.fxml"));
                Parent root = loader.load();

                Main.getPrimaryStage().setScene(new Scene(root, 1024,768));
                Main.getPrimaryStage().show();
            }catch (Exception e){
                e.printStackTrace();
            }

        });
    }

    private void wczytajTransakcje(){
        wczytajStronęTransakcji(1);
        transaction_actual_page.textProperty().addListener((observable, oldValue, newValue) -> {
            wczytajStronęTransakcji(Integer.parseInt(newValue));
        });
    }

    private void wczytajStronęTransakcji(int page){
        transakcjeList = DatabaseConnector.getInstance().pobierzTransakcje(page);
        transakcjeStringList = FXCollections.observableArrayList();
        for (Transakcje t: transakcjeList
        ) {
            transakcjeStringList.add("ID: #" + t.getIdTransakcji() + " Wartość: " + t.getWartoscTransakcji() + ".00 zł");
        }

        transactionListView.setItems(transakcjeStringList);
        actualTransaction = transakcjeList.get(0);

        transakcjeMaxPage = DatabaseConnector.getInstance().liczbaStronTransakcji();
        transaction_max_page.setText(" / " + transakcjeMaxPage);
        if(transakcjeMaxPage == 1)
            transaction_actual_page.setDisable(true);
        wczytajStronęBiletów(1);
    }

    private void wczytajStronęBiletów(int page){
        biletyList = DatabaseConnector.getInstance().pobierzBiletyDlaTransakcji(actualTransaction.getIdTransakcji(), page);
        biletyStringList = FXCollections.observableArrayList();
        for (Bilety b: biletyList
        ) {
            biletyStringList.add("ID BILETU: #" + b.getIdBiletu());
        }
        ticketListView.setItems(biletyStringList);
        actualBilet = biletyList.get(0);

        biletyMaxPage = DatabaseConnector.getInstance().liczbaStronBiletów(actualTransaction.getIdTransakcji());
        tickets_max_page.setText(" / " + biletyMaxPage);
        if(biletyMaxPage == 1)
            tickets_actual_page.setDisable(true);
    }

    private void obserwujZmianęStronyBiletów(){
        tickets_actual_page.textProperty().addListener((observable, oldValue, newValue) -> {
            wczytajStronęBiletów(Integer.parseInt(newValue));
        });
    }

    private void obserwujZmianęAktualnejTransakcji(){
        transactionListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                actualTransaction = transakcjeList.get(transactionListView.getSelectionModel().getSelectedIndex());
                wczytajStronęBiletów(1);
            }
        });
    }

    private void obserwujZmianęAktualnegoBiletu(){
        ticketListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                actualBilet = biletyList.get(ticketListView.getSelectionModel().getSelectedIndex());
                System.out.println(actualBilet.getIdBiletu());
            }
        });
    }
}
