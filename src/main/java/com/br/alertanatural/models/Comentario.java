package com.br.alertanatural.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Objects;

@Entity // Indica que a classe é uma entidade JPA mapeada para uma tabela no banco de dados
@Table(name = "comentarios") // Define o nome da tabela no banco de dados
public class Comentario {

    @Id // Define o campo 'id' como a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define o comportamento de geração automática do valor para a chave primária
    private Long id;

    @Column(columnDefinition = "TEXT") // Define que o campo 'texto' será armazenado como tipo TEXT no banco de dados
    private String texto;

    @CreationTimestamp // Gera automaticamente a data e hora de criação do registro
    private Date dataCadastro;

    @ManyToOne // Define o relacionamento muitos-para-um entre 'Comentario' e 'Publicacoes'
    @JoinColumn(name = "id_publicacao", nullable = false) // Define a coluna de relacionamento e define que ela não pode ser nula
    @JsonIgnore // Ignora o campo 'publicacao' durante a serialização JSON para evitar loops infinitos
    private Publicacoes publicacao;

    @ManyToOne // Define o relacionamento muitos-para-um entre 'Comentario' e 'Usuarios'
    @JoinColumn(name = "idusuario", nullable = false) // Define a coluna de relacionamento e define que ela não pode ser nula
    private Usuarios usuarios;

    // Construtor padrão (sem parâmetros) para a entidade JPA
    public Comentario() {
    }

    // Construtor com parâmetros para inicializar a entidade com valores específicos
    public Comentario(Long id, String texto, Date dataCadastro, Publicacoes publicacao, Usuarios usuarios) {
        this.id = id;
        this.texto = texto;
        this.dataCadastro = dataCadastro;
        this.publicacao = publicacao;
        this.usuarios = usuarios;
    }

    // Métodos getter e setter para acessar e modificar os valores dos atributos
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

    // Método toString para exibir uma representação em String do objeto
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

    // Método equals para comparar dois objetos da classe 'Comentario' com base no id
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Comentario that = (Comentario) o;
        return Objects.equals(id, that.id);
    }

    // Método hashCode para gerar um código hash baseado no id
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
