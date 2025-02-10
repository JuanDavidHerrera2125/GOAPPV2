package com.SENA.GOAPPv2.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
    @JsonIgnoreProperties({"id", "password", "email"}) // Ignorar estos campos
    private User administrador;

    @ManyToOne
    @JoinColumn(name = "agente_id", nullable = false)
    @JsonIgnoreProperties({"id", "password", "email"}) // Solo devuelve nombre y username
    private User agente;

    @ManyToOne
    @JoinColumn(name = "tienda_id", nullable = false)
    @JsonIgnoreProperties({"id", "password", "email"})
    private User tienda;

    private LocalDateTime assignedAt;

    public Tasks(User administrador, User agente, User tienda) {
    }

    // ✅ Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getAdministrador() { return administrador; }
    public void setAdministrador(User administrador) { this.administrador = administrador; }

    public User getAgente() { return agente; }
    public void setAgente(User agente) { this.agente = agente; }

    public User getTienda() { return tienda; }
    public void setTienda(User tienda) { this.tienda = tienda; }

    public LocalDateTime getAssignedAt() { return assignedAt; }
    public void setAssignedAt(LocalDateTime assignedAt) { this.assignedAt = assignedAt; }
}
