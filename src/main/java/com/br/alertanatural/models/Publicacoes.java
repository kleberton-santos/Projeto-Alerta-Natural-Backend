package com.br.alertanatural.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "publicacoes")
public class Publicacoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPublicacao;

    @Column(columnDefinition = "TEXT")
    private String texto;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;

    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuarios usuario;

    @OneToMany(mappedBy = "publicacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fotos> fotos;

    public Publicacoes() {
    }

    public Publicacoes(Long idPublicacao, String texto, Date dataCadastro, Usuarios usuario, List<Fotos> fotos) {
        this.idPublicacao = idPublicacao;
        this.texto = texto;
        this.dataCadastro = dataCadastro;
        this.usuario = usuario;
        this.fotos = fotos;
    }

    public Long getIdPublicacao() {
        return idPublicacao;
    }

    public void setIdPublicacao(Long idPublicacao) {
        this.idPublicacao = idPublicacao;
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

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public List<Fotos> getFotos() {
        return fotos;
    }

    public void setFotos(List<Fotos> fotos) {
        this.fotos = fotos;
    }

    @Override
    public String toString() {
        return "Publicacao{" +
                "idPublicacao=" + idPublicacao +
                ", texto='" + texto + '\'' +
                ", dataCadastro=" + dataCadastro +
                ", usuario=" + usuario +
                ", fotos=" + fotos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Publicacoes that = (Publicacoes) o;
        return Objects.equals(idPublicacao, that.idPublicacao);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idPublicacao);
    }
}
