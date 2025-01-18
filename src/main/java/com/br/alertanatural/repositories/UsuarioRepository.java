package com.br.alertanatural.repositories;

import com.br.alertanatural.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuarios,Long> {

    Optional<Usuarios> findByEmail(String email);
}
