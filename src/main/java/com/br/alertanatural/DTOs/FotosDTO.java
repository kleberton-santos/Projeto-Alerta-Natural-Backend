package com.br.alertanatural.DTOs;

import java.util.Date;
import java.util.List;

public class FotosDTO {

    private Long idFoto;
    private String caminhoFoto;
    private Date dataCadastro;
    private Long idUsuario;
    private Long idPublicacao;

    public FotosDTO() {
    }

    // Construtores, getters e setters

    public FotosDTO(Long idFoto, String caminhoFoto, Date dataCadastro, Long idUsuario, Long idPublicacao) {
        this.idFoto = idFoto;
        this.caminhoFoto = caminhoFoto;
        this.dataCadastro = dataCadastro;
        this.idUsuario = idUsuario;
        this.idPublicacao = idPublicacao;
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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdPublicacao() {
        return idPublicacao;
    }

    public void setIdPublicacao(Long idPublicacao) {
        this.idPublicacao = idPublicacao;
    }
}