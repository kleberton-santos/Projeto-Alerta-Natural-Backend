package com.br.alertanatural.controllers;

import com.br.alertanatural.DTOs.LoginDTO;
import com.br.alertanatural.models.Usuarios;
import com.br.alertanatural.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints relacionados aos usuários")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário com os dados fornecidos")
    @PostMapping
    public ResponseEntity<Usuarios> criarUsuario(@RequestBody Usuarios usuarios) {
        Usuarios usuarioCriado = usuarioService.criarUsuario(usuarios);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários")
    @GetMapping
    public ResponseEntity<List<Usuarios>> listarUsuarios() {
        List<Usuarios> listaUsuarios = usuarioService.listarUsuarios();
        return ResponseEntity.status(HttpStatus.OK).body(listaUsuarios);
    }

    @Operation(summary = "Buscar usuário por ID", description = "Retorna o usuário correspondente ao ID fornecido")
    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> listarUsuarios(@PathVariable Long id) {
        Optional<Usuarios> usuario = usuarioService.listarUsuariosPorId(id);

        if (usuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Deletar usuário", description = "Deleta o usuário correspondente ao ID fornecido")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        try {
            usuarioService.deletarUsuario(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Editar usuário", description = "Edita as informações do usuário baseado no ID fornecido")
    @PutMapping("/{id}")
    public ResponseEntity<Usuarios> editarUsuario(@PathVariable Long id, @RequestBody Usuarios usuarioAtualizado) {
        try {
            Usuarios usuarioEditado = usuarioService.editarUsuario(id, usuarioAtualizado);
            return ResponseEntity.status(HttpStatus.OK).body(usuarioEditado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}




