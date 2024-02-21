package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ListaJogos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idPerfil;
    private String nomeJogo;
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean zerado;

    public ListaJogos(int idPerfil, String nomeJogo, Boolean zerado) {
        this.idPerfil = idPerfil;
        this.nomeJogo = nomeJogo;
        this.zerado = zerado;
    }

    public ListaJogos() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNomeJogo() {
        return nomeJogo;
    }

    public void setNomeJogo(String nomeJogo) {
        this.nomeJogo = nomeJogo;
    }

    public Boolean getZerado() {
        return zerado;
    }

    public void setZerado(Boolean zerado) {
        this.zerado = zerado;
    }

}
