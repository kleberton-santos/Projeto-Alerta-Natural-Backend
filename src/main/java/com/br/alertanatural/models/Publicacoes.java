package com.br.alertanatural.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity // Marca a classe como uma entidade JPA mapeada para uma tabela no banco de dados
@Table(name = "publicacoes") // Define o nome da tabela no banco de dados
public class Publicacoes {

    @Id // Define a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define a estratégia de geração automática para o ID
    private Long idPublicacao;

    @Column(columnDefinition = "TEXT") // Define que a coluna 'texto' será do tipo TEXT no banco de dados
    private String texto;

    private String nomeUsuario; // Nome do usuário que fez a publicação

    private String fotoUsuario; // Foto do usuário que fez a publicação

    @CreationTimestamp // Gera automaticamente a data de criação do registro
    private Date dataCadastro;

    @UpdateTimestamp // Gera automaticamente a data da última atualização do registro
    private Date dataAtualizacao;

    @ManyToOne // Define o relacionamento muitos-para-um entre 'Publicacoes' e 'Usuarios'
    @JoinColumn(name = "idusuario", nullable = false) // Define a coluna de relacionamento e define que não pode ser nula
    @JsonIgnore // Ignora o campo 'usuario' durante a serialização JSON
    private Usuarios usuario;

    @OneToMany(mappedBy = "publicacao", cascade = CascadeType.ALL, orphanRemoval = true) // Relacionamento um-para-muitos com 'Fotos'
    @JsonManagedReference // Controla a serialização JSON para evitar problemas de recursão
    private List<Fotos> fotos;

    @OneToMany(mappedBy = "publicacao", cascade = CascadeType.ALL, orphanRemoval = true) // Relacionamento um-para-muitos com 'Comentario'
    private List<Comentario> comentarios;

    @ElementCollection // Define um tipo de coleção simples (lista de strings) para vídeos
    private List<String> videos;

    @OneToMany(mappedBy = "publicacao", cascade = CascadeType.ALL, orphanRemoval = true) // Relacionamento um-para-muitos com 'Curtida'
    private List<Curtida> curtidas;

    // Construtor padrão (sem parâmetros) para a entidade JPA
    public Publicacoes() {
    }

    // Construtor com parâmetros para inicializar a entidade com valores específicos
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

    // Métodos getter e setter para acessar e modificar os valores dos atributos
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

    public List<Curtida> getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(List<Curtida> curtidas) {
        this.curtidas = curtidas;
    }

    // Método toString para exibir uma representação em String do objeto
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

    // Método equals para comparar dois objetos da classe 'Publicacoes' com base no id
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Publicacoes that = (Publicacoes) o;
        return Objects.equals(idPublicacao, that.idPublicacao);
    }

    // Método hashCode para gerar um código hash baseado no id
    @Override
    public int hashCode() {
        return Objects.hashCode(idPublicacao);
    }
}
