package com.br.alertanatural.controllers;

import com.br.alertanatural.DTOs.AmigosDTO;
import com.br.alertanatural.models.Amigos;
import com.br.alertanatural.models.Usuarios;
import com.br.alertanatural.services.AmigosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/amigos")
@Tag(name = "Amigos", description = "Endpoints relacionados aos amigos")
public class AmigosController {

    @Autowired
    private AmigosService amigosService;

    // Endpoint para adicionar um amigo
    @Operation(summary = "Adicionar um novo amigo", description = "Adiciona um novo amigo entre dois usuários")
    @PostMapping("/adicionar")
    public ResponseEntity<AmigosDTO> adicionarAmigo(@RequestParam Long idUsuario, @RequestParam Long idAmigoUsuario) {
        Amigos amizade = amigosService.adicionarAmigo(idUsuario, idAmigoUsuario);
        AmigosDTO resposta = new AmigosDTO(amizade);
        return ResponseEntity.ok(resposta);
    }

    @Operation(summary = "Listar usuários que o usuário está seguindo", description = "Retorna uma lista dos usuários que o usuário está seguindo")
    @GetMapping("/seguindo/{idUsuario}")
    public ResponseEntity<List<AmigosDTO>> listarSeguindo(@PathVariable Long idUsuario) {
        List<AmigosDTO> seguindo = amigosService.listarSeguindo(idUsuario).stream()
                .map(AmigosDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(seguindo);
    }

    // Endpoint para listar os seguidores do usuário
    @Operation(summary = "Listar seguidores de um usuário", description = "Retorna uma lista dos seguidores de um usuário")
    @GetMapping("/seguidores/{idUsuario}")
    public ResponseEntity<List<AmigosDTO>> listarSeguidores(@PathVariable Long idUsuario) {
        List<AmigosDTO> seguidores = amigosService.listarSeguidores(idUsuario).stream()
                .map(AmigosDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(seguidores);
    }

    @Operation(summary = "Buscar idUsuario do amigo pelo idAmigo", description = "Retorna o idUsuario do amigo com base no idAmigo")
    @GetMapping("/buscar-idusuario/{idAmigo}")
    public ResponseEntity<Long> buscarIdUsuarioPorAmigo(@PathVariable Long idAmigo) {
        Long idUsuario = amigosService.buscarIdUsuarioPorAmigo(idAmigo);
        if (idUsuario != null) {
            return ResponseEntity.ok(idUsuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Remover um amigo", description = "Remove um amigo da lista de amigos do usuário")
    @DeleteMapping("/remover")
    public ResponseEntity<Void> removerAmigo(@RequestParam Long idUsuario, @RequestParam Long idAmigoUsuario) {
        try {
            boolean amizadeExiste = amigosService.verificarAmizade(idUsuario, idAmigoUsuario);
            if (!amizadeExiste) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            amigosService.removerAmigo(idUsuario, idAmigoUsuario);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.err.println("Erro ao remover amigo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
