package com.br.alertanatural.controllers;

import com.br.alertanatural.DTOs.AmigosDTO;
import com.br.alertanatural.models.Amigos;
import com.br.alertanatural.models.Usuarios;
import com.br.alertanatural.services.AmigosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/amigos")
public class AmigosController {

    @Autowired
    private AmigosService amigosService;

    // Endpoint para adicionar um amigo
    @PostMapping("/adicionar")
    public ResponseEntity<AmigosDTO> adicionarAmigo(@RequestParam Long idUsuario, @RequestParam Long idAmigoUsuario) {
        Amigos amizade = amigosService.adicionarAmigo(idUsuario, idAmigoUsuario);
        AmigosDTO resposta = new AmigosDTO(amizade);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/seguindo/{idUsuario}")
    public ResponseEntity<List<AmigosDTO>> listarSeguindo(@PathVariable Long idUsuario) {
        List<AmigosDTO> seguindo = amigosService.listarSeguindo(idUsuario).stream()
                .map(AmigosDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(seguindo);
    }

    // Endpoint para listar os seguidores do usuário
    @GetMapping("/seguidores/{idUsuario}")
    public ResponseEntity<List<AmigosDTO>> listarSeguidores(@PathVariable Long idUsuario) {
        List<AmigosDTO> seguidores = amigosService.listarSeguidores(idUsuario).stream()
                .map(AmigosDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(seguidores);
    }
}
