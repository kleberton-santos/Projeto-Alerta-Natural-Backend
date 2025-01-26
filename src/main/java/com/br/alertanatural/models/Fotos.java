package com.br.alertanatural.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "fotos")
public class Fotos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFoto;

    private String caminhoFoto;

    @CreationTimestamp
    private Date dataCadastro;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    @JsonIgnore
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "idPublicacao", nullable = true) // Relacionamento opcional com Publicacoes
    private Publicacoes publicacao;


    public Fotos() {
    }

    public Fotos(Long idFoto, String caminhoFoto, Date dataCadastro, Usuarios usuario, Publicacoes publicacao) {
        this.idFoto = idFoto;
        this.caminhoFoto = caminhoFoto;
        this.dataCadastro = dataCadastro;
        this.usuario = usuario;
        this.publicacao = publicacao;
    }

    public Long getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(Long idFoto) {
        this.idFoto = idFoto;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Publicacoes getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(Publicacoes publicacao) {
        this.publicacao = publicacao;
    }

    @Override
    public String toString() {
        return "Fotos{" +
                "idFoto=" + idFoto +
                ", caminhoFoto='" + caminhoFoto + '\'' +
                ", dataCadastro=" + dataCadastro +
                ", usuario=" + usuario +
                ", publicacao=" + publicacao +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Fotos fotos = (Fotos) o;
        return Objects.equals(idFoto, fotos.idFoto);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idFoto);
    }
}
