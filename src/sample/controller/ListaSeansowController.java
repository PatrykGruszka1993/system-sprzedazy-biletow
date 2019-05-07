package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.Main;
import sample.entity.Filmy;
import sample.entity.Seanse;
import sample.jdbc.DatabaseConnector;
import sun.java2d.pipe.SpanShapeRenderer;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListaSeansowController implements Initializable {

    @FXML
    private ListView<Seanse> seanseList;

    @FXML
    private ComboBox<Filmy> filmyComboBox;

    @FXML
    private DatePicker calendarPicker;

    private Stage stage;

    private Date data;

    private SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
    private SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ustawDate();
        fillFilmyComboBox();
        fillSeanseList();
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

    private void fillFilmyComboBox() {
        List<Filmy> filmy = DatabaseConnector.getInstance().queryFilmy();

        ObservableList<Filmy> obList = FXCollections.observableArrayList(filmy);
        filmyComboBox.getItems().addAll(obList);
    }

    private void fillSeanseList(){

        filmyComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            seanseList.getItems().clear();
            Filmy film = filmyComboBox.getSelectionModel().getSelectedItem();

            List<Seanse> seanse = DatabaseConnector.getInstance().querySeanseDlaDanegoFilmuBezDaty(film.getIdFilmu());
            seanseList.getItems().addAll(seanse);

        });

    }

    public void filtrujListePoWcisnieciu(KeyEvent keyEvent) {

        switch(keyEvent.getCode()){
            case ENTER:
                filtrujListe(null);
                break;
        }
    }

    public void filtrujListe(ActionEvent actionEvent) {

        seanseList.getItems().clear();
        Filmy film = filmyComboBox.getSelectionModel().getSelectedItem();

        if(calendarPicker.getValue() != null){
            List<Seanse> seanse = DatabaseConnector.getInstance().querySeansDlaDanegoFilmu(film, data);
            seanseList.getItems().addAll(seanse);

        } else {
            List<Seanse> seanse = DatabaseConnector.getInstance().querySeanseDlaDanegoFilmuBezDaty(film.getIdFilmu());
            seanseList.getItems().addAll(seanse);
        }
    }

    public void dodajSeans(ActionEvent actionEvent) {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/dodaj_seans_view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.setTitle("Dodaj seans");
            stage.initOwner(Main.getPrimaryStage());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
            odswiezListe();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void edytujSeans(ActionEvent actionEvent) {

        Seanse seans = seanseList.getSelectionModel().getSelectedItem();
        if (sprawdzCzyNieZaznaczonoSeansu(seans)) {
            return;
        }

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/edytuj_seans_view.fxml"));

            fxmlLoader.getNamespace().put("idSeansuOld", seans.getIdSeansu());
            fxmlLoader.getNamespace().put("idSaliOld", seans.getIdSali());
            fxmlLoader.getNamespace().put("idFilmuOld", seans.getIdFilmu());

            Parent root = fxmlLoader.load();
            EdytujSeansController ctrl = fxmlLoader.getController();
            ctrl.ustawDate(przygotujDatęSeansu(dateFormat.format(seans.getDataSeansu())));
            ctrl.ustawGodzine(hourFormat.format(seans.getDataSeansu()), minuteFormat.format(seans.getDataSeansu()));
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.setTitle("Edytuj seans");
            stage.initOwner(Main.getPrimaryStage());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            stage.showAndWait();
            odswiezListe();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private LocalDate przygotujDatęSeansu(String data){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(data, formatter);
        return localDate;
    }

    public void usunSeans(ActionEvent actionEvent) {
        Seanse seans = seanseList.getSelectionModel().getSelectedItem();
        if (sprawdzCzyNieZaznaczonoSeansu(seans)) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie usunięcia seansu");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Czy na pewno chcesz usunąć seans \"" + seans.toString() +  "\" ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                DatabaseConnector.getInstance().usunSeans(seans.getIdSeansu());
                odswiezListe();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private boolean sprawdzCzyNieZaznaczonoSeansu(Seanse seans) {
        if (seans == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("Nie wybrano seansu");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    private void odswiezListe() {
        seanseList.getItems().clear();
        fillSeanseList();
    }

    public void anuluj(ActionEvent actionEvent) {
        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
