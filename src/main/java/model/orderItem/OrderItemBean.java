package model.orderItem;

import java.io.Serializable;

public class OrderItemBean implements Serializable {
    private long idItem;
    private long idOrdine;
    private long prezzo;
    private long quantita;

    public OrderItemBean() {}

    public long getIdItem() {
        return idItem;
    }

    public void setIdItem(long idItem) {
        this.idItem = idItem;
    }

    public long getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(long idOrdine) {
        this.idOrdine = idOrdine;
    }

    public long getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(long prezzo) {
        this.prezzo = prezzo;
    }

    public long getQuantita() {
        return quantita;
    }

    public void setQuantita(long quantita) {
        this.quantita = quantita;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "idItem=" + idItem +
                ", idOrdine=" + idOrdine +
                ", prezzo=" + prezzo +
                ", quantita=" + quantita +
                '}';
    }
}