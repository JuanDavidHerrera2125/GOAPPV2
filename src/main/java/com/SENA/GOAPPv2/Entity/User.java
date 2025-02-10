package com.SENA.GOAPPv2.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Representa la entidad "User" que contiene los datos de autenticación y rol de un usuario.
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @OneToOne
    @JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "fk_user_person", foreignKeyDefinition = "FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE"))
    @JsonManagedReference
    private Person person;


    /*
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false, unique = true)
    @JsonBackReference
    private Person person; */

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoleType role;


    @OneToOne
    @JoinColumn(name = "code_id")
    private Code code; // Usa directamente el objeto Code


    @Column(name = "is_active", nullable = false)
    private boolean isActive = false; // Estado activo/inactivo.

    @Column(name = "start_time")
    private LocalDateTime startTime; // Inicio de la jornada laboral.

    public User() {
    }

    public User(String username, String password, Person person, RoleType role) {
        this.username = username;
        this.password = password;
        this.person = person;
        this.role = role;
    }
    // === Getters y Setters ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    // === Métodos personalizados ===

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role=" + role +
                '}';
    }

    // === Enum para los roles del usuario ===

    /**
     * Enumeración que define los roles disponibles para los usuarios.
     */
    public enum RoleType {
        ADMINISTRADOR, // Usuario con permisos de administración
        AGENTE,        // Usuario con permisos de agente
        TIENDA         // Usuario con permisos asociados a tiendas
    }
}
