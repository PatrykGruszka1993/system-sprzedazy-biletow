package sample.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Seanse {

    private SimpleIntegerProperty idFilmu;
    private SimpleIntegerProperty idSali;
    private SimpleIntegerProperty idSeansu;
    private DateFormat dataSeansu;

    public Seanse() {
        this.idFilmu = new SimpleIntegerProperty();
        this.idSali = new SimpleIntegerProperty();
        this.idSeansu = new SimpleIntegerProperty();
        this.dataSeansu = new SimpleDateFormat();
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

    public DateFormat getDataSeansu() {
        return dataSeansu;
    }

    public void setDataSeansu(DateFormat dataSeansu) {
        this.dataSeansu = dataSeansu;
    }
}
