package com.br.alertanatural.services;

import com.br.alertanatural.DTOs.LoginDTO;
import com.br.alertanatural.models.Usuarios;
import com.br.alertanatural.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public Usuarios criarUsuario(Usuarios usuarios){
        return usuarioRepository.save(usuarios);
    }

    public List<Usuarios> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuarios> listarUsuariosPorId(Long id){
        return usuarioRepository.findById(id);
    }

    public Usuarios editarUsuario(Long id, Usuarios usuarioAtualizado) {
        Optional<Usuarios> usuarioExistente = usuarioRepository.findById(id);

        if (usuarioExistente.isPresent()) {
            Usuarios usuario = usuarioExistente.get();

            // Atualiza os campos simples
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setSobreNome(usuarioAtualizado.getSobreNome());
            usuario.setCpf(usuarioAtualizado.getCpf());
            usuario.setTelefone(usuarioAtualizado.getTelefone());
            usuario.setEmail(usuarioAtualizado.getEmail());
            usuario.setSenha(usuarioAtualizado.getSenha());
            usuario.setFoto(usuarioAtualizado.getFoto());

            // Atualiza relacionamentos, se necessário (exemplo: atualizar fotos)
            if (usuarioAtualizado.getFotos() != null) {
                usuario.setFotos(usuarioAtualizado.getFotos());
            }

            // Atualiza a lista de publicações, se necessário
            if (usuarioAtualizado.getPublicacoes() != null) {
                usuario.setPublicacoes(usuarioAtualizado.getPublicacoes());
            }

            // Atualiza a lista de amigos, se necessário
            if (usuarioAtualizado.getAmigos() != null) {
                usuario.setAmigos(usuarioAtualizado.getAmigos());
            }

            // Atualiza a lista de seguidores, se necessário
            if (usuarioAtualizado.getSeguidores() != null) {
                usuario.setSeguidores(usuarioAtualizado.getSeguidores());
            }

            // Atualiza a data de atualização
            usuario.setDataAtualizacao(new Date());

            // Salva o usuário atualizado no banco de dados
            return usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

    public void deletarUsuario(Long id){
         usuarioRepository.deleteById(id);
    }

}
