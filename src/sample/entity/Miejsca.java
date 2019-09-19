package sample.entity;

import javafx.beans.property.SimpleIntegerProperty;

public class Miejsca {

    private SimpleIntegerProperty idMiejsca;
    private SimpleIntegerProperty idFilmu;
    private SimpleIntegerProperty idSali;
    private SimpleIntegerProperty idSeansu;

    private SimpleIntegerProperty nrMiejsca;
    private SimpleIntegerProperty rzad;

    public Miejsca() {
        this.idMiejsca = new SimpleIntegerProperty();
        this.idFilmu = new SimpleIntegerProperty();
        this.idSali = new SimpleIntegerProperty();
        this.idSeansu = new SimpleIntegerProperty();
        this.nrMiejsca = new SimpleIntegerProperty();
        this.rzad = new SimpleIntegerProperty();
    }



    public int getIdMiejsca() {
        return idMiejsca.get();
    }

    public SimpleIntegerProperty idMiejscaProperty() {
        return idMiejsca;
    }

    public void setIdMiejsca(int idMiejsca) {
        this.idMiejsca.set(idMiejsca);
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

    public int getIdSali() {
        return idSali.get();
    }

    public SimpleIntegerProperty idSaliProperty() {
        return idSali;
    }

    public void setIdSali(int idSali) {
        this.idSali.set(idSali);
    }

    public int getIdSeansu() {
        return idSeansu.get();
    }

    public SimpleIntegerProperty idSeansuProperty() {
        return idSeansu;
    }

    public void setIdSeansu(int idSeansu) {
        this.idSeansu.set(idSeansu);
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

    public int getRzad() {
        return rzad.get();
    }

    public SimpleIntegerProperty rzadProperty() {
        return rzad;
    }

    public void setRzad(int rzad) {
        this.rzad.set(rzad);
    }

    @Override
    public String toString() {
        return "Miejsca{" +
                "idMiejsca=" + idMiejsca +
                ", idFilmu=" + idFilmu +
                ", idSali=" + idSali +
                ", idSeansu=" + idSeansu +
                ", nrMiejsca=" + nrMiejsca +
                ", rzad=" + rzad +
                '}';
    }
}
