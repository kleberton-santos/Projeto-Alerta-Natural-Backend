package com.br.alertanatural.repositories;

import com.br.alertanatural.models.Curtida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurtidaRepository extends JpaRepository<Curtida, Long> {

    // Método para verificar se uma curtida já existe para um usuário e uma publicação específicos.
    boolean existsByUsuarioIdusuarioAndPublicacaoIdPublicacao(Long idusuario, Long idPublicacao);

    // Método para buscar uma curtida específica de um usuário em uma publicação específica.
    // Retorna um Optional para lidar com o caso onde a curtida não é encontrada.
    Optional<Curtida> findByUsuarioIdusuarioAndPublicacaoIdPublicacao(Long idusuario, Long idPublicacao);

    // Método para contar o número de curtidas em uma publicação específica, usando o id da publicação.
    int countByPublicacaoIdPublicacao(Long idPublicacao);

}
