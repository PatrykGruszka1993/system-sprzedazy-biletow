package sample.entity;

import javafx.beans.property.SimpleIntegerProperty;

public class Miejsca {

    private SimpleIntegerProperty idMiejsca;
    private SimpleIntegerProperty nrMiejsca;
    private SimpleIntegerProperty nrSali;
    private SimpleIntegerProperty rzad;

    public int getIdMiejsca() {
        return idMiejsca.get();
    }

    public SimpleIntegerProperty idMiejscaProperty() {
        return idMiejsca;
    }

    public void setIdMiejsca(int idMiejsca) {
        this.idMiejsca.set(idMiejsca);
    }

    public int getNrMiejsca() {
        return nrMiejsca.get();
    }

    public SimpleIntegerProperty nrMiejscaProperty() {
        return nrMiejsca;
    }

    public void setNrMiejsca(int nrMiejsca) {
        this.nrMiejsca.set(nrMiejsca);
    }

    public int getNrSali() {
        return nrSali.get();
    }

    public SimpleIntegerProperty nrSaliProperty() {
        return nrSali;
    }

    public void setNrSali(int nrSali) {
        this.nrSali.set(nrSali);
    }

    public int getRzad() {
        return rzad.get();
    }

    public SimpleIntegerProperty rzadProperty() {
        return rzad;
    }

    public void setRzad(int rzad) {
        this.rzad.set(rzad);
    }
}
