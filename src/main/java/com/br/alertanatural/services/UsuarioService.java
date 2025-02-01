package com.br.alertanatural.services;

import com.br.alertanatural.DTOs.LoginDTO;
import com.br.alertanatural.models.Usuarios;
import com.br.alertanatural.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuarios criarUsuario(Usuarios usuario) {
        // Criptografar a senha antes de salvar no banco
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public List<Usuarios> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuarios> listarUsuariosPorId(Long id){
        return usuarioRepository.findById(id);
    }

    public Usuarios editarUsuario(Long id, Usuarios usuarioAtualizado) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setEmail(usuarioAtualizado.getEmail());
                    usuario.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha())); // Recriptografar senha ao editar
                    return usuarioRepository.save(usuario);
                }).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void deletarUsuario(Long id){
         usuarioRepository.deleteById(id);
    }

}
