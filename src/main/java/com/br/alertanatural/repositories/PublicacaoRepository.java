package com.br.alertanatural.repositories;

import com.br.alertanatural.models.Publicacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PublicacaoRepository extends JpaRepository<Publicacoes, Long> {

    // Método para buscar uma publicação pelo seu id, com o carregamento das fotos associadas usando JOIN FETCH.
    // Retorna um Optional para lidar com o caso onde a publicação não é encontrada.
    @Query("SELECT p FROM Publicacoes p LEFT JOIN FETCH p.fotos WHERE p.idPublicacao = :id")
    Optional<Publicacoes> findByIdWithFotos(@Param("id") Long id);

    // Método para buscar publicações de um usuário específico pelo seu id.
    List<Publicacoes> findByUsuarioIdusuario(Long idUsuario);

    // Método para buscar publicações de múltiplos usuários, cujos ids são fornecidos em uma lista.
    List<Publicacoes> findByUsuarioIdusuarioIn(List<Long> idsUsuarios);

    // Método para buscar todas as publicações, ordenadas pela data de cadastro de forma decrescente.
    List<Publicacoes> findAllByOrderByDataCadastroDesc();

}
