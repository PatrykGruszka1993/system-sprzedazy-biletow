package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioMenuItem;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class TransakcjeController implements Initializable {

    @FXML
    private RadioMenuItem sprzedażViewMenuItem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sprzedażViewMenuItem.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/sample.fxml"));
                Parent root = loader.load();

                Main.getPrimaryStage().setScene(new Scene(root, 1024,768));
                Main.getPrimaryStage().show();
            }catch (Exception e){
                e.printStackTrace();
            }

        });
    }
}
