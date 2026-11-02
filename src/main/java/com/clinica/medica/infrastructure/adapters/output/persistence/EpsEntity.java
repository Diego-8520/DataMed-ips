package com.clinica.medica.infrastructure.adapters.output.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "eps")
public class EpsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nit;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private Boolean activo = true;

    protected EpsEntity() {}

    public EpsEntity(Long id, String nit, String nombre, Boolean activo) {
        this.id = id;
        this.nit = nit;
        this.nombre = nombre;
        this.activo = activo;
    }

    public Long getId() { return id; }
    public String getNit() { return nit; }
    public String getNombre() { return nombre; }
    public Boolean getActivo() { return activo; }
    public void setNit(String nit) { this.nit = nit; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
