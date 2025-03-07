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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/fotos") // Mapeia os endpoints para fotos
@Tag(name = "Fotos", description = "Endpoints relacionados às fotos") // Definindo o nome e descrição para a documentação Swagger
public class FotoController {

    // Caminho da imagem padrão
    private static final String UPLOAD_DIR = "C:/foto-alerta"; // Diretório onde as imagens estão armazenadas

    @Autowired
    private FotoService fotoService; // Serviço responsável pelas operações relacionadas às fotos

    // Endpoint para salvar uma nova foto
    @Operation(summary = "Salvar uma nova foto", description = "Salva uma foto na plataforma") // Descrição da operação para documentação Swagger
    @PostMapping // Mapeia a requisição POST para salvar a foto
    public FotosDTO salvarFoto(@RequestBody FotosDTO fotoDTO) {
        return fotoService.salvarFoto(fotoDTO); // Chama o serviço para salvar a foto e retorna o objeto salvo
    }

    // Endpoint para listar fotos de um usuário
    @Operation(summary = "Listar fotos de um usuário", description = "Retorna as fotos de um usuário específico") // Descrição da operação para documentação Swagger
    @GetMapping("/usuario/{idUsuario}") // Mapeia a requisição GET para listar fotos por ID do usuário
    public List<FotosDTO> listarFotosPorUsuario(@PathVariable Long idUsuario) {
        return fotoService.listarFotosPorUsuario(idUsuario); // Chama o serviço para listar as fotos do usuário
    }

    // Endpoint para listar fotos de uma publicação
    @Operation(summary = "Listar fotos de uma publicação", description = "Retorna as fotos associadas a uma publicação") // Descrição da operação para documentação Swagger
    @GetMapping("/publicacao/{idPublicacao}") // Mapeia a requisição GET para listar fotos por ID da publicação
    public List<FotosDTO> listarFotosPorPublicacao(@PathVariable Long idPublicacao) {
        return fotoService.listarFotosPorPublicacao(idPublicacao); // Chama o serviço para listar as fotos da publicação
    }

    // Endpoint para servir a imagem padrão
    @Operation(summary = "Obter imagem padrão", description = "Retorna a imagem padrão (icon_user.png)") // Descrição da operação para documentação Swagger
    @GetMapping("/padrao") // Mapeia a requisição GET para retornar a imagem padrão
    public ResponseEntity<Resource> getImagemPadrao() {
        try {
            // Define o caminho da imagem padrão
            Path caminhoImagem = Paths.get(UPLOAD_DIR).resolve("icon_user.png"); // Caminho completo da imagem padrão
            Resource resource = new UrlResource(caminhoImagem.toUri()); // Cria um recurso para acessar a imagem

            // Verifica se a imagem existe e é legível
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok() // Retorna a imagem com status OK
                        .contentType(MediaType.IMAGE_PNG) // Define o tipo de conteúdo como PNG
                        .body(resource); // Envia o recurso da imagem como resposta
            } else {
                throw new RuntimeException("Imagem padrão não encontrada"); // Lança erro caso a imagem não seja encontrada
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar a imagem padrão", e); // Lança erro em caso de falha ao carregar a imagem
        }
    }

    // Endpoint para fazer upload de vídeos
    @Operation(summary = "Fazer upload de vídeo", description = "Faz o upload de um vídeo para o servidor")
    @PostMapping("/upload-video")
    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file) {
        try {
            Path uploadPath = Paths.get("C:/foto-alerta");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), uploadPath.resolve(fileName));

            return ResponseEntity.ok(fileName);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar o vídeo: " + e.getMessage());
        }
    }

    @Operation(summary = "Obter vídeo", description = "Retorna um vídeo pelo nome do arquivo")
    @GetMapping("/video/{filename}")
    public ResponseEntity<Resource> getVideo(@PathVariable String filename) {
        try {
            Path videoPath = Paths.get("C:/foto-alerta").resolve(filename);
            Resource resource = new UrlResource(videoPath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("video/mp4")) // Define o tipo de conteúdo como video/mp4
                        .body(resource);
            } else {
                throw new RuntimeException("Vídeo não encontrado: " + filename);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar o vídeo: " + e.getMessage());
        }
    }
}
