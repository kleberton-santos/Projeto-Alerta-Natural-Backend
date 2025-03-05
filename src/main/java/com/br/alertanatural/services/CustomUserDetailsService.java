package com.br.alertanatural.services;

import com.br.alertanatural.config.CustomUserDetails;
import com.br.alertanatural.models.Usuarios;
import com.br.alertanatural.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Implementa o método loadUserByUsername para carregar as informações do usuário com base no email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca o usuário pelo email no banco de dados
        Usuarios usuario = usuarioRepository.findByEmail(email) // Realiza a consulta no banco de dados pelo email
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email)); // Lança exceção caso o usuário não seja encontrado

        // Retorna uma instância de CustomUserDetails com o email do usuário
        return new CustomUserDetails(usuario.getEmail()); // Cria e retorna o objeto CustomUserDetails, que contém as informações do usuário
    }
}
