package com.br.alertanatural.repositories;

import com.br.alertanatural.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuarios,Long> {

    Optional<Usuarios> findByEmail(String email);
    List<Usuarios> findByNomeContainingIgnoreCase(String nome);

}
