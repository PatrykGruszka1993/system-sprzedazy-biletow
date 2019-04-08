package sample.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import sample.entity.Filmy;
import sample.entity.Seanse;
import sample.jdbc.DatabaseConnector;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ComboBox<Filmy> comboBoxFilm;

    @FXML
    private ComboBox<Seanse> comboBoxSeans;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wczytajFilmy();
        wczytajSeans();
    }

    @FXML
    public void wczytajFilmy(){
        List <Filmy> filmy = DatabaseConnector.getInstance().queryFilmy();
        ObservableList<Filmy> obList = FXCollections.observableArrayList(filmy);
        comboBoxFilm.getItems().addAll(obList);
        comboBoxFilm.getSelectionModel().selectFirst();

    }

    @FXML
    public void wczytajSeans(){
        comboBoxFilm.valueProperty().addListener((observable, oldValue, newValue) -> {
            comboBoxSeans.getItems().clear();
            if(newValue == null){
                comboBoxSeans.getItems().clear();
                comboBoxSeans.setDisable(true);
            } else {
                List<Seanse> seanse = DatabaseConnector.getInstance().querySeansDlaDanegoFilmu(newValue);
                comboBoxSeans.getItems().setAll(seanse);
                comboBoxSeans.setDisable(false);
            }
        });
    }


}
