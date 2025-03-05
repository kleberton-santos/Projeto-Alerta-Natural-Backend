package com.br.alertanatural.repositories;

import com.br.alertanatural.models.Fotos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotoRepository extends JpaRepository<Fotos,Long> {

    // Método para buscar as fotos associadas a um usuário específico, utilizando o id do usuário.
    List<Fotos> findByUsuario_Idusuario(Long idusuario);

    // Método para buscar as fotos associadas a uma publicação específica, utilizando o id da publicação.
    List<Fotos> findByPublicacao_IdPublicacao(Long idPublicacao);

}
