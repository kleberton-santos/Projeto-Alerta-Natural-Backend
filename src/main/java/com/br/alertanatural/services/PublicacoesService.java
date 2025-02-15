package com.br.alertanatural.services;

import com.br.alertanatural.DTOs.PublicacaoDTO;
import com.br.alertanatural.models.Fotos;
import com.br.alertanatural.models.Publicacoes;
import com.br.alertanatural.models.Usuarios;
import com.br.alertanatural.repositories.FotoRepository;
import com.br.alertanatural.repositories.PublicacaoRepository;
import com.br.alertanatural.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PublicacoesService {

    @Value("${diretorio.fotos}") // Configura o diretório no application.properties
    private String diretorioFotos;

    @Autowired
    private PublicacaoRepository publicacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FotoRepository fotoRepository;

    // Método para expor o diretório de fotos
    public String getDiretorioFotos() {
        return diretorioFotos;
    }



    // Cria uma nova publicação
    public Publicacoes criarPublicacao(PublicacaoDTO publicacaoDTO) {
        Publicacoes publicacao = new Publicacoes();

        // Busca o usuário pelo ID e associa à publicação
        Usuarios usuario = usuarioRepository.findById(publicacaoDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        publicacao.setUsuario(usuario);

        publicacao.setTexto(publicacaoDTO.getTexto());
        publicacao.setNomeUsuario(usuario.getNome()); // Define o nome do usuário
        publicacao.setFotoUsuario(usuario.getFoto()); // Define a foto do usuário

        // Salva a publicação no banco de dados
        Publicacoes publicacaoSalva = publicacaoRepository.save(publicacao);

        // Se houver fotos ou vídeos, salva os arquivos e associa à publicação
        if (publicacaoDTO.getFotos() != null || publicacaoDTO.getVideos() != null) {
            salvarArquivos(publicacaoSalva.getIdPublicacao(), publicacaoDTO.getFotos(), publicacaoDTO.getVideos());
        }

        return publicacaoSalva;
    }

    // Salva fotos e vídeos associados a uma publicação
    public void salvarArquivos(Long publicacaoId, List<String> fotosCaminhos, List<String> videosCaminhos) {
        Publicacoes publicacao = publicacaoRepository.findById(publicacaoId)
                .orElseThrow(() -> new RuntimeException("Publicação não encontrada"));

        // Salvar fotos
        if (fotosCaminhos != null && !fotosCaminhos.isEmpty()) {
            // Limpa a coleção existente de fotos
            publicacao.getFotos().clear();

            // Adiciona as novas fotos
            for (String caminhoFoto : fotosCaminhos) {
                Fotos foto = new Fotos();
                foto.setCaminhoFoto(caminhoFoto);
                foto.setUsuario(publicacao.getUsuario()); // Associa ao usuário
                foto.setPublicacao(publicacao); // Associa à publicação
                publicacao.getFotos().add(foto); // Adiciona à coleção existente
            }
        }

        // Salvar vídeos
        if (videosCaminhos != null && !videosCaminhos.isEmpty()) {
            publicacao.setVideos(videosCaminhos);
        }

        publicacaoRepository.save(publicacao);
    }

    // Método para salvar um arquivo no diretório e retornar o caminho
    public String salvarArquivo(MultipartFile arquivo, String diretorio) {
        try {
            String nomeArquivo = UUID.randomUUID().toString() + "_" + arquivo.getOriginalFilename();
            Path caminhoCompleto = Paths.get(diretorio, nomeArquivo);
            Files.copy(arquivo.getInputStream(), caminhoCompleto, StandardCopyOption.REPLACE_EXISTING);
            return caminhoCompleto.toString();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo", e);
        }
    }

    // Lista todas as publicações
    public List<PublicacaoDTO> listarPublicacoes() {
        List<Publicacoes> publicacoes = publicacaoRepository.findAll();
        List<PublicacaoDTO> publicacoesDTO = new ArrayList<>();

        for (Publicacoes p : publicacoes) {
            PublicacaoDTO dto = new PublicacaoDTO();
            dto.setIdUsuario(p.getUsuario().getIdusuario());
            dto.setTexto(p.getTexto());
            dto.setNomeUsuario(p.getUsuario().getNome());
            dto.setFotoUsuario(p.getUsuario().getFoto());

            // Mapear as fotos corretamente
            List<String> fotosCaminhos = new ArrayList<>();
            if (p.getFotos() != null) {
                for (Fotos foto : p.getFotos()) {
                    fotosCaminhos.add(foto.getCaminhoFoto());
                }
            }
            dto.setFotos(fotosCaminhos);

            // Mapear os vídeos corretamente
            List<String> videosCaminhos = p.getVideos();
            dto.setVideos(videosCaminhos);

            publicacoesDTO.add(dto);
        }
        return publicacoesDTO;
    }

    // Busca uma publicação por ID
    public Publicacoes buscarPublicacaoPorId(Long id) {
        return publicacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicação não encontrada"));
    }


    public List<PublicacaoDTO> buscarPublicacoesPorUsuario(Long idUsuario) {
        List<Publicacoes> publicacoes = publicacaoRepository.findByUsuarioIdusuario(idUsuario);
        List<PublicacaoDTO> publicacoesDTO = new ArrayList<>();

        for (Publicacoes p : publicacoes) {
            PublicacaoDTO dto = new PublicacaoDTO();
            dto.setIdUsuario(p.getUsuario().getIdusuario());
            dto.setTexto(p.getTexto());
            dto.setNomeUsuario(p.getUsuario().getNome());
            dto.setFotoUsuario(p.getUsuario().getFoto());

            // Mapear as fotos corretamente
            List<String> fotosCaminhos = new ArrayList<>();
            if (p.getFotos() != null) {
                for (Fotos foto : p.getFotos()) {
                    fotosCaminhos.add(foto.getCaminhoFoto());
                }
            }
            dto.setFotos(fotosCaminhos);

            // Mapear os vídeos corretamente
            List<String> videosCaminhos = p.getVideos();
            dto.setVideos(videosCaminhos);

            publicacoesDTO.add(dto);
        }
        return publicacoesDTO;
    }


    // Edita uma publicação existente
    public Publicacoes editarPublicacao(Long id, PublicacaoDTO publicacaoDTO) {
        Publicacoes publicacao = publicacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicação não encontrada"));

        publicacao.setTexto(publicacaoDTO.getTexto());

        // Atualiza as fotos
        if (publicacaoDTO.getFotos() != null) {
            List<Fotos> fotosEntidades = new ArrayList<>();
            for (String caminhoFoto : publicacaoDTO.getFotos()) {
                Fotos foto = new Fotos();
                foto.setCaminhoFoto(caminhoFoto);
                foto.setUsuario(publicacao.getUsuario()); // Associa ao usuário
                foto.setPublicacao(publicacao); // Associa à publicação
                fotosEntidades.add(foto);
            }
            publicacao.setFotos(fotosEntidades);
        }

        // Atualiza os vídeos
        if (publicacaoDTO.getVideos() != null) {
            publicacao.setVideos(publicacaoDTO.getVideos());
        }

        return publicacaoRepository.save(publicacao);
    }

    // Deleta uma publicação por ID
    public void deletarPublicacao(Long id) {
        Publicacoes publicacao = publicacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicação não encontrada"));

        publicacaoRepository.delete(publicacao);
    }
}