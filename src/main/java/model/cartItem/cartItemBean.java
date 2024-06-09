package model.cartItem;

import java.io.Serializable;

public class cartItemBean implements Serializable {
    private long idItem;
    private long idCarrello;
    private long prezzo;

    public cartItemBean() {}

    public long getIdItem() {
        return idItem;
    }

    public void setIdItem(long idItem) {
        this.idItem = idItem;
    }

    public long getIdCarrello() {
        return idCarrello;
    }

    public void setIdCarrello(long idCarrello) {
        this.idCarrello = idCarrello;
    }

    public long getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(long prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "idItem=" + idItem +
                ", idCarrello=" + idCarrello +
                ", prezzo=" + prezzo +
                '}';
    }
}