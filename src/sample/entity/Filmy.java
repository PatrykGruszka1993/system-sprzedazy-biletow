package sample.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Filmy {

    private SimpleIntegerProperty idFilmu;
    private SimpleStringProperty czasTrwania;
    private SimpleStringProperty opis;
    private SimpleStringProperty tytul;

    public Filmy() {
        this.idFilmu = new SimpleIntegerProperty();
        this.czasTrwania = new SimpleStringProperty();
        this.opis = new SimpleStringProperty();
        this.tytul = new SimpleStringProperty();
    }

    public int getIdFilmu() {
        return idFilmu.get();
    }

    public SimpleIntegerProperty idFilmuProperty() {
        return idFilmu;
    }

    public void setIdFilmu(int idFilmu) {
        this.idFilmu.set(idFilmu);
    }

    public String getCzasTrwania() {
        return czasTrwania.get();
    }

    public SimpleStringProperty czasTrwaniaProperty() {
        return czasTrwania;
    }

    public void setCzasTrwania(String czasTrwania) {
        this.czasTrwania.set(czasTrwania);
    }

    public String getOpis() {
        return opis.get();
    }

    public SimpleStringProperty opisProperty() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis.set(opis);
    }

    public String getTytul() {
        return tytul.get();
    }

    public SimpleStringProperty tytulProperty() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul.set(tytul);
    }
}