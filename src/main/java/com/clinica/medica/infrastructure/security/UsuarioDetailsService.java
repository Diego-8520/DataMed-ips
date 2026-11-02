package com.clinica.medica.infrastructure.security;

import com.clinica.medica.infrastructure.adapters.output.persistence.UsuarioEntity;
import com.clinica.medica.infrastructure.adapters.output.persistence.UsuarioJpaRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {
    private final UsuarioJpaRepository repository;

    public UsuarioDetailsService(UsuarioJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioEntity usuario = repository.findByEmailAndActivoTrue(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return User.withUsername(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()))
                .disabled(!usuario.getActivo())
                .build();
    }
}
