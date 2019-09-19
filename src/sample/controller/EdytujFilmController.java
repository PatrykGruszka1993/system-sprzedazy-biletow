package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.jdbc.DatabaseConnector;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EdytujFilmController implements Initializable {

    @FXML
    private TextField tytul;

    @FXML
    private TextField czasTrwania;

    @FXML
    private TextArea opis;

    @FXML
    private Button add;

    @FXML
    private Button cancel;

    @FXML
    private Label id;

    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void edytujFilmZBazy(ActionEvent actionEvent) {

        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        int idFilmu = Integer.parseInt(id.getText().trim());
        String tytulStr = tytul.getText().trim();
        String czasTrwaniaStr = czasTrwania.getText().trim();
        String opisStr = opis.getText().trim();

        System.out.println(idFilmu + "   " + tytulStr);

        if(tytulStr.equals("") && czasTrwaniaStr.equals("") && opisStr.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("Błędne dane");
            alert.showAndWait();
            return;
        }

        try {
            DatabaseConnector.getInstance().edytujFilm(idFilmu, tytulStr, czasTrwaniaStr, opisStr);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        stage.close();
    }

    public void anuluj(ActionEvent actionEvent) {
        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
