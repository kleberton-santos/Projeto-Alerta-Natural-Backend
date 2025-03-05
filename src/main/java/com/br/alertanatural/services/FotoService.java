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

import java.util.Date;
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

    // Método para salvar uma nova foto
    public FotosDTO salvarFoto(FotosDTO fotoDTO) {
        // Verifica se o usuário existe no banco de dados
        Usuarios usuario = usuarioRepository.findById(fotoDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário com ID " + fotoDTO.getIdUsuario() + " não encontrado"));

        // Inicializa a publicação como null
        Publicacoes publicacao = null;

        // Se houver um ID de publicação, tenta buscar no banco
        if (fotoDTO.getIdPublicacao() != null) {
            publicacao = publicacaoRepository.findById(fotoDTO.getIdPublicacao())
                    .orElseThrow(() -> new RuntimeException("Publicação com ID " + fotoDTO.getIdPublicacao() + " não encontrada"));
        }

        // Cria e configura a entidade Foto
        Fotos foto = new Fotos();
        foto.setCaminhoFoto(fotoDTO.getCaminhoFoto()); // Define o caminho da foto
        foto.setDataCadastro(new Date()); // Define a data de cadastro como a data atual
        foto.setUsuario(usuario); // Associa o usuário à foto
        foto.setPublicacao(publicacao); // Associa a publicação à foto, se existir

        // Salva a foto no banco de dados
        Fotos fotoSalva = fotoRepository.save(foto);

        // Retorna o DTO com os dados da foto salva
        return new FotosDTO(
                fotoSalva.getIdFoto(), // ID da foto
                fotoSalva.getCaminhoFoto(), // Caminho da foto
                fotoSalva.getDataCadastro(), // Data de cadastro
                fotoSalva.getUsuario().getIdusuario(), // ID do usuário
                fotoSalva.getPublicacao() != null ? fotoSalva.getPublicacao().getIdPublicacao() : null // ID da publicação, se existir
        );
    }

    // Método para listar fotos de um usuário
    public List<FotosDTO> listarFotosPorUsuario(Long idusuario) {
        List<Fotos> fotos = fotoRepository.findByUsuario_Idusuario(idusuario); // Busca fotos do usuário pelo ID
        return fotos.stream().map(foto -> new FotosDTO(
                foto.getIdFoto(), // ID da foto
                foto.getCaminhoFoto(), // Caminho da foto
                foto.getDataCadastro(), // Data de cadastro
                foto.getUsuario().getIdusuario(), // ID do usuário
                foto.getPublicacao() != null ? foto.getPublicacao().getIdPublicacao() : null // ID da publicação, se existir
        )).collect(Collectors.toList()); // Retorna uma lista de DTOs com os dados das fotos
    }

    // Método para listar fotos de uma publicação
    public List<FotosDTO> listarFotosPorPublicacao(Long idPublicacao) {
        List<Fotos> fotos = fotoRepository.findByPublicacao_IdPublicacao(idPublicacao); // Busca fotos da publicação pelo ID
        return fotos.stream().map(foto -> new FotosDTO(
                foto.getIdFoto(), // ID da foto
                foto.getCaminhoFoto(), // Caminho da foto
                foto.getDataCadastro(), // Data de cadastro
                foto.getUsuario().getIdusuario(), // ID do usuário
                foto.getPublicacao() != null ? foto.getPublicacao().getIdPublicacao() : null // ID da publicação, se existir
        )).collect(Collectors.toList()); // Retorna uma lista de DTOs com os dados das fotos
    }
}
