package com.br.alertanatural.controllers;

import com.br.alertanatural.DTOs.FotosDTO;
import com.br.alertanatural.services.FotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

@RestController
@RequestMapping("/fotos")
@Tag(name = "Fotos", description = "Endpoints relacionados às fotos")
public class FotoController {

    // Caminho da imagem padrão
    private static final String UPLOAD_DIR = "C:/foto-alerta";

    @Autowired
    private FotoService fotoService;

    // Endpoint para salvar uma nova foto
    @Operation(summary = "Salvar uma nova foto", description = "Salva uma foto na plataforma")
    @PostMapping
    public FotosDTO salvarFoto(@RequestBody FotosDTO fotoDTO) {
        return fotoService.salvarFoto(fotoDTO);
    }

    // Endpoint para listar fotos de um usuário
    @Operation(summary = "Listar fotos de um usuário", description = "Retorna as fotos de um usuário específico")
    @GetMapping("/usuario/{idUsuario}")
    public List<FotosDTO> listarFotosPorUsuario(@PathVariable Long idUsuario) {
        return fotoService.listarFotosPorUsuario(idUsuario);
    }

    // Endpoint para listar fotos de uma publicação
    @Operation(summary = "Listar fotos de uma publicação", description = "Retorna as fotos associadas a uma publicação")
    @GetMapping("/publicacao/{idPublicacao}")
    public List<FotosDTO> listarFotosPorPublicacao(@PathVariable Long idPublicacao) {
        return fotoService.listarFotosPorPublicacao(idPublicacao);
    }

    // Endpoint para servir a imagem padrão
    @Operation(summary = "Obter imagem padrão", description = "Retorna a imagem padrão (icon_user.png)")
    @GetMapping("/padrao")
    public ResponseEntity<Resource> getImagemPadrao() {
        try {
            // Define o caminho da imagem padrão
            Path caminhoImagem = Paths.get(UPLOAD_DIR).resolve("icon_user.png");
            Resource resource = new UrlResource(caminhoImagem.toUri());

            // Verifica se a imagem existe e é legível
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG) // Define o tipo de conteúdo como PNG
                        .body(resource);
            } else {
                throw new RuntimeException("Imagem padrão não encontrada");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar a imagem padrão", e);
        }
    }
}