package model.infoConsegna;

import java.io.Serializable;

public class InfoConsegnaBean implements Serializable {
    private long id;
    private String citta;
    private int cap;
    private String via;
    private String altro;
    private String destinatario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getAltro() {
        return altro;
    }

    public void setAltro(String altro) {
        this.altro = altro;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    @Override
    public String toString() {
        return "InfoConsegnaBean{" +
                "id=" + id +
                ", citta='" + citta + '\'' +
                ", cap=" + cap +
                ", via='" + via + '\'' +
                ", altro='" + altro + '\'' +
                ", destinatario='" + destinatario + '\'' +
                '}';
    }
}
