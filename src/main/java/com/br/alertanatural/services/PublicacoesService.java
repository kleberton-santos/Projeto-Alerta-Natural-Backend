package com.br.alertanatural.services;

import com.br.alertanatural.DTOs.PublicacaoDTO;
import com.br.alertanatural.models.Fotos;
import com.br.alertanatural.models.Publicacoes;
import com.br.alertanatural.models.Usuarios;
import com.br.alertanatural.repositories.FotoRepository;
import com.br.alertanatural.repositories.PublicacaoRepository;
import com.br.alertanatural.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublicacoesService {

    @Autowired
    private PublicacaoRepository publicacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FotoRepository fotoRepository;

    public Publicacoes criarPublicacao(PublicacaoDTO publicacaoDTO) {
        // Buscar o usuário pelo ID
        Usuarios usuario = usuarioRepository.findById(publicacaoDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Criar a publicação e associar o usuário
        Publicacoes publicacao = new Publicacoes();
        publicacao.setTexto(publicacaoDTO.getTexto());
        publicacao.setUsuario(usuario); // Associa o usuário à publicação

        if (publicacaoDTO.getFotos() != null) {
            List<Fotos> fotos = new ArrayList<>();
            for (String caminhoFoto : publicacaoDTO.getFotos()) {
                Fotos foto = new Fotos();
                foto.setCaminhoFoto(caminhoFoto);
                foto.setUsuario(usuario); // Associa o usuário à foto

                // Adiciona a foto à lista
                fotos.add(foto);
            }
            publicacao.setFotos(fotos);  // Associa a lista de fotos à publicação
        }

        // Salvar a publicação com as fotos associadas
        return publicacaoRepository.save(publicacao);
    }

    public List<Publicacoes> listarPublicacoes() {
        return publicacaoRepository.findAll();
    }

    public Publicacoes buscarPublicacaoPorId(Long id) {
        return publicacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicação não encontrada"));
    }

    public Publicacoes editarPublicacao(Long id, PublicacaoDTO publicacaoDTO) {
        // Verifica se a publicação existe
        Publicacoes publicacao = publicacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicação não encontrada"));

        // Atualiza o texto da publicação
        publicacao.setTexto(publicacaoDTO.getTexto());

        // Atualiza as fotos se existirem
        if (publicacaoDTO.getFotos() != null) {
            List<Fotos> fotos = new ArrayList<>();
            for (String caminhoFoto : publicacaoDTO.getFotos()) {
                Fotos foto = new Fotos();
                foto.setCaminhoFoto(caminhoFoto);
                foto.setUsuario(publicacao.getUsuario());
                fotos.add(foto);
            }
            publicacao.setFotos(fotos);
        }

        // Salva a publicação editada
        return publicacaoRepository.save(publicacao);
    }

    public void deletarPublicacao(Long id) {
        Publicacoes publicacao = publicacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicação não encontrada"));

        // Deleta a publicação
        publicacaoRepository.delete(publicacao);
    }



}
