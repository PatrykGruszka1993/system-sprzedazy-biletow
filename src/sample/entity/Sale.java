package sample.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Sale {

    private SimpleIntegerProperty idSali;
    private SimpleStringProperty nazwaSali;

    public Sale() {
        this.idSali = new SimpleIntegerProperty();
        this.nazwaSali = new SimpleStringProperty();

    }

    public int getIdSali() {
        return idSali.get();
    }

    public SimpleIntegerProperty idSaliProperty() {
        return idSali;
    }

    public void setIdSali(int idSali) {
        this.idSali.set(idSali);
    }

    public String getNazwaSali() {
        return nazwaSali.get();
    }

    public SimpleStringProperty nazwaSaliProperty() {
        return nazwaSali;
    }

    public void setNazwaSali(String nazwaSali) {
        this.nazwaSali.set(nazwaSali);
    }

    @Override
    public String toString() {
        return getNazwaSali();
    }
}
