package com.br.alertanatural.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    private String nomeUsuario;

    private String fotoUsuario;



    @CreationTimestamp
    private Date dataCadastro;

    @UpdateTimestamp
    private Date dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    @JsonIgnore
    private Usuarios usuario;

    @OneToMany(mappedBy = "publicacao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Fotos> fotos;

    @OneToMany(mappedBy = "publicacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios;

    @ElementCollection
    private List<String> videos;

    public Publicacoes() {
    }

    public Publicacoes(Long idPublicacao, String texto, String nomeUsuario, String fotoUsuario, List<String> videos, Date dataCadastro, Date dataAtualizacao, Usuarios usuario, List<Fotos> fotos) {
        this.idPublicacao = idPublicacao;
        this.texto = texto;
        this.nomeUsuario = nomeUsuario;
        this.fotoUsuario = fotoUsuario;
        this.videos = videos;
        this.dataCadastro = dataCadastro;
        this.dataAtualizacao = dataAtualizacao;
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

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
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

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getFotoUsuario() {
        return fotoUsuario;
    }

    public void setFotoUsuario(String fotoUsuario) {
        this.fotoUsuario = fotoUsuario;
    }

    public List<String> getVideos() {
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "Publicacoes{" +
                "idPublicacao=" + idPublicacao +
                ", texto='" + texto + '\'' +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", fotoUsuario='" + fotoUsuario + '\'' +
                ", videos=" + videos +
                ", dataCadastro=" + dataCadastro +
                ", dataAtualizacao=" + dataAtualizacao +
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
