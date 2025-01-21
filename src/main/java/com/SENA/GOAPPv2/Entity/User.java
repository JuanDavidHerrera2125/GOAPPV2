package com.SENA.GOAPPv2.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

/**
 * Representa la entidad "User" que contiene los datos de autenticación y rol de un usuario.
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de usuario único para el inicio de sesión.
     */
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /**
     * Contraseña del usuario (debería estar encriptada).
     */
    @Column(nullable = false, length = 100)
    private String password;

    /**
     * Relación uno a uno con la entidad "Person".
     * Representa los datos personales asociados al usuario.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false, unique = true)
    @JsonBackReference  // Evita la serialización en el lado de User
    private Person person;

    /**
     * Tipo de rol del usuario (ADMINISTRADOR, AGENTE, TIENDA).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoleType role;

    // === Constructores ===

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
