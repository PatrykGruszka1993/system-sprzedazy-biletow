package sample.entity;

import javafx.beans.property.SimpleIntegerProperty;

import java.time.LocalDateTime;

public class Seanse {

    private SimpleIntegerProperty idFilmu;
    private SimpleIntegerProperty idMiejsca;
    private SimpleIntegerProperty idSeansu;
    private LocalDateTime dataSeansu;
    private LocalDateTime godzina;

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

    public int getIdSeansu() {
        return idSeansu.get();
    }

    public SimpleIntegerProperty idSeansuProperty() {
        return idSeansu;
    }

    public void setIdSeansu(int idSeansu) {
        this.idSeansu.set(idSeansu);
    }

    public LocalDateTime getDataSeansu() {
        return dataSeansu;
    }

    public void setDataSeansu(LocalDateTime dataSeansu) {
        this.dataSeansu = dataSeansu;
    }

    public LocalDateTime getGodzina() {
        return godzina;
    }

    public void setGodzina(LocalDateTime godzina) {
        this.godzina = godzina;
    }
}
