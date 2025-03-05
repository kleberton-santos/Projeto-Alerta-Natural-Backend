package com.br.alertanatural.services;

import com.br.alertanatural.models.Usuarios;
import com.br.alertanatural.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Alteração: agora injetando PasswordEncoder em vez de BCryptPasswordEncoder

    // Método para autenticar um usuário com email e senha
    public boolean autenticar(String email, String senha) {
        // Busca o usuário no banco de dados pelo email
        Optional<Usuarios> usuarioOpt = usuarioRepository.findByEmail(email);

        // Se o usuário for encontrado
        if (usuarioOpt.isPresent()) {
            Usuarios usuario = usuarioOpt.get();
            // Compara a senha digitada com a senha criptografada armazenada no banco de dados
            return passwordEncoder.matches(senha, usuario.getSenha()); // Retorna true se as senhas coincidirem
        }
        // Se o usuário não for encontrado, retorna false
        return false;
    }
}
