package sample.controller;

import com.sun.rowset.internal.Row;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import sample.entity.*;


import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class BiletyController implements Initializable {

    @FXML
    private GridPane biletyGrid;

    private Transakcje transakcja;
    private List<Bilety> bilety;
    private List <Miejsca> miejsca;
    private Seanse seans;
    private Filmy film;

    private SimpleIntegerProperty wartośćTransakcji;

    public Transakcje getTransakcja() {
        return transakcja;
    }

    public void setTransakcja(Transakcje transakcja) {
        this.transakcja = transakcja;
    }

    public List<Bilety> getBilety() { return bilety; }

    public void setBilety(List<Bilety> bilety) {
        this.bilety = bilety;
    }

    public List<Miejsca> getMiejsca() {
        return miejsca;
    }

    public void setMiejsca(List<Miejsca> miejsca) {
        this.miejsca = miejsca;
    }

    public Seanse getSeans() {
        return seans;
    }

    public void setSeans(Seanse seans) {
        this.seans = seans;
    }

    public Filmy getFilm() {
        return film;
    }

    public void setFilm(Filmy film) {
        this.film = film;
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void przygotujWidok(){

        // 0. Przygotowanie zmiennej przechowującej aktualną wartość transakcji.

        transakcja.setWartoscTransakcji(20 * this.bilety.size());

        // 1. Przygotowanie etykiety tytułowej z tytułem filmu, datą seansu i nr sali.
        Label tytułFilmu = new Label("Film: " + film.getTytul());
        biletyGrid.add(tytułFilmu,1,1, 2, 1);

        Label dataSeansu = new Label("Data seansu: " + seans.toString());
        biletyGrid.add(dataSeansu,1,2);

        Label nrSali = new Label("Nr sali: " + seans.getIdSali());
        biletyGrid.add(nrSali,2,2);

        // 2. Przygotowanie sekcji z biletami. Ma się składać z etykiety z nr biletu, etykiety z nr miejsca i rzędem
        //    oraz comboBox z wyborem biletu - normalny lub ulgowy.
        Iterator biletyIterator = bilety.iterator();
        Iterator miejscaIterator = miejsca.iterator();
        int offset = 0;


        while(biletyIterator.hasNext() && miejscaIterator.hasNext()){
            Bilety b = (Bilety) biletyIterator.next();
            Miejsca m = (Miejsca) miejscaIterator.next();

            RowConstraints row = new RowConstraints();
            row.setMinHeight(50);
            row.setMaxHeight(50);
            biletyGrid.getRowConstraints().add(row);

            Label biletLabel = new Label("Bilet nr: " + b.getIdBiletu() + "\n" + "Rząd: " + m.getRzad() + " Miejsce: " + m.getNrMiejsca());

            biletLabel.setStyle("-fx-label-padding: 0 0 0 30;");

            biletyGrid.add(biletLabel, 1, 4 + offset);

            ObservableList <String> typyBiletów = FXCollections.observableArrayList("Normalny", "Ulgowy");
            final ComboBox typBiletuCombo = new ComboBox(typyBiletów);
            typBiletuCombo.getSelectionModel().select(0);
            typBiletuCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue == "Ulgowy")
                    transakcja.setWartoscTransakcji(transakcja.getWartoscTransakcji() - 5);
                else
                    transakcja.setWartoscTransakcji(transakcja.getWartoscTransakcji() + 5);
            });
            biletyGrid.add(typBiletuCombo, 2, 4 + offset);

            offset++;

        }

        // 3. Przygotowanie sekcji z podsumowaniem transakcji. Etykieta z aktualną kwotą.
        Label transakcjaTekst = new Label("Wartość transakcji: ");
        biletyGrid.add(transakcjaTekst, 1, 4 + offset);
        Label wartośćTransakcjiEtykieta = new Label( transakcja.getWartoscTransakcji() + ",00 zł");
        transakcja.wartoscTransakcjiProperty().addListener((observable, oldValue, newValue) -> {
            wartośćTransakcjiEtykieta.textProperty().setValue(newValue.toString() + ",00 zł");
        });
        biletyGrid.add(wartośćTransakcjiEtykieta, 2,4 + offset);
    }
}
