package com.br.alertanatural.util;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Classe utilitária para geração de chaves secretas seguras.
 */
public class KeyGenerator {

    /**
     * Método principal que gera e exibe uma chave secreta aleatória codificada em Base64.
     * A chave gerada possui 64 bytes (512 bits), adequada para assinaturas seguras.
     */
    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom(); // Gerador de números aleatórios seguros
        byte[] key = new byte[64]; // Tamanho da chave em bytes (512 bits)
        secureRandom.nextBytes(key); // Preenche o array com bytes aleatórios

        String encodedKey = Base64.getEncoder().encodeToString(key); // Codifica a chave em Base64
        System.out.println("Sua chave secreta gerada: " + encodedKey); // Exibe a chave gerada
    }
}