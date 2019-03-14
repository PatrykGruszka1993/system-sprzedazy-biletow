package sample.entity;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Bilety {

    private SimpleIntegerProperty idBiletu;
    private SimpleIntegerProperty idSeansu;
    private SimpleIntegerProperty idTransakcji;
    private SimpleIntegerProperty seanseIdFilmu;
    private SimpleIntegerProperty seanseIdMiejsca;
    private SimpleStringProperty typBiletu;

    public int getIdBiletu() {
        return idBiletu.get();
    }

    public SimpleIntegerProperty idBiletuProperty() {
        return idBiletu;
    }

    public void setIdBiletu(int idBiletu) {
        this.idBiletu.set(idBiletu);
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

    public int getSeanseIdFilmu() {
        return seanseIdFilmu.get();
    }

    public SimpleIntegerProperty seanseIdFilmuProperty() {
        return seanseIdFilmu;
    }

    public void setSeanseIdFilmu(int seanseIdFilmu) {
        this.seanseIdFilmu.set(seanseIdFilmu);
    }

    public int getSeanseIdMiejsca() {
        return seanseIdMiejsca.get();
    }

    public SimpleIntegerProperty seanseIdMiejscaProperty() {
        return seanseIdMiejsca;
    }

    public void setSeanseIdMiejsca(int seanseIdMiejsca) {
        this.seanseIdMiejsca.set(seanseIdMiejsca);
    }

    public SimpleStringProperty getTypBiletu() {
        return typBiletu;
    }

    public void setTypBiletu(SimpleStringProperty typBiletu) {
        this.typBiletu = typBiletu;
    }
}
