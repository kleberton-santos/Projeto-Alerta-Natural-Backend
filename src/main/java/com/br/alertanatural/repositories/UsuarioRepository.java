package com.br.alertanatural.repositories;

import com.br.alertanatural.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuarios,Long> {

    // Método para buscar um usuário por email. Retorna um Optional para lidar com o caso onde o usuário não é encontrado.
    Optional<Usuarios> findByEmail(String email);

    // Método para buscar usuários que tenham o nome contendo o termo especificado, ignorando diferença entre maiúsculas e minúsculas.
    List<Usuarios> findByNomeContainingIgnoreCase(String nome);

}
