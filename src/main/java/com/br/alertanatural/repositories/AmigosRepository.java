package com.br.alertanatural.repositories;

import com.br.alertanatural.models.Amigos;
import com.br.alertanatural.models.Usuarios;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AmigosRepository extends JpaRepository<Amigos, Long> {
    List<Amigos> findByUsuarioIdusuario(Long idUsuario);
    Optional<Amigos> findById(Long idAmigo);
    boolean existsByUsuarioIdusuarioAndAmigoIdusuario(Long idUsuario, Long idAmigoUsuario);


    // Remover uma amizade
    @Modifying
    @Transactional
    @Query("DELETE FROM Amigos a WHERE a.usuario.id = :usuarioId AND a.amigo.id = :amigoId")
    void deleteByUsuarioIdAndAmigoId(@Param("usuarioId") Long usuarioId, @Param("amigoId") Long amigoId);


}
