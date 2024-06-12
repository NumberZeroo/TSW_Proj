package model.carrello;

import java.io.Serializable;

public class CarrelloBean implements Serializable {
    private long id;
    private long idUtente;

    public CarrelloBean() {}

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

    @Override
    public String toString() {
        return "CarrelloBean{" +
                "id=" + id +
                ", idUtente=" + idUtente +
                '}';
    }
}