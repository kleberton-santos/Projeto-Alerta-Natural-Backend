package com.br.alertanatural.services;

import com.br.alertanatural.DTOs.PublicacaoDTO;
import com.br.alertanatural.models.Amigos;
import com.br.alertanatural.models.Fotos;
import com.br.alertanatural.models.Publicacoes;
import com.br.alertanatural.models.Usuarios;
import com.br.alertanatural.repositories.AmigosRepository;
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
import java.util.stream.Collectors;

@Service
public class PublicacoesService {

    @Value("${diretorio.fotos}") // Configura o diretório de fotos a partir do application.properties
    private String diretorioFotos;

    @Autowired
    private PublicacaoRepository publicacaoRepository; // Repositório para acessar as publicações

    @Autowired
    private UsuarioRepository usuarioRepository; // Repositório para acessar os usuários

    @Autowired
    private FotoRepository fotoRepository; // Repositório para acessar fotos

    @Autowired
    private AmigosRepository amigosRepository; // Repositório para acessar amigos

    @Autowired
    private CurtidaService curtidaService; // Serviço para contar curtidas nas publicações

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

        publicacaoRepository.save(publicacao); // Salva a publicação com fotos e vídeos atualizados
    }

    // Método para salvar um arquivo no diretório e retornar o caminho
    public String salvarArquivo(MultipartFile arquivo, String diretorio) {
        try {
            // Gera um nome único para o arquivo utilizando UUID
            String nomeArquivo = UUID.randomUUID().toString() + "_" + arquivo.getOriginalFilename();
            Path caminhoCompleto = Paths.get(diretorio, nomeArquivo);
            Files.copy(arquivo.getInputStream(), caminhoCompleto, StandardCopyOption.REPLACE_EXISTING); // Salva o arquivo no diretório
            return caminhoCompleto.toString(); // Retorna o caminho do arquivo salvo
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo", e); // Lança uma exceção caso ocorra erro ao salvar o arquivo
        }
    }

    // Lista todas as publicações
    public List<PublicacaoDTO> listarPublicacoes() {
        List<Publicacoes> publicacoes = publicacaoRepository.findAllByOrderByDataCadastroDesc(); // Ordena por dataCadastro decrescente
        List<PublicacaoDTO> publicacoesDTO = new ArrayList<>();

        for (Publicacoes p : publicacoes) {
            // Converte cada publicação para DTO
            PublicacaoDTO dto = new PublicacaoDTO(
                    p.getIdPublicacao(),
                    p.getUsuario().getIdusuario(),
                    p.getTexto(),
                    p.getNomeUsuario(),
                    p.getFotoUsuario(),
                    p.getFotos().stream().map(Fotos::getCaminhoFoto).collect(Collectors.toList()), // Extrai os caminhos das fotos
                    p.getVideos(),
                    p.getDataCadastro(), // Adicionado dataCadastro
                    curtidaService.contarCurtidas(p.getIdPublicacao()) // Contagem de curtidas
            );
            publicacoesDTO.add(dto);
        }
        return publicacoesDTO;
    }

    // Busca uma publicação por ID
    public Publicacoes buscarPublicacaoPorId(Long id) {
        return publicacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicação não encontrada"));
    }

    // Busca publicações de um usuário específico
    public List<PublicacaoDTO> buscarPublicacoesPorUsuario(Long idUsuario) {
        List<Publicacoes> publicacoes = publicacaoRepository.findByUsuarioIdusuario(idUsuario);
        List<PublicacaoDTO> publicacoesDTO = new ArrayList<>();

        for (Publicacoes p : publicacoes) {
            PublicacaoDTO dto = new PublicacaoDTO();
            dto.setIdPublicacao(p.getIdPublicacao()); // Mapeia o ID da publicação
            dto.setIdUsuario(p.getUsuario().getIdusuario());
            dto.setTexto(p.getTexto());
            dto.setNomeUsuario(p.getUsuario().getNome());
            dto.setFotoUsuario(p.getUsuario().getFoto());

            // Mapeia as fotos corretamente
            List<String> fotosCaminhos = new ArrayList<>();
            if (p.getFotos() != null) {
                for (Fotos foto : p.getFotos()) {
                    fotosCaminhos.add(foto.getCaminhoFoto());
                }
            }
            dto.setFotos(fotosCaminhos);

            // Mapeia os vídeos corretamente
            List<String> videosCaminhos = p.getVideos();
            dto.setVideos(videosCaminhos);

            publicacoesDTO.add(dto); // Adiciona o DTO à lista
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
                fotosEntidades.add(foto); // Adiciona à lista de fotos
            }
            publicacao.setFotos(fotosEntidades);
        }

        // Atualiza os vídeos
        if (publicacaoDTO.getVideos() != null) {
            publicacao.setVideos(publicacaoDTO.getVideos());
        }

        return publicacaoRepository.save(publicacao); // Salva a publicação editada
    }

    // Deleta uma publicação por ID
    public void deletarPublicacao(Long id) {
        Publicacoes publicacao = publicacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicação não encontrada"));

        publicacaoRepository.delete(publicacao); // Deleta a publicação
    }

    // Busca publicações dos amigos de um usuário
    public List<PublicacaoDTO> buscarPublicacoesAmigos(Long idUsuario) {
        // Busca a lista de amigos do usuário
        List<Amigos> amigos = amigosRepository.findByUsuarioIdusuario(idUsuario);

        // Extrai os IDs dos amigos
        List<Long> idsAmigos = amigos.stream()
                .map(amigo -> amigo.getAmigo().getIdusuario()) // Extrai o ID do amigo
                .collect(Collectors.toList());

        // Busca as publicações dos amigos
        List<Publicacoes> publicacoes = publicacaoRepository.findByUsuarioIdusuarioIn(idsAmigos);

        // Converte as publicações para DTO
        return converterParaDTO(publicacoes);
    }

    // Converte uma lista de publicações para uma lista de DTOs
    private List<PublicacaoDTO> converterParaDTO(List<Publicacoes> publicacoes) {
        return publicacoes.stream()
                .map(publicacao -> new PublicacaoDTO(
                        publicacao.getIdPublicacao(), // idPublicacao
                        publicacao.getUsuario().getIdusuario(), // idUsuario
                        publicacao.getTexto(), // texto
                        publicacao.getNomeUsuario(), // nomeUsuario
                        publicacao.getFotoUsuario(), // fotoUsuario
                        publicacao.getFotos().stream() // fotos (extrai os caminhos das fotos)
                                .map(Fotos::getCaminhoFoto) // Extrai o caminho de cada foto
                                .collect(Collectors.toList()), // Converte para List<String>
                        publicacao.getVideos(), // vídeos
                        publicacao.getDataCadastro(), // dataCadastro
                        curtidaService.contarCurtidas(publicacao.getIdPublicacao()) // curtidas
                ))
                .collect(Collectors.toList()); // Coleta os resultados em uma lista de PublicacaoDTO
    }

    // Deleta uma publicação do usuário específico
    public void deletarPublicacaoPorUsuario(Long idUsuario, Long idPublicacao) {
        if (idUsuario == null || idPublicacao == null) {
            throw new IllegalArgumentException("ID do usuário e ID da publicação não podem ser nulos.");
        }

        // Busca a publicação pelo ID e verifica se pertence ao usuário
        Publicacoes publicacao = publicacaoRepository.findById(idPublicacao)
                .orElseThrow(() -> new RuntimeException("Publicação não encontrada."));

        if (!publicacao.getUsuario().getIdusuario().equals(idUsuario)) {
            throw new RuntimeException("Usuário não tem permissão para deletar esta publicação.");
        }

        // Deleta a publicação
        publicacaoRepository.delete(publicacao);
    }
}
