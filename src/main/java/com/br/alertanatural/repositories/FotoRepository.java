package com.br.alertanatural.repositories;

import com.br.alertanatural.models.Fotos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FotoRepository extends JpaRepository<Fotos,Long> {
}
