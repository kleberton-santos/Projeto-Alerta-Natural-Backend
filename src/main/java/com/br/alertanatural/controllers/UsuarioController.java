package com.br.alertanatural.controllers;

import com.br.alertanatural.DTOs.LoginDTO;
import com.br.alertanatural.models.Usuarios;
import com.br.alertanatural.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/logins")
    public ResponseEntity<String> criarLogin(@RequestBody LoginDTO loginDTO) {
        try {
            Usuarios usuario = usuarioService.criarLogin(loginDTO);
            return ResponseEntity.ok("Login bem-sucedido! Bem-vindo, " + usuario.getNome());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/listar-logins")
    public ResponseEntity<List<LoginDTO>> listarLogins() {
        List<LoginDTO> usuariosDTO = usuarioService.listarLoging();
        return ResponseEntity.ok(usuariosDTO);
    }

    @GetMapping("/listar-logins/{id}")
    public ResponseEntity<LoginDTO> listarLoginPorId(@PathVariable Long id) {
        try {
            LoginDTO usuarioDTO = usuarioService.listarLoginPorId(id);
            return ResponseEntity.ok(usuarioDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Usuarios> criarUsuario(@RequestBody Usuarios usuarios) {
        Usuarios usuarioCriado = usuarioService.criarUsuario(usuarios);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @GetMapping
    public ResponseEntity<List<Usuarios>> listarUsuarios() {
        List<Usuarios> listaUsuarios = usuarioService.listarUsuarios();
        return ResponseEntity.status(HttpStatus.OK).body(listaUsuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> listarUsuarios(@PathVariable Long id) {
        Optional<Usuarios> usuario = usuarioService.listarUsuariosPorId(id);

        if (usuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        try {
            usuarioService.deletarUsuario(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}




