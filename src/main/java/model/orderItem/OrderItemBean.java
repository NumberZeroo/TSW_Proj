package model.orderItem;

import java.io.Serializable;

public class OrderItemBean implements Serializable {
    private long id;
    private long idProdotto;
    private long idOrdine;
    private double prezzo;
    private int quantita;

    public OrderItemBean() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(long idProdotto) {
        this.idProdotto = idProdotto;
    }

    public long getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(long idOrdine) {
        this.idOrdine = idOrdine;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "idItem=" + idProdotto +
                ", idOrdine=" + idOrdine +
                ", prezzo=" + prezzo +
                ", quantita=" + quantita +
                '}';
    }
}