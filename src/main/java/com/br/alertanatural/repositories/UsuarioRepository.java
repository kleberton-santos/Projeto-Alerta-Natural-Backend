package com.br.alertanatural.repositories;

import com.br.alertanatural.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuarios,Long> {
}
