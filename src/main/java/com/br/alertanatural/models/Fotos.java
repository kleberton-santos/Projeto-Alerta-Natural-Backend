package com.br.alertanatural.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Objects;

@Entity // Indica que a classe é uma entidade JPA mapeada para uma tabela no banco de dados
@Table(name = "fotos") // Define o nome da tabela no banco de dados
public class Fotos {

    @Id // Define o campo 'idFoto' como a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define o comportamento de geração automática do valor para a chave primária
    private Long idFoto;

    private String caminhoFoto; // Caminho do arquivo da foto no sistema

    @CreationTimestamp // Gera automaticamente a data e hora de criação do registro
    private Date dataCadastro;

    @ManyToOne // Define o relacionamento muitos-para-um entre 'Fotos' e 'Usuarios'
    @JoinColumn(name = "idUsuario", nullable = false) // Define a coluna de relacionamento e define que ela não pode ser nula
    @JsonIgnore // Ignora o campo 'usuario' durante a serialização JSON
    private Usuarios usuario;

    @ManyToOne // Define o relacionamento muitos-para-um entre 'Fotos' e 'Publicacoes'
    @JoinColumn(name = "idPublicacao", nullable = true) // Define a coluna de relacionamento e permite que seja nula
    @JsonBackReference // Evita loops infinitos durante a serialização JSON, ignorando a referência de volta para 'Fotos' em 'Publicacoes'
    private Publicacoes publicacao;

    // Construtor padrão (sem parâmetros) para a entidade JPA
    public Fotos() {
    }

    // Construtor com parâmetros para inicializar a entidade com valores específicos
    public Fotos(Long idFoto, String caminhoFoto, Date dataCadastro, Usuarios usuario, Publicacoes publicacao) {
        this.idFoto = idFoto;
        this.caminhoFoto = caminhoFoto;
        this.dataCadastro = dataCadastro;
        this.usuario = usuario;
        this.publicacao = publicacao;
    }

    // Métodos getter e setter para acessar e modificar os valores dos atributos
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

    // Método toString para exibir uma representação em String do objeto
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

    // Método equals para comparar dois objetos da classe 'Fotos' com base no id
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Fotos fotos = (Fotos) o;
        return Objects.equals(idFoto, fotos.idFoto);
    }

    // Método hashCode para gerar um código hash baseado no id
    @Override
    public int hashCode() {
        return Objects.hashCode(idFoto);
    }
}
