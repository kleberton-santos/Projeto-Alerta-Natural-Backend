package com.br.alertanatural.repositories;

import com.br.alertanatural.models.Publicacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PublicacaoRepository extends JpaRepository<Publicacoes, Long> {
    @Query("SELECT p FROM Publicacoes p LEFT JOIN FETCH p.fotos WHERE p.idPublicacao = :id")
    Optional<Publicacoes> findByIdWithFotos(@Param("id") Long id);

    List<Publicacoes> findByUsuarioIdusuario(Long idUsuario);


}
