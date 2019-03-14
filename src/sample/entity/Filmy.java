package sample.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Filmy {

    private SimpleIntegerProperty idFilmu;
    private SimpleIntegerProperty czasTrwania;
    private SimpleStringProperty opis;
    private SimpleStringProperty tytul;

    public int getIdFilmu() {
        return idFilmu.get();
    }

    public SimpleIntegerProperty idFilmuProperty() {
        return idFilmu;
    }

    public void setIdFilmu(int idFilmu) {
        this.idFilmu.set(idFilmu);
    }

    public int getCzasTrwania() {
        return czasTrwania.get();
    }

    public SimpleIntegerProperty czasTrwaniaProperty() {
        return czasTrwania;
    }

    public void setCzasTrwania(int czasTrwania) {
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
