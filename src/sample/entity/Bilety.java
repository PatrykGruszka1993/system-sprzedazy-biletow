package sample.entity;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Bilety {

    private SimpleIntegerProperty idBiletu;
    private SimpleIntegerProperty idFilmu;
    private SimpleIntegerProperty idMiejsca;
    private SimpleIntegerProperty idSali;
    private SimpleIntegerProperty idSeansu;
    private SimpleIntegerProperty idTransakcji;
    private SimpleStringProperty typBiletu;


    public Bilety() {
        this.idBiletu = new SimpleIntegerProperty();
        this.idFilmu = new SimpleIntegerProperty();
        this.idMiejsca = new SimpleIntegerProperty();
        this.idSali = new SimpleIntegerProperty();
        this.idSeansu = new SimpleIntegerProperty();
        this.idTransakcji = new SimpleIntegerProperty();
        this.typBiletu = new SimpleStringProperty();
    }

    public int getIdBiletu() {
        return idBiletu.get();
    }

    public SimpleIntegerProperty idBiletuProperty() {
        return idBiletu;
    }

    public void setIdBiletu(int idBiletu) {
        this.idBiletu.set(idBiletu);
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

    public int getIdMiejsca() {
        return idMiejsca.get();
    }

    public SimpleIntegerProperty idMiejscaProperty() {
        return idMiejsca;
    }

    public void setIdMiejsca(int idMiejsca) {
        this.idMiejsca.set(idMiejsca);
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

    public int getIdTransakcji() {
        return idTransakcji.get();
    }

    public SimpleIntegerProperty idTransakcjiProperty() {
        return idTransakcji;
    }

    public void setIdTransakcji(int idTransakcji) {
        this.idTransakcji.set(idTransakcji);
    }

    public String getTypBiletu() {
        return typBiletu.get();
    }

    public SimpleStringProperty typBiletuProperty() {
        return typBiletu;
    }

    public void setTypBiletu(String typBiletu) {
        this.typBiletu.set(typBiletu);
    }
}