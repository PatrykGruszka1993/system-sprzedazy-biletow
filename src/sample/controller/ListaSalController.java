package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import org.omg.CORBA.DATA_CONVERSION;
import sample.entity.Sale;
import sample.jdbc.DatabaseConnector;

import javax.xml.crypto.Data;
import java.net.URL;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class ListaSalController implements Initializable {

    @FXML
    private ListView saleListView;

    private List<Sale> saleList;
    private Sale aktualnaSala;
    private ObservableList<String> saleStringList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wczytajSale();
        obserwujZmianęAktualnejSali();
    }

    private void wczytajSale(){
        saleList = DatabaseConnector.getInstance().querySale();
        saleStringList = FXCollections.observableArrayList();
        for (Sale s: saleList
             ) {
            saleStringList.add(s.getNazwaSali());
        }

        saleListView.setItems(saleStringList);
        aktualnaSala = saleList.get(0);
    }

    private void obserwujZmianęAktualnejSali(){
        saleListView.setOnMouseClicked(event -> aktualnaSala = saleList.get(saleListView.getSelectionModel().getSelectedIndex()));
    }

    @FXML
    private void usunSale(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie usunięcia sali");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Czy na pewno usunąć salę?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            usunSaleZBazy();
            potwierdźUsunięcie();
            wczytajSale();
        }
    }

    private void usunSaleZBazy(){
        DatabaseConnector.getInstance().deleteSale(aktualnaSala.getIdSali());
    }

    private void potwierdźUsunięcie(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Potwierdzenie");
        alert.setHeaderText(null);
        alert.setContentText("Usuwanie zakończone.");

        alert.showAndWait();
    }

    @FXML
    private void dodajSale(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Dodaj salę");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setContentText("Wprowadź nazwę sali:");

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            wprowadźSalęDoBazy(result.get());
            potwierdźWprowadzenie();
            wczytajSale();
        }
    }

    private void wprowadźSalęDoBazy(String nazwaSali){
        DatabaseConnector.getInstance().insertSala(nazwaSali);
    }

    private void potwierdźWprowadzenie(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Potwierdzenie");
        alert.setHeaderText(null);
        alert.setContentText("Dodawanie zakończone.");

        alert.showAndWait();
    }

    @FXML
    private void edytujSale(){
        TextInputDialog dialog = new TextInputDialog(aktualnaSala.getNazwaSali());
        dialog.setTitle("Edytuj salę");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setContentText("Wprowadź nową nazwę sali:");

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            zaktualizujSaleWBazie(result.get());
            potwierdźAktualizację();
            wczytajSale();
        }
    }

    private void zaktualizujSaleWBazie(String nowaNazwa){
        DatabaseConnector.getInstance().updateSala(aktualnaSala.getIdSali(), nowaNazwa);
    }

    private void potwierdźAktualizację(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Potwierdzenie");
        alert.setHeaderText(null);
        alert.setContentText("Edycja zakończona.");

        alert.showAndWait();
    }

}
