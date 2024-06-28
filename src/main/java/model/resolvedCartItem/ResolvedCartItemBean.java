package model.resolvedCartItem;

/**
 * Questa classe funge da bean che fonde "CartItem" e "Product" per avere sia la quantità nel carrello per ogni
 *  prodotto che le informazioni del prodotto stesso. Utile per la visualizzazione dei prodotti nel carrello e nel
 *  checkout.
 */
public class ResolvedCartItemBean {
    // Attributi di Product
    private long idProdotto;
    private String nome;
    private String descrizione;
    private int disponibilita;
    private String taglia;
    private String categoria;
    private int minEta;
    private int maxEta;
    private String iva;
    private double prezzo;
    private boolean sterilizzati;
    private String imgPath;
    private long tipoAnimale;
    private boolean isVisibile;

    // Attributi di CartItem
    private long idCartItem;
    private long idCarrello;
    private int quantita;

    public void setTipoAnimale(long tipoAnimale) {
        this.tipoAnimale = tipoAnimale;
    }

    public boolean isVisibile() {
        return isVisibile;
    }

    public void setVisibile(boolean visibile) {
        isVisibile = visibile;
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

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public boolean isSterilizzati() {
        return sterilizzati;
    }

    public long getTipoAnimale() {
        return tipoAnimale;
    }

    public long getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(long idProdotto) {
        this.idProdotto = idProdotto;
    }

    public long getIdCartItem() {
        return idCartItem;
    }

    public void setIdCartItem(long idCartItem) {
        this.idCartItem = idCartItem;
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
        return "ResolvedCartItemBean{" +
                "idProdotto=" + idProdotto +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", disponibilita=" + disponibilita +
                ", taglia='" + taglia + '\'' +
                ", categoria='" + categoria + '\'' +
                ", minEta=" + minEta +
                ", maxEta=" + maxEta +
                ", iva='" + iva + '\'' +
                ", prezzo=" + prezzo +
                ", sterilizzati=" + sterilizzati +
                ", imgPath='" + imgPath + '\'' +
                ", tipoAnimale=" + tipoAnimale +
                ", isVisibile=" + isVisibile +
                ", idCartItem=" + idCartItem +
                ", idCarrello=" + idCarrello +
                ", quantita=" + quantita +
                '}';
    }
}
