package sample.entity;

import javafx.beans.property.SimpleIntegerProperty;

public class Transakcje {

    private SimpleIntegerProperty idTransakcji;
    private SimpleIntegerProperty wartoscTransakcji;

    public Transakcje() {
        this.idTransakcji = new SimpleIntegerProperty();
        this.wartoscTransakcji = new SimpleIntegerProperty();
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

    public int getWartoscTransakcji() {
        return wartoscTransakcji.get();
    }

    public SimpleIntegerProperty wartoscTransakcjiProperty() {
        return wartoscTransakcji;
    }

    public void setWartoscTransakcji(int wartoscTransakcji) {
        this.wartoscTransakcji.set(wartoscTransakcji);
    }
}
