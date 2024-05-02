package model.beans;

import java.io.Serializable;

public class Carrello implements Serializable {
    private long idUtente;
    private long idProdotto;
    private long quantita;

    public Carrello() {}

    public long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(long idUtente) {
        this.idUtente = idUtente;
    }

    public long getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(long idProdotto) {
        this.idProdotto = idProdotto;
    }

    public long getQuantita() {
        return quantita;
    }

    public void setQuantita(long quantita) {
        this.quantita = quantita;
    }

    @Override
    public String toString() {
        return "Carrello{" +
                "idUtente=" + idUtente +
                ", idProdotto=" + idProdotto +
                ", quantita=" + quantita +
                '}';
    }
}