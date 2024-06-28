package model.cartItem;

import java.io.Serializable;

public class CartItemBean implements Serializable {
    private long id;
    private long idProdotto;
    private long idCarrello;
    private int quantita;

    public CartItemBean() {}

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

    public long getIdCarrello() {
        return idCarrello;
    }

    public void setIdCarrello(long idCarrello) {
        this.idCarrello = idCarrello;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    @Override
    public String toString() {
        return "CartItemBean{" +
                "id=" + id +
                ", idProdotto=" + idProdotto +
                ", idCarrello=" + idCarrello +
                ", quantita=" + quantita +
                '}';
    }
}