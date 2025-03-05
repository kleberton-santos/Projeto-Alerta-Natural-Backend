package com.br.alertanatural.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String texto;

    @CreationTimestamp
    private Date dataCadastro;

    @ManyToOne
    @JoinColumn(name = "id_publicacao", nullable = false)
    @JsonIgnore
    private Publicacoes publicacao;

    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuarios usuarios;

    public Comentario() {
    }

    public Comentario(Long id, String texto, Date dataCadastro, Publicacoes publicacao, Usuarios usuarios) {
        this.id = id;
        this.texto = texto;
        this.dataCadastro = dataCadastro;
        this.publicacao = publicacao;
        this.usuarios = usuarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Publicacoes getPublicacoes() {
        return publicacao;
    }

    public void setPublicacoes(Publicacoes publicacoes) {
        this.publicacao = publicacoes;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", texto='" + texto + '\'' +
                ", dataCadastro=" + dataCadastro +
                ", publicacoes=" + publicacao +
                ", usuarios=" + usuarios +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Comentario that = (Comentario) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

