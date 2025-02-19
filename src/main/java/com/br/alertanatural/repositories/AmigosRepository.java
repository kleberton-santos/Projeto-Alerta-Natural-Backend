package com.br.alertanatural.repositories;

import com.br.alertanatural.models.Amigos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AmigosRepository extends JpaRepository<Amigos, Long> {
    List<Amigos> findByUsuarioIdusuario(Long idUsuario);
    Optional<Amigos> findById(Long idAmigo);
}
