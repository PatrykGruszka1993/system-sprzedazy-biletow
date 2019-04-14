package sample.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import sample.entity.Filmy;
import sample.entity.Miejsca;
import sample.entity.Seanse;
import sample.jdbc.DatabaseConnector;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import java.util.Date;

public class Controller implements Initializable {

    @FXML
    private GridPane seat_grid;

    @FXML
    private ComboBox<Filmy> comboBoxFilm;

    @FXML
    private ComboBox<Seanse> comboBoxSeans;

    @FXML
    private DatePicker calendarPicker;

    private Date data;

    private Button seat;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ustawDate();
        wczytajFilmy();
        wczytajSeans();
        wczytajMiejsca();
    }

    @FXML
    private void ustawDate(){
        calendarPicker.setValue(LocalDate.now());
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isBefore(LocalDate.now())){
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    }
                };
        calendarPicker.setDayCellFactory(dayCellFactory);
        data =java.sql.Date.valueOf(calendarPicker.getValue());
        calendarPicker.valueProperty().addListener((observable, oldValue, newValue) ->
                data =java.sql.Date.valueOf(calendarPicker.getValue()));
    }

    @FXML
    public void wczytajFilmy(){
        calendarPicker.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            comboBoxFilm.getItems().clear();
            List<Filmy> filmy = DatabaseConnector.getInstance().queryFilmyDlaDaty(data);
            if (newValue == null) {
                comboBoxFilm.setDisable(true);
            } else {
                ObservableList<Filmy> obList = FXCollections.observableArrayList(filmy);
                comboBoxFilm.getItems().addAll(obList);
            }
        });
    }

    @FXML
    public void wczytajSeans(){
        comboBoxFilm.valueProperty().addListener((observable, oldValue, newValue) -> {
            comboBoxSeans.getItems().clear();
            if(newValue == null){
                comboBoxSeans.getItems().clear();
                comboBoxSeans.setDisable(true);
            } else {
                List<Seanse> seanse = DatabaseConnector.getInstance().querySeansDlaDanegoFilmu(newValue, data);
                comboBoxSeans.getItems().setAll(seanse);
                comboBoxSeans.setDisable(false);
            }
        });
    }

    @FXML
    private void wczytajMiejsca(){
        comboBoxSeans.valueProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue != null){
                    List<Miejsca> miejsca = DatabaseConnector.getInstance().znajdzZajeteMiejsca(newValue);
                    miejsca.forEach(miejsce->{
                        seat = getButtonFromGrid(seat_grid, miejsce);
                        seat.setDisable(true);
                            });
                }
                });
    }

    private Button getButtonFromGrid (GridPane grid, Miejsca miejsce){
        for (Node child : grid.getChildren()){
            if(GridPane.getColumnIndex(child) + 1 == miejsce.getNrMiejsca() && GridPane.getRowIndex(child) - 3 == miejsce.getRzad())
                return (Button) child;
        }
        return null;
    }
}
