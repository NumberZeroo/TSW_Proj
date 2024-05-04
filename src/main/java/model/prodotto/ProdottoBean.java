package model.prodotto;

import java.io.Serializable;

public class ProdottoBean implements Serializable {
    private long id;
    private String nome;
    private int disponibilita;
    private String taglia;
    private String categoria;
    private int minEta;
    private int maxEta;
    private int iva;
    private long prezzo;
    private boolean sterilizzati;
    private String imgPath;
    private long tipoAnimale;

    public ProdottoBean() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(int disponibilita) {
        this.disponibilita = disponibilita;
    }

    public String getTaglia() {
        return taglia;
    }

    public void setTaglia(String taglia) {
        this.taglia = taglia;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getMinEta() {
        return minEta;
    }

    public void setMinEta(int minEta) {
        this.minEta = minEta;
    }

    public int getMaxEta() {
        return maxEta;
    }

    public void setMaxEta(int maxEta) {
        this.maxEta = maxEta;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public long getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(long prezzo) {
        this.prezzo = prezzo;
    }

    public boolean getSterilizzati() {
        return sterilizzati;
    }

    public void setSterilizzati(boolean sterilizzati) {
        this.sterilizzati = sterilizzati;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public long getTipoAnimale() {
        return tipoAnimale;
    }

    public void setTipoAnimale(long tipoAnimale) {
        this.tipoAnimale = tipoAnimale;
    }

    @Override
    public String toString() {
        return "Prodotto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", disponibilita=" + disponibilita +
                ", taglia='" + taglia + '\'' +
                ", tipo='" + categoria + '\'' +
                ", minEta=" + minEta +
                ", maxEta=" + maxEta +
                ", iva=" + iva +
                ", prezzo=" + prezzo +
                ", sterilizzati=" + sterilizzati +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }
}