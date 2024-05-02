package model.beans;

import java.io.Serializable;
import java.util.Date;

public class Pet implements Serializable {
    private String nome;
    private long idUtente;
    private String imgPath;
    private String tipo;
    private String taglia;
    private String sterilizzato;
    private Date dataNascita;

    public Pet() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(long idUtente) {
        this.idUtente = idUtente;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTaglia() {
        return taglia;
    }

    public void setTaglia(String taglia) {
        this.taglia = taglia;
    }

    public String getSterilizzato() {
        return sterilizzato;
    }

    public void setSterilizzato(String sterilizzato) {
        this.sterilizzato = sterilizzato;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "nome='" + nome + '\'' +
                ", idUtente=" + idUtente +
                ", imgPath='" + imgPath + '\'' +
                ", tipo='" + tipo + '\'' +
                ", taglia='" + taglia + '\'' +
                ", sterilizzato='" + sterilizzato + '\'' +
                ", dataNascita=" + dataNascita +
                '}';
    }
}