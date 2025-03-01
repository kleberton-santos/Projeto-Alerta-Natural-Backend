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
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String UPLOAD_DIR = "C:\\foto-alerta";

    static {
        File diretorio = new File(UPLOAD_DIR);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
    }

    public Usuarios criarUsuario(Usuarios usuario) {
        // Verifica se a senha foi fornecida
        if (usuario.getSenha() != null) {
            // Criptografar a senha antes de salvar no banco
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        } else {
            // Define uma senha padrão para usuários OAuth2 (opcional)
            usuario.setSenha("OAUTH2_USER"); // Você pode usar um valor fixo ou gerar um UUID
        }

        return usuarioRepository.save(usuario);
    }

    public List<Usuarios> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuarios> listarUsuariosPorId(Long id){
        return usuarioRepository.findById(id);
    }


    public void deletarUsuario(Long id){
         usuarioRepository.deleteById(id);
    }

    public Optional<Usuarios> listarUsuariosPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuarios editarUsuario(Long id, Usuarios usuarioAtualizado, MultipartFile foto) throws IOException {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNome(usuarioAtualizado.getNome());
                    usuario.setSobreNome(usuarioAtualizado.getSobreNome());
                    usuario.setCpf(usuarioAtualizado.getCpf());
                    usuario.setTelefone(usuarioAtualizado.getTelefone());
                    usuario.setEmail(usuarioAtualizado.getEmail());

                    // Verifique se a senha foi passada no corpo da requisição
                    if (usuarioAtualizado.getSenha() != null) {
                        usuario.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha())); // Recriptografar senha
                    }

                    // Salvar a foto, se fornecida
                    if (foto != null && !foto.isEmpty()) {
                        String fotoUrl = null; // Salva a foto no diretório
                        try {
                            fotoUrl = salvarFoto(foto);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        usuario.setFoto(fotoUrl); // Atualiza o caminho da foto no banco de dados
                    }

                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    private String salvarFoto(MultipartFile foto) throws IOException {
        // Geração de nome único para a foto
        String nomeArquivo = System.currentTimeMillis() + "_" + foto.getOriginalFilename();
        Path caminhoDestino = Paths.get(UPLOAD_DIR, nomeArquivo);

        // Salva o arquivo no diretório especificado
        Files.copy(foto.getInputStream(), caminhoDestino);

        // Retorna o caminho da foto salva
        return nomeArquivo;
    }

    public List<Usuarios> listarUsuariosPorNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome);
    }


}


