package com.br.alertanatural.util;

/**
 * Classe que representa a resposta contendo tokens JWT e informações do usuário autenticado.
 */
public class JwtResponse {

    private String accessToken; // Token de acesso JWT
    private String refreshToken; // Token de atualização JWT
    private Long id; // ID do usuário autenticado
    private String nome; // Nome do usuário
    private String foto; // URL da foto do usuário

    /**
     * Construtor padrão sem argumentos.
     */
    public JwtResponse() {
    }

    /**
     * Construtor que inicializa apenas os tokens.
     * @param accessToken Token de acesso JWT.
     * @param refreshToken Token de atualização JWT.
     */
    public JwtResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    /**
     * Construtor que inicializa os tokens e informações do usuário.
     * @param accessToken Token de acesso JWT.
     * @param refreshToken Token de atualização JWT.
     * @param id ID do usuário autenticado.
     * @param nome Nome do usuário.
     * @param foto URL da foto do usuário.
     */
    public JwtResponse(String accessToken, String refreshToken, Long id, String nome, String foto) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.nome = nome;
        this.foto = foto;
    }

    /**
     * Obtém o token de acesso JWT.
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Define o token de acesso JWT.
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Obtém o token de atualização JWT.
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Define o token de atualização JWT.
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Obtém o ID do usuário autenticado.
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID do usuário autenticado.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém o nome do usuário autenticado.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do usuário autenticado.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a URL da foto do usuário autenticado.
     */
    public String getFoto() {
        return foto;
    }

    /**
     * Define a URL da foto do usuário autenticado.
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }
}
