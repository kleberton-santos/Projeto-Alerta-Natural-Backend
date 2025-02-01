package com.br.alertanatural.DTOs;

import com.br.alertanatural.models.Amigos;

import java.util.Date;

public class AmigosDTO {
    private Long idAmigo;
    private String usuarioNome;
    private String amigoNome;
    private Date dataCadastro;

    public AmigosDTO(Amigos amigos) {
        this.idAmigo = amigos.getIdAmigo();
        this.usuarioNome = amigos.getUsuario().getNome();
        this.amigoNome = amigos.getAmigo().getNome();
        this.dataCadastro = amigos.getDataCadastro();
    }

    // Getters e Setters
    public Long getIdAmigo() {
        return idAmigo;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public String getAmigoNome() {
        return amigoNome;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }
}

