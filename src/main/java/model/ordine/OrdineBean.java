package model.ordine;

import java.io.Serializable;

public class OrdineBean implements Serializable {
    private long id;
    private long idUtente;
    private String pathFattura;

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

    public String getPathFattura() {
        return pathFattura;
    }

    public void setPathFattura(String pathFattura) {
        this.pathFattura = pathFattura;
    }

    @Override
    public String toString() {
        return "Ordine{" +
                "id=" + id +
                ", idUtente=" + idUtente +
                ", pathFattura='" + pathFattura + '\'' +
                '}';
    }
}