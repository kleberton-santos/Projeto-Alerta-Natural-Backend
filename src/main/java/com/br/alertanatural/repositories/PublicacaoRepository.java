package com.br.alertanatural.repositories;

import com.br.alertanatural.models.Publicacoes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacaoRepository extends JpaRepository<Publicacoes, Long> {
}
