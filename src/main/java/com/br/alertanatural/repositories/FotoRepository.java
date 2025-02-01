package com.br.alertanatural.repositories;

import com.br.alertanatural.models.Fotos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotoRepository extends JpaRepository<Fotos,Long> {
    List<Fotos> findByUsuario_Idusuario(Long idusuario);
    List<Fotos> findByPublicacao_IdPublicacao(Long idPublicacao);
}
