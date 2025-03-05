package com.br.alertanatural.repositories;

import com.br.alertanatural.models.Comentario;
import com.br.alertanatural.models.Publicacoes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByPublicacao(Publicacoes publicacao);
}

