package com.br.alertanatural.controllers;

import com.br.alertanatural.DTOs.PublicacaoDTO;
import com.br.alertanatural.models.Publicacoes;
import com.br.alertanatural.services.PublicacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publicacoes")
public class PublicacoesController {

    @Autowired
    private PublicacoesService publicacoesService;

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

}
