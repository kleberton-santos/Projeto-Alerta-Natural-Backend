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

    // Adiciona um novo comentário a uma publicação específica por um usuário
    public Comentario adicionarComentario(Publicacoes publicacao, Usuarios usuario, String texto) {
        Comentario comentario = new Comentario(); // Cria um novo objeto Comentario
        comentario.setPublicacoes(publicacao); // Associa a publicação ao comentário
        comentario.setUsuarios(usuario); // Associa o usuário ao comentário
        comentario.setTexto(texto); // Define o texto do comentário
        return comentarioRepository.save(comentario); // Salva o comentário no banco de dados
    }

    // Lista todos os comentários de uma determinada publicação
    public List<ComentarioDTO> listarComentariosPorPublicacao(Publicacoes publicacao) {
        List<Comentario> comentarios = comentarioRepository.findByPublicacao(publicacao); // Recupera os comentários da publicação
        return comentarios.stream() // Converte a lista de Comentario para uma lista de ComentarioDTO
                .map(this::toDTO) // Mapeia cada Comentario para ComentarioDTO
                .collect(Collectors.toList()); // Coleta os resultados em uma lista
    }

    // Edita um comentário existente
    public Comentario editarComentario(Long idComentario, String novoTexto) {
        Comentario comentario = comentarioRepository.findById(idComentario) // Busca o comentário pelo ID
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado")); // Lança exceção caso o comentário não exista
        comentario.setTexto(novoTexto); // Atualiza o texto do comentário
        return comentarioRepository.save(comentario); // Salva as alterações no banco de dados
    }

    // Deleta um comentário pelo ID
    public void deletarComentario(Long idComentario) {
        comentarioRepository.deleteById(idComentario); // Deleta o comentário com o ID fornecido
    }

    // Busca um comentário específico pelo ID
    public Comentario buscarComentarioPorId(Long idComentario) {
        return comentarioRepository.findById(idComentario) // Busca o comentário pelo ID
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado")); // Lança exceção caso o comentário não exista
    }

    // Converte um objeto Comentario para um objeto ComentarioDTO
    public ComentarioDTO toDTO(Comentario comentario) {
        ComentarioDTO dto = new ComentarioDTO(); // Cria uma instância de ComentarioDTO
        dto.setId(comentario.getId()); // Define o ID do comentário
        dto.setTexto(comentario.getTexto()); // Define o texto do comentário
        dto.setDataCadastro(comentario.getDataCadastro()); // Define a data de cadastro do comentário
        dto.setIdUsuario(comentario.getUsuarios().getIdusuario()); // Define o ID do usuário que fez o comentário
        dto.setNomeUsuario(comentario.getUsuarios().getNome()); // Define o nome do usuário que fez o comentário
        return dto; // Retorna o objeto ComentarioDTO
    }
}
