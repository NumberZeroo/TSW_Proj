package model.pet;

import java.io.Serializable;
import java.sql.*;

public class PetBean implements Serializable {
    private long id;
    private String nome;
    private long idUtente;
    private String imgPath;
    private String tipo;
    private String taglia;
    private boolean sterilizzato;
    private Date dataNascita;

    public PetBean() {
    }

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

    public boolean getSterilizzato() {
        return sterilizzato;
    }

    public void setSterilizzato(boolean sterilizzato) {
        this.sterilizzato = sterilizzato;
    }

    public java.sql.Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    @Override
    public String toString() {
        return "PetBean{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idUtente=" + idUtente +
                ", imgPath='" + imgPath + '\'' +
                ", tipo='" + tipo + '\'' +
                ", taglia='" + taglia + '\'' +
                ", sterilizzato='" + sterilizzato + '\'' +
                ", dataNascita=" + dataNascita +
                '}';
    }
}