package com.br.alertanatural.services;

import com.br.alertanatural.DTOs.LoginDTO;
import com.br.alertanatural.models.Usuarios;
import com.br.alertanatural.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository; // Repositório para interagir com o banco de dados de usuários

    @Autowired
    private PasswordEncoder passwordEncoder; // Para criptografar as senhas dos usuários

    private static final String UPLOAD_DIR = "C:\\foto-alerta"; // Diretório para salvar fotos dos usuários

    static {
        // Criação do diretório para salvar as fotos, caso não exista
        File diretorio = new File(UPLOAD_DIR);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
    }

    // Cria um novo usuário
    public Usuarios criarUsuario(Usuarios usuario) {
        // Verifica se a senha foi fornecida
        if (usuario.getSenha() != null) {
            // Criptografa a senha antes de salvar no banco
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        } else {
            // Define uma senha padrão para usuários OAuth2 (opcional)
            usuario.setSenha("OAUTH2_USER"); // Usado para autenticação externa
        }

        // Salva o usuário no banco de dados
        return usuarioRepository.save(usuario);
    }

    // Lista todos os usuários
    public List<Usuarios> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Lista um usuário pelo ID
    public Optional<Usuarios> listarUsuariosPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Deleta um usuário pelo ID
    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Busca um usuário pelo email
    public Optional<Usuarios> listarUsuariosPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Edita os dados de um usuário, incluindo foto
    public Usuarios editarUsuario(Long id, Usuarios usuarioAtualizado, MultipartFile foto) throws IOException {
        // Verifica se o usuário existe no banco
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    // Atualiza os campos do usuário
                    usuario.setNome(usuarioAtualizado.getNome());
                    usuario.setSobreNome(usuarioAtualizado.getSobreNome());
                    usuario.setCpf(usuarioAtualizado.getCpf());
                    usuario.setTelefone(usuarioAtualizado.getTelefone());
                    usuario.setEmail(usuarioAtualizado.getEmail());

                    // Verifica se a senha foi passada e a criptografa
                    if (usuarioAtualizado.getSenha() != null) {
                        usuario.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha())); // Recriptografa a senha
                    }

                    // Se houver foto fornecida, salva-a
                    if (foto != null && !foto.isEmpty()) {
                        String fotoUrl = null;
                        try {
                            fotoUrl = salvarFoto(foto); // Chama o método para salvar a foto
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        usuario.setFoto(fotoUrl); // Atualiza o caminho da foto no banco de dados
                    }

                    // Salva as alterações no banco de dados
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado")); // Se o usuário não for encontrado, lança exceção
    }

    // Método para salvar a foto no diretório e retornar o caminho do arquivo
    private String salvarFoto(MultipartFile foto) throws IOException {
        // Gera um nome único para a foto, baseado no timestamp atual
        String nomeArquivo = System.currentTimeMillis() + "_" + foto.getOriginalFilename();
        Path caminhoDestino = Paths.get(UPLOAD_DIR, nomeArquivo);

        // Salva o arquivo no diretório especificado
        Files.copy(foto.getInputStream(), caminhoDestino, StandardCopyOption.REPLACE_EXISTING);

        // Retorna o nome do arquivo salvo (caminho relativo)
        return nomeArquivo;
    }

    // Lista os usuários cujo nome contenha o termo pesquisado
    public List<Usuarios> listarUsuariosPorNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome); // Busca por nome, ignorando maiúsculas/minúsculas
    }

    // Atualiza as informações de um usuário
    public Usuarios atualizarUsuario(Usuarios usuario) {
        return usuarioRepository.save(usuario);
    }
}
