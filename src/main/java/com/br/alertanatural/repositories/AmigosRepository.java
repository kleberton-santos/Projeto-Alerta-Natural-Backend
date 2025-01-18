package com.br.alertanatural.repositories;

import com.br.alertanatural.models.Amigos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmigosRepository extends JpaRepository<Amigos, Long> {
}
