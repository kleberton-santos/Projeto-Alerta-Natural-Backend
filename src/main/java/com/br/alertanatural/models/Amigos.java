package com.br.alertanatural.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Objects;

@Entity // Indica que a classe é uma entidade JPA mapeada para uma tabela no banco de dados
@Table(name = "amigos") // Define o nome da tabela no banco de dados
@JsonIgnoreProperties({"usuario", "amigo"}) // Ignora os campos 'usuario' e 'amigo' durante a serialização JSON
public class Amigos {

    @Id // Define o campo 'idAmigo' como a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define o comportamento de geração automática do valor para a chave primária
    private Long idAmigo;

    @ManyToOne // Define o relacionamento muitos-para-um entre 'Amigos' e 'Usuarios'
    @JoinColumn(name = "idUsuario", nullable = false) // Define a coluna que mapeia o relacionamento e define que ela não pode ser nula
    private Usuarios usuario;

    @ManyToOne // Define o relacionamento muitos-para-um entre 'Amigos' e 'Usuarios'
    @JoinColumn(name = "idAmigoUsuario", nullable = false)  // Define a coluna que mapeia o relacionamento e define que ela não pode ser nula
    private Usuarios amigo;

    @CreationTimestamp // Gera automaticamente a data e hora de criação do registro
    private Date dataCadastro;

    // Construtor padrão (sem parâmetros) para a entidade JPA
    public Amigos() {
    }

    // Construtor com parâmetros para inicializar a entidade com valores específicos
    public Amigos(Long idAmigo, Usuarios usuario, Usuarios amigo, Date dataCadastro) {
        this.idAmigo = idAmigo;
        this.usuario = usuario;
        this.amigo = amigo;
        this.dataCadastro = dataCadastro;
    }

    // Métodos getter e setter para acessar e modificar os valores dos atributos
    public Long getIdAmigo() {
        return idAmigo;
    }

    public void setIdAmigo(Long idAmigo) {
        this.idAmigo = idAmigo;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Usuarios getAmigo() {
        return amigo;
    }

    public void setAmigo(Usuarios amigo) {
        this.amigo = amigo;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    // Método toString para exibir uma representação em String do objeto
    @Override
    public String toString() {
        return "Amigos{" +
                "idAmigo=" + idAmigo +
                ", usuario=" + usuario +
                ", amigo=" + amigo +
                ", dataCadastro=" + dataCadastro +
                '}';
    }

    // Método equals para comparar dois objetos da classe 'Amigos' com base no idAmigo
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Amigos amigos = (Amigos) o;
        return Objects.equals(idAmigo, amigos.idAmigo);
    }

    // Método hashCode para gerar um código hash baseado no idAmigo
    @Override
    public int hashCode() {
        return Objects.hashCode(idAmigo);
    }
}
