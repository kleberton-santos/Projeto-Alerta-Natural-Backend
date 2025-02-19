package com.br.alertanatural.controllers;

import com.br.alertanatural.DTOs.FotosDTO;
import com.br.alertanatural.DTOs.LoginDTO;
import com.br.alertanatural.models.Usuarios;
import com.br.alertanatural.services.FotoService;
import com.br.alertanatural.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    private UsuarioService usuarioService;

    @Autowired
    private FotoService fotoService;

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
    public ResponseEntity<Usuarios> listarUsuariosId(@PathVariable Long id) {
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
    @PutMapping("/{idusuario}")
    public ResponseEntity<Usuarios> editarUsuario(@PathVariable Long idusuario,
                                                  @RequestParam("usuario") String usuarioJson,
                                                  @RequestPart(value = "foto", required = false) MultipartFile foto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Usuarios usuarioAtualizado = objectMapper.readValue(usuarioJson, Usuarios.class);

            Usuarios usuarioEditado = usuarioService.editarUsuario(idusuario, usuarioAtualizado, foto);

            // Se uma foto foi enviada, salva na tabela de fotos
            if (foto != null && !foto.isEmpty()) {
                FotosDTO fotoDTO = new FotosDTO();
                fotoDTO.setCaminhoFoto(usuarioEditado.getFoto());
                fotoDTO.setIdUsuario(usuarioEditado.getIdusuario());
                fotoService.salvarFoto(fotoDTO);
            }

            return ResponseEntity.status(HttpStatus.OK).body(usuarioEditado);
        } catch (Exception e) {
            e.printStackTrace();
            Usuarios erro = new Usuarios();
            erro.setNome("Erro ao editar usuário");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
        }
    }

    @Operation(summary = "Buscar usuários por nome", description = "Retorna uma lista de usuários cujos nomes contenham a string fornecida")
    @GetMapping("/buscarPorNome")
    public ResponseEntity<List<Usuarios>> listarUsuariosPorNome(@RequestParam String nome) {
        List<Usuarios> usuarios = usuarioService.listarUsuariosPorNome(nome);
        if (usuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }


}







