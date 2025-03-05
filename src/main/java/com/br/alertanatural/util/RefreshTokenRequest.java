package com.br.alertanatural.util;

/**
 * Classe para representar a requisição de renovação de token.
 */
public class RefreshTokenRequest {

    private String refreshToken; // Token de atualização enviado pelo cliente

    /**
     * Obtém o refresh token.
     * @return O token de atualização.
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Define o refresh token.
     * @param refreshToken O novo token de atualização.
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
