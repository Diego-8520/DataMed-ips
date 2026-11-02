package com.clinica.medica.infrastructure.adapters.output.persistence;

import com.clinica.medica.domain.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role rol;
    @Column(nullable = false)
    private Boolean activo = true;

    protected UsuarioEntity() {}

    public UsuarioEntity(Long id, String email, String password, Role rol, Boolean activo) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.activo = activo;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Role getRol() { return rol; }
    public Boolean getActivo() { return activo; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRol(Role rol) { this.rol = rol; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
