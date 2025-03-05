package com.br.alertanatural.controllers;

import com.br.alertanatural.DTOs.FotosDTO;
import com.br.alertanatural.models.Usuarios;
import com.br.alertanatural.services.FotoService;
import com.br.alertanatural.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints relacionados aos usuários")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService; // Serviço responsável pelas operações de usuário

    @Autowired
    private FotoService fotoService; // Serviço responsável pelo gerenciamento das fotos

    // Endpoint para criar um novo usuário
    @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário com os dados fornecidos")
    @PostMapping
    public ResponseEntity<Usuarios> criarUsuario(@RequestBody Usuarios usuarios) {
        // Chama o serviço para criar o usuário e retorna a resposta com status CREATED
        Usuarios usuarioCriado = usuarioService.criarUsuario(usuarios);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    // Endpoint para listar todos os usuários
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários")
    @GetMapping
    public ResponseEntity<List<Usuarios>> listarUsuarios() {
        // Chama o serviço para listar todos os usuários e retorna a lista
        List<Usuarios> listaUsuarios = usuarioService.listarUsuarios();
        return ResponseEntity.status(HttpStatus.OK).body(listaUsuarios);
    }

    // Endpoint para buscar um usuário por ID
    @Operation(summary = "Buscar usuário por ID", description = "Retorna o usuário correspondente ao ID fornecido")
    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> listarUsuariosId(@PathVariable Long id) {
        // Tenta encontrar o usuário pelo ID, se encontrado, retorna os dados
        Optional<Usuarios> usuario = usuarioService.listarUsuariosPorId(id);

        if (usuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
        } else {
            // Caso o usuário não seja encontrado, retorna 404 (NOT_FOUND)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Endpoint para deletar um usuário
    @Operation(summary = "Deletar usuário", description = "Deleta o usuário correspondente ao ID fornecido")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        try {
            // Chama o serviço para deletar o usuário
            usuarioService.deletarUsuario(id);
            // Retorna status 204 (NO_CONTENT) indicando que a operação foi bem-sucedida
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            // Em caso de erro (usuário não encontrado), retorna status 404 (NOT_FOUND)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Endpoint para editar um usuário
    @Operation(summary = "Editar usuário", description = "Edita as informações do usuário baseado no ID fornecido")
    @PutMapping("/{idusuario}")
    public ResponseEntity<Usuarios> editarUsuario(@PathVariable Long idusuario,
                                                  @RequestParam("usuario") String usuarioJson,
                                                  @RequestPart(value = "foto", required = false) MultipartFile foto) {
        try {
            // Converte o JSON recebido no corpo da requisição para um objeto 'Usuarios'
            ObjectMapper objectMapper = new ObjectMapper();
            Usuarios usuarioAtualizado = objectMapper.readValue(usuarioJson, Usuarios.class);

            // Chama o serviço para editar o usuário
            Usuarios usuarioEditado = usuarioService.editarUsuario(idusuario, usuarioAtualizado, foto);

            // Se uma foto foi enviada, salva na tabela de fotos
            if (foto != null && !foto.isEmpty()) {
                FotosDTO fotoDTO = new FotosDTO();
                fotoDTO.setCaminhoFoto(usuarioEditado.getFoto());
                fotoDTO.setIdUsuario(usuarioEditado.getIdusuario());
                fotoService.salvarFoto(fotoDTO);
            }

            // Retorna a resposta com o usuário atualizado
            return ResponseEntity.status(HttpStatus.OK).body(usuarioEditado);
        } catch (Exception e) {
            // Caso ocorra algum erro durante a edição, retorna um erro no corpo da resposta
            e.printStackTrace();
            Usuarios erro = new Usuarios();
            erro.setNome("Erro ao editar usuário");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
        }
    }

    // Endpoint para buscar usuários por nome
    @Operation(summary = "Buscar usuários por nome", description = "Retorna uma lista de usuários cujos nomes contenham a string fornecida")
    @GetMapping("/buscarPorNome")
    public ResponseEntity<List<Usuarios>> listarUsuariosPorNome(@RequestParam String nome) {
        // Chama o serviço para listar usuários com o nome fornecido
        List<Usuarios> usuarios = usuarioService.listarUsuariosPorNome(nome);

        // Se não encontrar usuários, retorna status 404 (NOT_FOUND)
        if (usuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Caso contrário, retorna a lista de usuários encontrados
        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }
}
