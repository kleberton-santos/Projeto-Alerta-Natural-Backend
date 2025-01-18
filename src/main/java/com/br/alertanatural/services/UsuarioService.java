package com.br.alertanatural.services;

import com.br.alertanatural.DTOs.LoginDTO;
import com.br.alertanatural.models.Usuarios;
import com.br.alertanatural.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuarios criarLogin(LoginDTO loginDTO) {
        Optional<Usuarios> usuarioOptional = usuarioRepository.findByEmail(loginDTO.getEmail());

        if (usuarioOptional.isPresent()) {
            Usuarios usuario = usuarioOptional.get();
            if (loginDTO.getSenha().equals(usuario.getSenha())) {
                return usuario;
            } else {
                throw new RuntimeException("Senha incorreta");
            }
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

    public List<LoginDTO> listarLoging() {
        List<Usuarios> usuarios = usuarioRepository.findAll();

        // Convertendo a lista de Usuários para uma lista de LoginDTO
        return usuarios.stream()
                .map(usuario -> new LoginDTO(usuario.getEmail(), null)) // Senha é null pois não deve ser exposta
                .collect(Collectors.toList());
    }

    public Usuarios criarUsuario(Usuarios usuarios){
        return usuarioRepository.save(usuarios);
    }

    public LoginDTO listarLoginPorId(Long id) {
        Optional<Usuarios> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            Usuarios usuario = usuarioOptional.get();
            return new LoginDTO(usuario.getEmail(), null); // Senha é null
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

    public List<Usuarios> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuarios> listarUsuariosPorId(Long id){
        return usuarioRepository.findById(id);
    }

    public void deletarUsuario(Long id){
         usuarioRepository.deleteById(id);
    }

}
