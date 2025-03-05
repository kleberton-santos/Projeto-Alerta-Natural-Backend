package com.br.alertanatural.DTOs;

import java.util.Date;
import java.util.List;

public class PublicacaoDTO {
    private Long idPublicacao;
    private Long idUsuario;
    private String texto;
    private String nomeUsuario;
    private String fotoUsuario;
    private List<String> fotos;
    private List<String> videos;
    private Date dataCadastro;
    private int curtidas;

    public PublicacaoDTO(Long idPublicacao, Long idUsuario, String texto, String nomeUsuario, String fotoUsuario, List<String> fotos, List<String> videos, Date dataCadastro, int curtidas) {
        this.idPublicacao = idPublicacao;
        this.idUsuario = idUsuario;
        this.texto = texto;
        this.nomeUsuario = nomeUsuario;
        this.fotoUsuario = fotoUsuario;
        this.fotos = fotos;
        this.videos = videos;
        this.dataCadastro = dataCadastro;
        this.curtidas = curtidas;
    }
    public PublicacaoDTO() {}

    public Long getIdPublicacao() {
        return idPublicacao;
    }

    public void setIdPublicacao(Long idPublicacao) {
        this.idPublicacao = idPublicacao;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
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

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public List<String> getVideos() {
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }
    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    public int getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(int curtidas) {
        this.curtidas = curtidas;
    }
}