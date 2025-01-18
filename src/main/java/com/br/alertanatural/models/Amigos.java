package com.br.alertanatural.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "amigos")
public class Amigos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAmigo;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "idAmigoUsuario", nullable = false)  // Renomeei para evitar conflito
    private Usuarios amigo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;

    public Amigos() {
    }

    public Amigos(Long idAmigo, Usuarios usuario, Usuarios amigo, Date dataCadastro) {
        this.idAmigo = idAmigo;
        this.usuario = usuario;
        this.amigo = amigo;
        this.dataCadastro = dataCadastro;
    }

    public Long getIdAmigo() {
        return idAmigo;
    }

    public void setIdAmigo(Long idAmigo) {
        this.idAmigo = idAmigo;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Usuarios getAmigo() {
        return amigo;
    }

    public void setAmigo(Usuarios amigo) {
        this.amigo = amigo;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public String toString() {
        return "Amigos{" +
                "idAmigo=" + idAmigo +
                ", usuario=" + usuario +
                ", amigo=" + amigo +
                ", dataCadastro=" + dataCadastro +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Amigos amigos = (Amigos) o;
        return Objects.equals(idAmigo, amigos.idAmigo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idAmigo);
    }
}
