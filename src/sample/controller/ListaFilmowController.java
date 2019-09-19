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
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import sample.entity.Filmy;
import sample.jdbc.DatabaseConnector;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListaFilmowController implements Initializable {

    @FXML
    private ListView<Filmy> movie_list;

    @FXML
    private Button add;

    @FXML
    private Button edit;

    @FXML
    private Button delete;

    @FXML
    private Button cancel;

    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillMovieList();

    }

    private void fillMovieList(){
        List<Filmy> filmy = DatabaseConnector.getInstance().queryFilmy();

        ObservableList<Filmy> obList = FXCollections.observableArrayList(filmy);
        movie_list.getItems().addAll(obList);
    }


    public void dodajFilm(ActionEvent actionEvent) {
        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/dodaj_film_view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.setTitle("Dodaj film");
            stage.initOwner(Main.getPrimaryStage());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
            odswiezListe();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void edytujFilm(ActionEvent actionEvent) {

        Filmy film = movie_list.getSelectionModel().getSelectedItem();
        if (sprawdzCzyNieZaznaczonoFilmu(film)) {
            return;
        }

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/edytuj_film_view.fxml"));

            fxmlLoader.getNamespace().put("idFilmu", film.getIdFilmu());
            fxmlLoader.getNamespace().put("tytulFilmu", film.getTytul());
            fxmlLoader.getNamespace().put("czasTrwaniaFilmu", film.getCzasTrwania());
            fxmlLoader.getNamespace().put("opisFilmu", film.getOpis());

            Parent root = fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.setTitle("Edytuj film");
            stage.initOwner(Main.getPrimaryStage());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            stage.showAndWait();
            odswiezListe();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean sprawdzCzyNieZaznaczonoFilmu(Filmy film) {
        if (film == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("Nie wybrano filmu");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    public void usunFilm(ActionEvent actionEvent) {
        //stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Filmy film = movie_list.getSelectionModel().getSelectedItem();
        if (sprawdzCzyNieZaznaczonoFilmu(film)) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie usunięcia filmu");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Czy na pewno chcesz usunąć film \"" + film.getTytul() +  "\" ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                DatabaseConnector.getInstance().usunFilm(film.getIdFilmu());
                odswiezListe();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void odswiezListe() {
        movie_list.getItems().clear();
        fillMovieList();
    }

    public void anuluj(ActionEvent actionEvent) {
        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public ListView<Filmy> getMovie_list() {
        return movie_list;
    }
}
