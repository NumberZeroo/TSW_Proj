package model.ordine;

import java.io.Serializable;
import java.sql.Date;

public class OrdineBean implements Serializable {
    private long id;
    private long idUtente;
    private long idInfoConsegna;
    private Date data;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public OrdineBean() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(long idUtente) {
        this.idUtente = idUtente;
    }

    public long getIdInfoConsegna() {

        return idInfoConsegna;
    }

    public void setIdInfoConsegna(long idInfoConsegna) {
        this.idInfoConsegna = idInfoConsegna;
    }

    @Override
    public String toString() {
        return "OrdineBean{" +
                "id=" + id +
                ", idUtente=" + idUtente +
                ", idInfoConsegna=" + idInfoConsegna +
                '}';
    }
}