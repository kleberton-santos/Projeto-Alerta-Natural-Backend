package com.br.alertanatural.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private String email;  // Armazena o e-mail do usuário

    public CustomUserDetails(String email) {
        this.email = email;  // Construtor que inicializa o e-mail do usuário
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna as autoridades do usuário (não implementadas neste caso)
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return null;  // Retorna null, pois a autenticação é feita com token e não com senha
    }

    @Override
    public String getUsername() {
        return email;  // O nome de usuário será o e-mail do usuário
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Define que a conta do usuário nunca expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Define que a conta do usuário não está bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Define que as credenciais do usuário nunca expiram
    }

    @Override
    public boolean isEnabled() {
        return true;  // Define que o usuário está habilitado
    }
}
