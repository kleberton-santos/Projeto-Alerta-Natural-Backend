package com.br.alertanatural.services;

import com.br.alertanatural.models.Amigos;
import com.br.alertanatural.models.Usuarios;
import com.br.alertanatural.repositories.AmigosRepository;
import com.br.alertanatural.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AmigosService {
    @Autowired
    private AmigosRepository amigosRepository;

    @Autowired
    private UsuarioRepository usuariosRepository;

    public Amigos adicionarAmigo(Long idUsuario, Long idAmigoUsuario) {
        Usuarios usuario = usuariosRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Usuarios amigo = usuariosRepository.findById(idAmigoUsuario)
                .orElseThrow(() -> new RuntimeException("Amigo não encontrado"));

        Amigos amizade = new Amigos();
        amizade.setUsuario(usuario);
        amizade.setAmigo(amigo);

        return amigosRepository.save(amizade);
    }

    public List<Amigos> listarSeguindo(Long idUsuario) {
        return amigosRepository.findAll().stream()
                .filter(a -> a.getUsuario().getIdusuario().equals(idUsuario))
                .collect(Collectors.toList());
    }

    // Listar seguidores do usuário
    public List<Amigos> listarSeguidores(Long idUsuario) {
        return amigosRepository.findAll().stream()
                .filter(a -> a.getAmigo().getIdusuario().equals(idUsuario))
                .collect(Collectors.toList());
    }

    public Long buscarIdUsuarioPorAmigo(Long idAmigo) {
        Optional<Amigos> amigo = amigosRepository.findById(idAmigo);
        return amigo.map(Amigos::getAmigo).map(Usuarios::getIdusuario).orElse(null);
    }

    public boolean verificarAmizade(Long idUsuario, Long idAmigoUsuario) {
        return amigosRepository.existsByUsuarioIdusuarioAndAmigoIdusuario(idUsuario, idAmigoUsuario);
    }

    @Transactional
    public void removerAmigo(Long idUsuario, Long idAmigoUsuario) {
        amigosRepository.deleteByUsuarioIdAndAmigoId(idUsuario, idAmigoUsuario);
    }




}
