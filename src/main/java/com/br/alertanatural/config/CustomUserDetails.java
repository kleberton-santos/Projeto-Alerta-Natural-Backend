package com.br.alertanatural.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private String email;

    public CustomUserDetails(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorne as autoridades do usuário aqui
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return null;  // A senha pode ser nula, pois estamos autenticando com token
    }

    @Override
    public String getUsername() {
        return email;  // Usamos o e-mail como nome de usuário
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
