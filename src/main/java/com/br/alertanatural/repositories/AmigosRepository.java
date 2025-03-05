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

    // Método para buscar todos os registros de amizade onde o id do usuário é o especificado.
    List<Amigos> findByUsuarioIdusuario(Long idUsuario);

    // Método para buscar uma amizade específica pelo id do amigo.
    Optional<Amigos> findById(Long idAmigo);

    // Método para verificar se existe uma amizade entre dois usuários, identificados pelos seus ids.
    boolean existsByUsuarioIdusuarioAndAmigoIdusuario(Long idUsuario, Long idAmigoUsuario);

    // Método para remover uma amizade entre dois usuários, utilizando os ids dos usuários envolvidos.
    @Modifying
    @Transactional
    @Query("DELETE FROM Amigos a WHERE a.usuario.id = :usuarioId AND a.amigo.id = :amigoId")
    void deleteByUsuarioIdAndAmigoId(@Param("usuarioId") Long usuarioId, @Param("amigoId") Long amigoId);

}
