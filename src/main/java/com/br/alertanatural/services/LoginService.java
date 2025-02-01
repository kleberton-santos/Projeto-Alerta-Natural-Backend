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

    public boolean autenticar(String email, String senha) {
        Optional<Usuarios> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuarios usuario = usuarioOpt.get();
            return passwordEncoder.matches(senha, usuario.getSenha()); // Comparando senha digitada com a criptografada
        }
        return false;
    }
}