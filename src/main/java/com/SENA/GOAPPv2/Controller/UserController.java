package com.SENA.GOAPPv2.Controller;

import com.SENA.GOAPPv2.Entity.User;
import com.SENA.GOAPPv2.IService.UserIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gestionar la entidad "User".
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserIService userService;

    // === CRUD BÁSICO ===

    /**
     * Obtener todos los usuarios.
     *
     * @return Lista de todos los usuarios.
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Obtener un usuario por su ID.
     *
     * @param id ID del usuario.
     * @return Usuario encontrado o código 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crear un nuevo usuario.
     *
     * @param user Datos del nuevo usuario.
     * @return Usuario creado.
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        User createdUser = userService.save(user);
        return ResponseEntity.status(201).body(createdUser);
    }

    /**
     * Actualizar un usuario existente.
     *
     * @param id   ID del usuario a actualizar.
     * @param user Nuevos datos del usuario.
     * @return Usuario actualizado o código 404 si no existe.
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (!userService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        user.setId(id); // Se asegura de que el ID sea el correcto
        User updatedUser = userService.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Eliminar un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @return Código de respuesta indicando el resultado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // === BÚSQUEDAS PERSONALIZADAS ===

    /**
     * Buscar un usuario por su nombre de usuario.
     *
     * @param username Nombre de usuario.
     * @return Usuario encontrado o código 404 si no existe.
     */
    @GetMapping("/search/by-username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Buscar usuarios por su rol.
     *
     * @param role Rol del usuario.
     * @return Lista de usuarios con el rol especificado.
     */
    @GetMapping("/search/by-role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        List<User> users = userService.findByRole(role);
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    /**
     * Obtener usuarios con sesiones activas.
     *
     * @return Lista de usuarios con sesión activa.
     */
    @GetMapping("/active-sessions")
    public ResponseEntity<List<User>> getActiveSessions() {
        List<User> activeUsers = userService.getActiveUsers();
        return ResponseEntity.ok(activeUsers);
    }
}
