package com.br.alertanatural.controllers;

import com.br.alertanatural.DTOs.FotosDTO;
import com.br.alertanatural.models.Fotos;
import com.br.alertanatural.services.FotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fotos")
@Tag(name = "Fotos", description = "Endpoints relacionados às fotos")
public class FotoController {

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
}
