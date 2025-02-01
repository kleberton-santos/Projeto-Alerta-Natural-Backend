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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        try {
            if (publicacaoDTO.getIdUsuario() == null || publicacaoDTO.getTexto() == null || publicacaoDTO.getTexto().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            Publicacoes publicacaoCriada = publicacoesService.criarPublicacao(publicacaoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(publicacaoCriada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Operation(summary = "Listar todas as publicações", description = "Retorna todas as publicações disponíveis")
    @GetMapping
    public ResponseEntity<List<Publicacoes>> listarPublicacoes() {
        List<Publicacoes> publicacoes = publicacoesService.listarPublicacoes();
        return ResponseEntity.ok(publicacoes);
    }

    @Operation(summary = "Buscar publicação por ID", description = "Retorna a publicação correspondente ao ID fornecido")
    @GetMapping("/{id}")
    public ResponseEntity<Publicacoes> buscarPublicacaoPorId(@PathVariable Long id) {
        try {
            Publicacoes publicacao = publicacoesService.buscarPublicacaoPorId(id);
            return ResponseEntity.ok(publicacao);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Editar publicação", description = "Edita a publicação existente com base no ID fornecido")
    @PutMapping("/{id}")
    public ResponseEntity<Publicacoes> editarPublicacao(@PathVariable Long id,
                                                        @RequestBody PublicacaoDTO publicacaoDTO) {
        try {
            Publicacoes publicacaoAtualizada = publicacoesService.editarPublicacao(id, publicacaoDTO);
            return ResponseEntity.ok(publicacaoAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @Operation(summary = "Deletar publicação", description = "Deleta a publicação correspondente ao ID fornecido")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPublicacao(@PathVariable Long id) {
        try {
            publicacoesService.deletarPublicacao(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



}
