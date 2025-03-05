package com.br.alertanatural.services;

import com.br.alertanatural.DTOs.ComentarioDTO;
import com.br.alertanatural.models.Comentario;
import com.br.alertanatural.models.Publicacoes;
import com.br.alertanatural.models.Usuarios;
import com.br.alertanatural.repositories.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    // Adicionar um novo comentário
    public Comentario adicionarComentario(Publicacoes publicacao, Usuarios usuario, String texto) {
        Comentario comentario = new Comentario();
        comentario.setPublicacoes(publicacao);
        comentario.setUsuarios(usuario);
        comentario.setTexto(texto);
        return comentarioRepository.save(comentario);
    }

    // Listar comentários de uma publicação
    public List<ComentarioDTO> listarComentariosPorPublicacao(Publicacoes publicacao) {
        List<Comentario> comentarios = comentarioRepository.findByPublicacao(publicacao);
        return comentarios.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Editar um comentário
    public Comentario editarComentario(Long idComentario, String novoTexto) {
        Comentario comentario = comentarioRepository.findById(idComentario)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado"));
        comentario.setTexto(novoTexto);
        return comentarioRepository.save(comentario);
    }

    // Remover um comentário
    public void deletarComentario(Long idComentario) {
        comentarioRepository.deleteById(idComentario);
    }

    public Comentario buscarComentarioPorId(Long idComentario) {
        return comentarioRepository.findById(idComentario)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado"));
    }

    // Converter Comentario para ComentarioDTO
    public ComentarioDTO toDTO(Comentario comentario) {
        ComentarioDTO dto = new ComentarioDTO();
        dto.setId(comentario.getId());
        dto.setTexto(comentario.getTexto());
        dto.setDataCadastro(comentario.getDataCadastro());
        dto.setIdUsuario(comentario.getUsuarios().getIdusuario());
        dto.setNomeUsuario(comentario.getUsuarios().getNome());
        return dto;
    }
}

