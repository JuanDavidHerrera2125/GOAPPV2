package com.SENA.GOAPPv2.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "tasks")
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
    private User administrador;

    @ManyToOne
    @JoinColumn(name = "agente_id", nullable = false)
    private User agente;

    @ManyToOne
    @JoinColumn(name = "tienda_id", nullable = false)
    private User tienda;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_asignacion", nullable = false)
    private Date fechaAsignacion;

    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt; // Campo adicional para LocalDateTime

    // Constructor sin parámetros (obligatorio para JPA)
    public Tasks() {
    }

    // Constructor con parámetros
    public Tasks(User administrador, User agente, User tienda) {
        this.administrador = administrador;
        this.agente = agente;
        this.tienda = tienda;
        this.fechaAsignacion = new Date(); // Asignar fecha actual como valor predeterminado
        this.assignedAt = LocalDateTime.now(); // Asignar fecha/hora actual como valor predeterminado
    }

    // Constructor completo
    public Tasks(User administrador, User agente, User tienda, Date fechaAsignacion, LocalDateTime assignedAt) {
        this.administrador = administrador;
        this.agente = agente;
        this.tienda = tienda;
        this.fechaAsignacion = fechaAsignacion;
        this.assignedAt = assignedAt;
    }


    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAdministrador() {
        return administrador;
    }

    public void setAdministrador(User administrador) {
        this.administrador = administrador;
    }

    public User getAgente() {
        return agente;
    }

    public void setAgente(User agente) {
        this.agente = agente;
    }

    public User getTienda() {
        return tienda;
    }

    public void setTienda(User tienda) {
        this.tienda = tienda;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
}
