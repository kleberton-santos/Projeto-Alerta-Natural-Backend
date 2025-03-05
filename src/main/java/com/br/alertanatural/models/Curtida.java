package com.br.alertanatural.models;

import jakarta.persistence.*;

@Entity // Indica que a classe é uma entidade JPA mapeada para uma tabela no banco de dados
@Table(name = "curtidas") // Define o nome da tabela no banco de dados
public class Curtida {

    @Id // Define o campo 'idCurtida' como a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define o comportamento de geração automática do valor para a chave primária
    private Long idCurtida;

    @ManyToOne // Define o relacionamento muitos-para-um entre 'Curtida' e 'Usuarios'
    @JoinColumn(name = "idusuario", nullable = false) // Define a coluna de relacionamento e define que ela não pode ser nula
    private Usuarios usuario;

    @ManyToOne // Define o relacionamento muitos-para-um entre 'Curtida' e 'Publicacoes'
    @JoinColumn(name = "idPublicacao", nullable = false) // Define a coluna de relacionamento e define que ela não pode ser nula
    private Publicacoes publicacao;

    // Métodos getter e setter para acessar e modificar os valores dos atributos

    public Long getIdCurtida() {
        return idCurtida;
    }

    public void setIdCurtida(Long idCurtida) {
        this.idCurtida = idCurtida;
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
}
