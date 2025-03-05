package com.br.alertanatural.controllers;

import com.br.alertanatural.DTOs.ComentarioDTO;
import com.br.alertanatural.DTOs.PublicacaoDTO;
import com.br.alertanatural.models.Comentario;
import com.br.alertanatural.models.Fotos;
import com.br.alertanatural.models.Publicacoes;
import com.br.alertanatural.models.Usuarios;
import com.br.alertanatural.repositories.PublicacaoRepository;
import com.br.alertanatural.services.ComentarioService;
import com.br.alertanatural.services.CurtidaService;
import com.br.alertanatural.services.PublicacoesService;
import com.br.alertanatural.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/publicacoes")
@Tag(name = "Publicações", description = "Endpoints relacionados às publicações")
public class PublicacoesController {

    @Autowired
    private PublicacoesService publicacoesService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private CurtidaService curtidaService;

    @Operation(summary = "Criar uma nova publicação", description = "Cria uma nova publicação com o texto fornecido")
    @PostMapping
    public ResponseEntity<Publicacoes> criarPublicacao(@RequestBody PublicacaoDTO publicacaoDTO) {
        Publicacoes publicacaoCriada = publicacoesService.criarPublicacao(publicacaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(publicacaoCriada);
    }

    @Operation(summary = "Fazer upload de arquivos (fotos e vídeos) para uma publicação", description = "Permite o upload de fotos e vídeos para uma publicação existente")
    @PostMapping(value = "/{id}/arquivos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadArquivos(
            @PathVariable Long id,
            @RequestParam(value = "fotos", required = false) List<MultipartFile> fotos, // fotos é opcional
            @RequestParam(value = "videos", required = false) List<MultipartFile> videos) { // videos é opcional

        // Listas para armazenar os caminhos das fotos e vídeos
        List<String> fotosCaminhos = new ArrayList<>();
        List<String> videosCaminhos = new ArrayList<>();

        // Processar fotos, se enviadas
        if (fotos != null && !fotos.isEmpty()) {
            for (MultipartFile foto : fotos) {
                String caminhoFoto = publicacoesService.salvarArquivo(foto, publicacoesService.getDiretorioFotos());
                fotosCaminhos.add(caminhoFoto);
            }
        }

        // Processar vídeos, se enviados
        if (videos != null && !videos.isEmpty()) {
            for (MultipartFile video : videos) {
                String caminhoVideo = publicacoesService.salvarArquivo(video, publicacoesService.getDiretorioFotos());
                videosCaminhos.add(caminhoVideo);
            }
        }

        // Passa os caminhos para o serviço
        publicacoesService.salvarArquivos(id, fotosCaminhos, videosCaminhos);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Listar todas as publicações", description = "Retorna todas as publicações disponíveis, ordenadas por data de cadastro")
    @GetMapping
    public ResponseEntity<List<PublicacaoDTO>> listarPublicacoes() {
        List<PublicacaoDTO> publicacoes = publicacoesService.listarPublicacoes();
        return ResponseEntity.ok(publicacoes);
    }

    @Operation(summary = "Buscar publicação por ID", description = "Retorna a publicação correspondente ao ID fornecido")
    @GetMapping("/{id}")
    public ResponseEntity<Publicacoes> buscarPublicacaoPorId(@PathVariable Long id) {
        Publicacoes publicacao = publicacoesService.buscarPublicacaoPorId(id);
        return ResponseEntity.ok(publicacao);
    }

    @Operation(summary = "Buscar publicações por ID do usuário", description = "Retorna todas as publicações de um usuário específico")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PublicacaoDTO>> buscarPublicacoesPorUsuario(@PathVariable Long idUsuario) {
        List<PublicacaoDTO> publicacoes = publicacoesService.buscarPublicacoesPorUsuario(idUsuario);
        return ResponseEntity.ok(publicacoes);
    }

    @Operation(summary = "Editar publicação", description = "Edita a publicação existente com base no ID fornecido")
    @PutMapping("/{id}")
    public ResponseEntity<Publicacoes> editarPublicacao(@PathVariable Long id, @RequestBody PublicacaoDTO publicacaoDTO) {
        Publicacoes publicacaoAtualizada = publicacoesService.editarPublicacao(id, publicacaoDTO);
        return ResponseEntity.ok(publicacaoAtualizada);
    }

    @Operation(summary = "Deletar publicação", description = "Deleta a publicação correspondente ao ID fornecido")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPublicacao(@PathVariable Long id) {
        publicacoesService.deletarPublicacao(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Buscar publicações de amigos", description = "Retorna todas as publicações dos amigos de um usuário específico")
    @GetMapping("/amigos/{idUsuario}")
    public ResponseEntity<List<PublicacaoDTO>> buscarPublicacoesAmigos(@PathVariable Long idUsuario) {
        List<PublicacaoDTO> publicacoes = publicacoesService.buscarPublicacoesAmigos(idUsuario);
        return ResponseEntity.ok(publicacoes);
    }

    @Operation(summary = "Deletar publicação por ID do usuário", description = "Deleta uma publicação específica do usuário")
    @DeleteMapping("/usuario/{idUsuario}/{idPublicacao}")
    public ResponseEntity<Void> deletarPublicacaoPorUsuario(
            @PathVariable Long idUsuario,
            @PathVariable Long idPublicacao) {
        if (idUsuario == null || idPublicacao == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        publicacoesService.deletarPublicacaoPorUsuario(idUsuario, idPublicacao);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Adicionar comentário a uma publicação", description = "Permite que um usuário adicione um comentário a uma publicação")
    @PostMapping("/{idPublicacao}/comentarios")
    public ResponseEntity<ComentarioDTO> adicionarComentario(
            @PathVariable Long idPublicacao,
            @RequestParam Long idUsuario,
            @RequestParam String texto) {

        Publicacoes publicacao = publicacoesService.buscarPublicacaoPorId(idPublicacao);
        Usuarios usuario = usuarioService.listarUsuariosPorId(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Comentario comentario = comentarioService.adicionarComentario(publicacao, usuario, texto);
        ComentarioDTO comentarioDTO = comentarioService.toDTO(comentario);

        return ResponseEntity.status(HttpStatus.CREATED).body(comentarioDTO);
    }

    @Operation(summary = "Listar comentários de uma publicação", description = "Retorna todos os comentários de uma publicação específica")
    @GetMapping("/{idPublicacao}/comentarios")
    public ResponseEntity<List<ComentarioDTO>> listarComentarios(@PathVariable Long idPublicacao) {
        Publicacoes publicacao = publicacoesService.buscarPublicacaoPorId(idPublicacao);
        List<ComentarioDTO> comentariosDTO = comentarioService.listarComentariosPorPublicacao(publicacao);
        return ResponseEntity.ok(comentariosDTO);
    }

    @Operation(summary = "Editar um comentário", description = "Permite que um usuário edite um comentário existente em uma publicação")
    @PutMapping("/{idPublicacao}/comentarios/{idComentario}")
    public ResponseEntity<ComentarioDTO> editarComentario(
            @PathVariable Long idPublicacao,
            @PathVariable Long idComentario,
            @RequestParam String novoTexto,
            @RequestParam Long idUsuario) {

        // Verifica se o comentário pertence ao usuário
        Comentario comentario = comentarioService.buscarComentarioPorId(idComentario);
        if (!comentario.getUsuarios().getIdusuario().equals(idUsuario)) {
            throw new RuntimeException("Usuário não tem permissão para editar este comentário");
        }

        Comentario comentarioAtualizado = comentarioService.editarComentario(idComentario, novoTexto);
        ComentarioDTO comentarioDTO = comentarioService.toDTO(comentarioAtualizado);

        return ResponseEntity.ok(comentarioDTO);
    }

    @Operation(summary = "Remover um comentário", description = "Permite que um usuário remova um comentário de uma publicação")
    @DeleteMapping("/{idPublicacao}/comentarios/{idComentario}")
    public ResponseEntity<Void> removerComentario(
            @PathVariable Long idPublicacao,
            @PathVariable Long idComentario,
            @RequestParam Long idUsuario) {

        // Verifica se o comentário pertence ao usuário
        Comentario comentario = comentarioService.buscarComentarioPorId(idComentario);
        if (!comentario.getUsuarios().getIdusuario().equals(idUsuario)) {
            throw new RuntimeException("Usuário não tem permissão para remover este comentário");
        }

        comentarioService.deletarComentario(idComentario);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Curtir ou descurtir uma publicação", description = "Permite que um usuário curta ou descurta uma publicação")
    @PostMapping("/{idPublicacao}/curtir")
    public ResponseEntity<Void> curtirOuDescurtirPublicacao(
            @PathVariable Long idPublicacao,
            @RequestParam Long idUsuario) {

        // Verifica se o usuário já curtiu a publicação
        if (curtidaService.usuarioJaCurtiu(idUsuario, idPublicacao)) {
            // Se já curtiu, descurte
            curtidaService.descurtirPublicacao(idUsuario, idPublicacao);
        } else {
            // Se não curtiu, curte
            curtidaService.curtirPublicacao(idUsuario, idPublicacao);
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Contar curtidas de uma publicação", description = "Retorna o número de curtidas de uma publicação")
    @GetMapping("/{idPublicacao}/curtidas")
    public ResponseEntity<Integer> contarCurtidas(@PathVariable Long idPublicacao) {
        int curtidas = curtidaService.contarCurtidas(idPublicacao);
        return ResponseEntity.ok(curtidas);
    }
}
