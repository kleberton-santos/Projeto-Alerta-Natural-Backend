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

    /**
     * Adiciona uma relação de amizade entre dois usuários.
     * @param idUsuario ID do usuário que deseja adicionar um amigo.
     * @param idAmigoUsuario ID do usuário que será adicionado como amigo.
     * @return A entidade de amizade salva no banco de dados.
     */
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

    /**
     * Lista os usuários que o usuário específico está seguindo.
     * @param idUsuario ID do usuário.
     * @return Lista de amigos que o usuário segue.
     */
    public List<Amigos> listarSeguindo(Long idUsuario) {
        return amigosRepository.findAll().stream()
                .filter(a -> a.getUsuario().getIdusuario().equals(idUsuario))
                .collect(Collectors.toList());
    }

    /**
     * Lista os seguidores de um usuário específico.
     * @param idUsuario ID do usuário.
     * @return Lista de amigos que seguem o usuário.
     */
    public List<Amigos> listarSeguidores(Long idUsuario) {
        return amigosRepository.findAll().stream()
                .filter(a -> a.getAmigo().getIdusuario().equals(idUsuario))
                .collect(Collectors.toList());
    }

    /**
     * Busca o ID do usuário associado a um amigo.
     * @param idAmigo ID do amigo.
     * @return ID do usuário correspondente ou null se não encontrado.
     */
    public Long buscarIdUsuarioPorAmigo(Long idAmigo) {
        Optional<Amigos> amigo = amigosRepository.findById(idAmigo);
        return amigo.map(Amigos::getAmigo).map(Usuarios::getIdusuario).orElse(null);
    }

    /**
     * Verifica se há uma relação de amizade entre dois usuários.
     * @param idUsuario ID do usuário.
     * @param idAmigoUsuario ID do amigo.
     * @return true se a amizade existir, false caso contrário.
     */
    public boolean verificarAmizade(Long idUsuario, Long idAmigoUsuario) {
        return amigosRepository.existsByUsuarioIdusuarioAndAmigoIdusuario(idUsuario, idAmigoUsuario);
    }

    /**
     * Remove uma relação de amizade entre dois usuários.
     * @param idUsuario ID do usuário.
     * @param idAmigoUsuario ID do amigo.
     */
    @Transactional
    public void removerAmigo(Long idUsuario, Long idAmigoUsuario) {
        amigosRepository.deleteByUsuarioIdAndAmigoId(idUsuario, idAmigoUsuario);
    }
}
