package com.br.alertanatural.services;

import com.br.alertanatural.models.Curtida;
import com.br.alertanatural.models.Publicacoes;
import com.br.alertanatural.models.Usuarios;
import com.br.alertanatural.repositories.CurtidaRepository;
import com.br.alertanatural.repositories.PublicacaoRepository;
import com.br.alertanatural.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurtidaService {

    @Autowired
    private CurtidaRepository curtidaRepository;

    @Autowired
    private PublicacaoRepository publicacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Verifica se o usuário já curtiu a publicação
    public boolean usuarioJaCurtiu(Long idusuario, Long idPublicacao) {
        return curtidaRepository.existsByUsuarioIdusuarioAndPublicacaoIdPublicacao(idusuario, idPublicacao); // Verifica no banco se a combinação usuário-publicação já existe
    }

    // Curtir uma publicação
    public void curtirPublicacao(Long idusuario, Long idPublicacao) {
        if (usuarioJaCurtiu(idusuario, idPublicacao)) { // Verifica se o usuário já curtiu a publicação
            throw new RuntimeException("Usuário já curtiu esta publicação."); // Lança exceção caso já tenha dado curtida
        }

        Publicacoes publicacao = publicacaoRepository.findById(idPublicacao) // Busca a publicação pelo ID
                .orElseThrow(() -> new RuntimeException("Publicação não encontrada.")); // Lança exceção caso não encontre a publicação

        Usuarios usuario = usuarioRepository.findById(idusuario) // Busca o usuário pelo ID
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado.")); // Lança exceção caso não encontre o usuário

        Curtida curtida = new Curtida(); // Cria um objeto Curtida
        curtida.setPublicacao(publicacao); // Associa a publicação à curtida
        curtida.setUsuario(usuario); // Associa o usuário à curtida
        curtidaRepository.save(curtida); // Salva a curtida no banco de dados
    }

    // Descurtir uma publicação
    public void descurtirPublicacao(Long idusuario, Long idPublicacao) {
        Curtida curtida = curtidaRepository.findByUsuarioIdusuarioAndPublicacaoIdPublicacao(idusuario, idPublicacao) // Busca a curtida pelo ID do usuário e da publicação
                .orElseThrow(() -> new RuntimeException("Curtida não encontrada.")); // Lança exceção caso a curtida não seja encontrada

        curtidaRepository.delete(curtida); // Deleta a curtida do banco de dados
    }

    // Contar curtidas de uma publicação
    public int contarCurtidas(Long idPublicacao) {
        return curtidaRepository.countByPublicacaoIdPublicacao(idPublicacao); // Conta e retorna o número de curtidas de uma publicação
    }
}
