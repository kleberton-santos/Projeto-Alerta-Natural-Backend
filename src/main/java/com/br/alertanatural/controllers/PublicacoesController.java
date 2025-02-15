package com.br.alertanatural.controllers;

import com.br.alertanatural.DTOs.PublicacaoDTO;
import com.br.alertanatural.models.Fotos;
import com.br.alertanatural.models.Publicacoes;
import com.br.alertanatural.repositories.PublicacaoRepository;
import com.br.alertanatural.services.PublicacoesService;
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

    @Operation(summary = "Criar uma nova publicação", description = "Cria uma nova publicação com o texto fornecido")
    @PostMapping
    public ResponseEntity<Publicacoes> criarPublicacao(@RequestBody PublicacaoDTO publicacaoDTO) {
        Publicacoes publicacaoCriada = publicacoesService.criarPublicacao(publicacaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(publicacaoCriada);
    }

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



    @Operation(summary = "Listar todas as publicações", description = "Retorna todas as publicações disponíveis")
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
}
