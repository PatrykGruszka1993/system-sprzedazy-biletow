package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import sample.Main;
import sample.entity.*;
import sample.jdbc.DatabaseConnector;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

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
    private List <Miejsca> zaznaczoneMiejsca;

    @FXML
    private Button seat;

    @FXML
    private Button ticket_seller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ustawDate();
        wczytajFilmy();
        wczytajSeans();
        wczytajMiejsca();
        zaznaczoneMiejsca = new ArrayList<>();
        disableAllButtons(seat_grid);

        ticket_seller.setOnAction((event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/bilety_view.fxml"));
                Transakcje transakcja = przygotujTransakcje();
                List <Bilety> wybraneBilety = przygotujBilety(transakcja);

                Stage stage = new Stage();
                stage.setTitle("Sprzedaż biletów");
                stage.setScene(new Scene(loader.load(),400,400));
                //zablokowanie okna-rodzica
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(Main.getPrimaryStage());

                stage.show();
            }catch (IOException e){
                e.printStackTrace();
            }
        }));
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
            wyczyscMiejsca(seat_grid);
            if(comboBoxSeans.getSelectionModel().isEmpty()){
                disableAllButtons(seat_grid);
            } else{
                enableAllButtons(seat_grid);
                if(newValue != null){
                    List<Miejsca> miejsca = DatabaseConnector.getInstance().znajdzZajeteMiejsca(newValue);
                    miejsca.forEach(miejsce->{
                        seat = getButtonFromGrid(seat_grid, miejsce);
                        seat.setDisable(true);
                    });
                }

            }
        });
    }

    private void wyczyscMiejsca(GridPane grid){
        for (Node child : grid.getChildren()){
            seat = (Button) child;
            seat.getStyleClass().remove("button-checked");
            seat.getStyleClass().add("button-unchecked");
            zaznaczoneMiejsca.clear();
        }
    }

    private void disableAllButtons(GridPane grid) {
        for (Node child : grid.getChildren()){
            seat = (Button) child;
            seat.setDisable(true);
        }
    }

    private void enableAllButtons (GridPane grid){
        for (Node child : grid.getChildren()){
            seat = (Button) child;
            seat.setDisable(false);
        }
    }

    private Button getButtonFromGrid (GridPane grid, Miejsca miejsce){
        for (Node child : grid.getChildren()){
            if(GridPane.getColumnIndex(child) + 1 == miejsce.getNrMiejsca() && GridPane.getRowIndex(child) - 3 == miejsce.getRzad())
                return (Button) child;
        }
        return null;
    }

    @FXML
    public void zajmijMiejsce(ActionEvent actionEvent) {

        seat = ((Button) actionEvent.getSource());
        String id = seat.getId();
        int rzad = Integer.parseInt(id.substring(5,7));
        int nrMiejsca = Integer.parseInt(id.substring(8,10));
        Seanse wybranySeans = comboBoxSeans.valueProperty().get();

        Miejsca zaznaczoneMiejsce = DatabaseConnector.getInstance().znajdzMiejsce(wybranySeans, nrMiejsca, rzad);

        for(Miejsca miejsce : zaznaczoneMiejsca){
            if(zaznaczoneMiejsce.getIdMiejsca() == miejsce.getIdMiejsca()){
                zaznaczoneMiejsca.remove(miejsce);
                seat.getStyleClass().remove("button-checked");
                seat.getStyleClass().add("button-unchecked");
                return;
            }
        }

        zaznaczoneMiejsca.add(zaznaczoneMiejsce);
        seat.getStyleClass().add("button-checked");
    }

    private Transakcje przygotujTransakcje(){

        Transakcje nowaTransakcja = new Transakcje();
        nowaTransakcja.setWartoscTransakcji(0);
        nowaTransakcja.setIdTransakcji(DatabaseConnector.getInstance().idDlaNowejTransakcji());

        return nowaTransakcja;
    }

    private List <Bilety> przygotujBilety(Transakcje transakcja){
        if(zaznaczoneMiejsca.isEmpty()){
            return Collections.emptyList();
        }else{
            List <Bilety> przygotowaneBilety = new ArrayList<>();
            int następneIDBiletu = DatabaseConnector.getInstance().idDlaNowegoBiletu();
            for (Miejsca miejsce: zaznaczoneMiejsca) {
                Bilety bilet = new Bilety();
                bilet.setIdFilmu(miejsce.getIdFilmu());
                bilet.setIdMiejsca(miejsce.getIdMiejsca());
                bilet.setIdSali(miejsce.getIdSali());
                bilet.setIdSeansu(miejsce.getIdSeansu());
                bilet.setIdTransakcji(transakcja.getIdTransakcji());
                bilet.setTypBiletu("1");
                bilet.setIdBiletu(następneIDBiletu);
                następneIDBiletu++;
                przygotowaneBilety.add(bilet);
            }

            return przygotowaneBilety;
        }
    }
}
