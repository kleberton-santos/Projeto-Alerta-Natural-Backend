package com.br.alertanatural.services;

import com.br.alertanatural.DTOs.FotosDTO;
import com.br.alertanatural.models.Fotos;
import com.br.alertanatural.models.Publicacoes;
import com.br.alertanatural.models.Usuarios;
import com.br.alertanatural.repositories.FotoRepository;
import com.br.alertanatural.repositories.PublicacaoRepository;
import com.br.alertanatural.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FotoService {

    @Autowired
    private FotoRepository fotoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PublicacaoRepository publicacaoRepository;

    public FotosDTO salvarFoto(FotosDTO fotoDTO) {
        // Verifica se o usuário existe
        Usuarios usuario = usuarioRepository.findById(fotoDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário com ID " + fotoDTO.getIdUsuario() + " não encontrado"));

        // Inicializa a publicação como null
        Publicacoes publicacao = null;

        // Se houver um ID de publicação, tenta buscar no banco
        if (fotoDTO.getIdPublicacao() != null) {
            publicacao = publicacaoRepository.findById(fotoDTO.getIdPublicacao())
                    .orElseThrow(() -> new RuntimeException("Publicação com ID " + fotoDTO.getIdPublicacao() + " não encontrada"));
        }

        // Cria e configura a entidade Fotos
        Fotos foto = new Fotos();
        foto.setCaminhoFoto(fotoDTO.getCaminhoFoto());
        foto.setUsuario(usuario);
        foto.setPublicacao(publicacao);

        // Salva a foto no banco
        Fotos fotoSalva = fotoRepository.save(foto);

        // Retorna a DTO com os dados da foto salva
        return new FotosDTO(
                fotoSalva.getIdFoto(),
                fotoSalva.getCaminhoFoto(),
                fotoSalva.getDataCadastro(),
                fotoSalva.getUsuario().getIdusuario(),
                fotoSalva.getPublicacao() != null ? fotoSalva.getPublicacao().getIdPublicacao() : null
        );
    }

    public List<FotosDTO> listarFotosPorUsuario(Long idusuario) {
        List<Fotos> fotos = fotoRepository.findByUsuario_Idusuario(idusuario); // Assume que você tem esse método no repositório
        return fotos.stream().map(foto -> new FotosDTO(
                foto.getIdFoto(),
                foto.getCaminhoFoto(),
                foto.getDataCadastro(),
                foto.getUsuario().getIdusuario(),
                foto.getPublicacao() != null ? foto.getPublicacao().getIdPublicacao() : null
        )).collect(Collectors.toList());
    }

    public List<FotosDTO> listarFotosPorPublicacao(Long idPublicacao) {
        List<Fotos> fotos = fotoRepository.findByPublicacao_IdPublicacao(idPublicacao); // Assume que você tem esse método no repositório
        return fotos.stream().map(foto -> new FotosDTO(
                foto.getIdFoto(),
                foto.getCaminhoFoto(),
                foto.getDataCadastro(),
                foto.getUsuario().getIdusuario(),
                foto.getPublicacao() != null ? foto.getPublicacao().getIdPublicacao() : null
        )).collect(Collectors.toList());
    }
}
