package sample.controller;

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

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class EdytujSeansController implements Initializable {

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField godzinaText;
    @FXML
    private TextField minutaText;
    @FXML
    private ComboBox<Sale> salaComboBox;
    @FXML
    private ComboBox<Filmy> filmyComboBox;
    @FXML
    private Button edytujSeanseBtn;
    @FXML
    private Button anulujBtn;
    @FXML
    private Label labelSeans;

    @FXML
    private Label labelSala;
    @FXML
    private Label labelFilm;


    private Stage stage;

    private Date data;

    private String minuta;
    private String godzina;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ustawDate();
        sprawdzPrawidlowoscGodziny();
        sprawdzPrawidlowoscMinuty();
        wypelnijSaleComboBox();
        wypelnijFilmyComboBox();
        ustawDane();

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

    public void sprawdzPrawidlowoscGodziny() {
        godzinaText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (godzinaText.getText().matches("([01]?[0-9]|2[0-3])")) {
                godzina = godzinaText.getText();
            } else {
                godzinaText.clear();
            }

        });
    }

    public void sprawdzPrawidlowoscMinuty() {
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

    private void wypelnijFilmyComboBox(){
        List<Filmy> filmy = DatabaseConnector.getInstance().queryFilmy();

        ObservableList<Filmy> obList = FXCollections.observableArrayList(filmy);
        filmyComboBox.getItems().addAll(obList);

    }

    private void ustawDane(){
        ObservableList<Sale> saleOb = salaComboBox.getItems();
        saleOb.forEach(sala -> {
            int idSali= sala.getIdSali();
            if(idSali == Integer.parseInt(labelSala.getText())){
                salaComboBox.getSelectionModel().select(sala);
            }
        });

        ObservableList<Filmy> filmyOb = filmyComboBox.getItems();
        filmyOb.forEach(film -> {
            int idFilmu = film.getIdFilmu();
            if(idFilmu == Integer.parseInt(labelFilm.getText())){
                filmyComboBox.getSelectionModel().select(film);
            }
        });

    }

    public void edytujSeans(ActionEvent actionEvent) {
        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();

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
            Date tmpDate = stworzNowaDate(godzina, minuta, data);
            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Filmy film = filmyComboBox.getSelectionModel().getSelectedItem();
            Sale sala = salaComboBox.getSelectionModel().getSelectedItem();

            int idSeansu = Integer.parseInt(labelSeans.getText());

            String dataSformatowana = formater.format(tmpDate);
            try {
                DatabaseConnector.getInstance().edytujSeans(idSeansu,film.getIdFilmu(), sala.getIdSali(), dataSformatowana);
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
            stage.close();
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


    public void anuluj(ActionEvent actionEvent) {
        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
