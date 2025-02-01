package com.br.alertanatural.controllers;

import com.br.alertanatural.DTOs.FotosDTO;
import com.br.alertanatural.models.Fotos;
import com.br.alertanatural.services.FotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fotos")
public class FotoController {

    @Autowired
    private FotoService fotoService;

    // Endpoint para salvar uma nova foto
    @PostMapping
    public FotosDTO salvarFoto(@RequestBody FotosDTO fotoDTO) {
        return fotoService.salvarFoto(fotoDTO);
    }

    // Endpoint para listar fotos de um usuário
    @GetMapping("/usuario/{idUsuario}")
    public List<FotosDTO> listarFotosPorUsuario(@PathVariable Long idUsuario) {
        return fotoService.listarFotosPorUsuario(idUsuario);
    }

    // Endpoint para listar fotos de uma publicação
    @GetMapping("/publicacao/{idPublicacao}")
    public List<FotosDTO> listarFotosPorPublicacao(@PathVariable Long idPublicacao) {
        return fotoService.listarFotosPorPublicacao(idPublicacao);
    }
}
