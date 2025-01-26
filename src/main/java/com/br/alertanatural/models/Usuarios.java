package com.br.alertanatural.models;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idusuario;

    @NotNull
    private String nome;

    private String sobreNome;

    @NotNull
    private String cpf;

    @NotNull
    private String telefone;

    @NotNull
    private String email;

    @NotNull
    private String senha;

    private String foto;

    @CreationTimestamp
    private Date dataCadastro;

    @UpdateTimestamp
    private Date dataAtualizacao;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fotos> fotos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Publicacoes> publicacoes;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Amigos> amigos;

    @OneToMany(mappedBy = "amigo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Amigos> seguidores;

    public Usuarios() {
    }

    public Usuarios(Long idusuario, String nome, String sobreNome, String cpf, String telefone, String email, String senha, String foto, Date dataCadastro, Date dataAtualizacao, List<Fotos> fotos, List<Publicacoes> publicacoes, List<Amigos> amigos, List<Amigos> seguidores) {
        this.idusuario = idusuario;
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.foto = foto;
        this.dataCadastro = dataCadastro;
        this.dataAtualizacao = dataAtualizacao;
        this.fotos = fotos;
        this.publicacoes = publicacoes;
        this.amigos = amigos;
        this.seguidores = seguidores;
    }

    public Long getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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

    public List<Fotos> getFotos() {
        return fotos;
    }

    public void setFotos(List<Fotos> fotos) {
        this.fotos = fotos;
    }

    public List<Publicacoes> getPublicacoes() {
        return publicacoes;
    }

    public void setPublicacoes(List<Publicacoes> publicacoes) {
        this.publicacoes = publicacoes;
    }

    public List<Amigos> getAmigos() {
        return amigos;
    }

    public void setAmigos(List<Amigos> amigos) {
        this.amigos = amigos;
    }

    public List<Amigos> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(List<Amigos> seguidores) {
        this.seguidores = seguidores;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idusuario=" + idusuario +
                ", nome='" + nome + '\'' +
                ", sobreNome='" + sobreNome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", foto='" + foto + '\'' +
                ", dataCadastro=" + dataCadastro +
                ", dataAtualizacao=" + dataAtualizacao +
                ", fotos=" + fotos +
                ", publicacoes=" + publicacoes +
                ", amigos=" + amigos +
                ", seguidores=" + seguidores +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuarios usuario = (Usuarios) o;
        return Objects.equals(idusuario, usuario.idusuario);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idusuario);
    }
}
