package sample.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.entity.Filmy;
import sample.entity.Sale;
import sample.entity.Seanse;
import sample.jdbc.DatabaseConnector;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class DodajSeansController implements Initializable {

    @FXML
    public ListView<Seanse> listaSeansowList;
    @FXML
    public ComboBox<Filmy> filmyComboBox;
    @FXML
    public DatePicker datePicker;
    @FXML
    public TextField godzinaText;
    @FXML
    public TextField minutaText;
    @FXML
    public ComboBox<Sale> salaComboBox;
    @FXML
    public Button dodajSeanseBtn;
    @FXML
    public Button anulujBtn;
    @FXML
    public Button dodajDoListyBtn;
    public Button usunSeansZListyBtn;

    private Date data;

    private String minuta;
    private String godzina;

    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wypelnijFilmyComboBox();
        ustawDate();
        sprawdzPrawidlowoscMinuty();
        sprawdzPrawidlowoscGodziny();
        wypelnijSaleComboBox();

    }

    private void wypelnijFilmyComboBox(){
        List<Filmy> filmy = DatabaseConnector.getInstance().queryFilmy();

        ObservableList<Filmy> obList = FXCollections.observableArrayList(filmy);
        filmyComboBox.getItems().addAll(obList);

    }

    @FXML
    private void ustawDate(){
        datePicker.setValue(LocalDate.now());
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
        datePicker.setDayCellFactory(dayCellFactory);
        data =java.sql.Date.valueOf(datePicker.getValue());
        datePicker.valueProperty().addListener((observable, oldValue, newValue) ->
                data =java.sql.Date.valueOf(datePicker.getValue()));
    }

    @FXML
    public void sprawdzPrawidlowoscGodziny(){
        godzinaText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (godzinaText.getText().matches("([01]?[0-9]|2[0-3])")) {
                godzina = godzinaText.getText();
            } else {
                godzinaText.clear();
            }

        });
    }

    @FXML
    public void sprawdzPrawidlowoscMinuty(){
        minutaText.textProperty().addListener((observable, oldValue, newValue) -> {
            if(minutaText.getText().matches("[0-5]?[0-9]")){
                minuta = minutaText.getText();
            } else {
                minutaText.clear();
            }
        });
    }

    private void wypelnijSaleComboBox(){
        List<Sale> sale = DatabaseConnector.getInstance().querySale();

        ObservableList<Sale> obList = FXCollections.observableArrayList(sale);
        salaComboBox.getItems().addAll(obList);

    }

    public void dodajDoListyAction(ActionEvent actionEvent) {

        if( data.toString().trim().isEmpty() ||
            godzinaText.getText().trim().isEmpty() ||
            minutaText.getText().trim().isEmpty() ||
            salaComboBox.getSelectionModel().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("Brak wszystkich danych.");
            alert.showAndWait();
            return;
        } else {
            Seanse seans = new Seanse();

            seans.setDataSeansu(stworzNowaDate(godzina, minuta, data));
            seans.setIdSali(salaComboBox.getSelectionModel().getSelectedItem().getIdSali());

            listaSeansowList.getItems().add(seans);
        }
    }

    private Date stworzNowaDate(String godzina, String minuta, Date data){

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
        cal.setTime(data);
        int year = cal.get(Calendar.YEAR)-1900;
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = Integer.valueOf(godzina);
        int minute = Integer.valueOf(minuta);

        Date nowaData = new Date(year, month, day, hour, minute);

        return nowaData;
    }

    public void usunSeansZListy(ActionEvent actionEvent) {

        if(listaSeansowList.getSelectionModel().isEmpty()){
            return;
        } else{
            listaSeansowList.getItems().remove(listaSeansowList.getSelectionModel().getSelectedIndex());
        }
    }

    public void dodajSeanse(ActionEvent actionEvent) {

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Filmy film = filmyComboBox.getSelectionModel().getSelectedItem();

        ObservableList<Seanse> seanse = listaSeansowList.getItems();

        seanse.forEach(seans -> {
            try {
                String data = formater.format(seans.getDataSeansu());
                DatabaseConnector.getInstance().utworzSeans(film.getIdFilmu(),seans.getIdSali(),data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    public void anuluj(ActionEvent actionEvent) {
        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
