package com.br.alertanatural.controllers;

import com.br.alertanatural.DTOs.AmigosDTO;
import com.br.alertanatural.models.Amigos;
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
@RequestMapping("/amigos") // Define a URL base para todos os endpoints deste controlador
@Tag(name = "Amigos", description = "Endpoints relacionados aos amigos") // Documentação do Swagger
public class AmigosController {

    @Autowired
    private AmigosService amigosService; // Injeção de dependência do serviço de amigos

    // Endpoint para adicionar um amigo
    @Operation(summary = "Adicionar um novo amigo", description = "Adiciona um novo amigo entre usuários") // Descrição no Swagger
    @PostMapping("/adicionar") // Define o método HTTP POST para adicionar um amigo
    public ResponseEntity<AmigosDTO> adicionarAmigo(@RequestParam Long idUsuario, @RequestParam Long idAmigoUsuario) {
        Amigos amizade = amigosService.adicionarAmigo(idUsuario, idAmigoUsuario); // Chama o serviço para adicionar a amizade
        AmigosDTO resposta = new AmigosDTO(amizade); // Converte o objeto de amizade para o formato DTO
        return ResponseEntity.ok(resposta); // Retorna a resposta com status 200 e o DTO do amigo
    }

    @Operation(summary = "Listar usuários que o usuário está seguindo", description = "Retorna uma lista dos usuários que o usuário está seguindo") // Descrição no Swagger
    @GetMapping("/seguindo/{idUsuario}") // Define o método HTTP GET para listar amigos seguidos
    public ResponseEntity<List<AmigosDTO>> listarSeguindo(@PathVariable Long idUsuario) {
        List<AmigosDTO> seguindo = amigosService.listarSeguindo(idUsuario).stream() // Busca a lista de usuários seguidos
                .map(AmigosDTO::new) // Converte cada objeto Amigos para o formato DTO
                .collect(Collectors.toList()); // Coleta os resultados em uma lista
        return ResponseEntity.ok(seguindo); // Retorna a lista de amigos seguidos com status 200
    }

    // Endpoint para listar os seguidores do usuário
    @Operation(summary = "Listar seguidores de um usuário", description = "Retorna uma lista dos seguidores de um usuário") // Descrição no Swagger
    @GetMapping("/seguidores/{idUsuario}") // Define o método HTTP GET para listar seguidores
    public ResponseEntity<List<AmigosDTO>> listarSeguidores(@PathVariable Long idUsuario) {
        List<AmigosDTO> seguidores = amigosService.listarSeguidores(idUsuario).stream() // Busca a lista de seguidores
                .map(AmigosDTO::new) // Converte cada objeto Amigos para o formato DTO
                .collect(Collectors.toList()); // Coleta os resultados em uma lista
        return ResponseEntity.ok(seguidores); // Retorna a lista de seguidores com status 200
    }

    @Operation(summary = "Buscar idUsuario do amigo pelo idAmigo", description = "Retorna o idUsuario do amigo com base no idAmigo") // Descrição no Swagger
    @GetMapping("/buscar-idusuario/{idAmigo}") // Define o método HTTP GET para buscar o id do usuário pelo id do amigo
    public ResponseEntity<Long> buscarIdUsuarioPorAmigo(@PathVariable Long idAmigo) {
        Long idUsuario = amigosService.buscarIdUsuarioPorAmigo(idAmigo); // Chama o serviço para buscar o id do usuário
        if (idUsuario != null) {
            return ResponseEntity.ok(idUsuario); // Retorna o idUsuario com status 200 se encontrado
        } else {
            return ResponseEntity.notFound().build(); // Retorna status 404 se o idUsuario não for encontrado
        }
    }

    @Operation(summary = "Remover um amigo", description = "Remove um amigo da lista de amigos do usuário") // Descrição no Swagger
    @DeleteMapping("/remover") // Define o método HTTP DELETE para remover a amizade
    public ResponseEntity<Void> removerAmigo(@RequestParam Long idUsuario, @RequestParam Long idAmigoUsuario) {
        try {
            boolean amizadeExiste = amigosService.verificarAmizade(idUsuario, idAmigoUsuario); // Verifica se a amizade existe
            if (!amizadeExiste) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna status 404 se a amizade não existir
            }

            amigosService.removerAmigo(idUsuario, idAmigoUsuario); // Chama o serviço para remover a amizade
            return ResponseEntity.noContent().build(); // Retorna status 204 indicando que a operação foi bem-sucedida
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna status 500 em caso de erro
        }
    }

    @Operation(summary = "Verificar se um usuário está seguindo outro", description = "Retorna true se o usuário está seguindo o amigo, caso contrário, retorna false") // Descrição no Swagger
    @GetMapping("/verificar-amizade") // Define o método HTTP GET para verificar a amizade
    public ResponseEntity<Boolean> verificarAmizade(
            @RequestParam Long idUsuario,
            @RequestParam Long idAmigoUsuario) {
        boolean amizadeExiste = amigosService.verificarAmizade(idUsuario, idAmigoUsuario); // Verifica se a amizade existe
        return ResponseEntity.ok(amizadeExiste); // Retorna o resultado da verificação com status 200
    }

}
