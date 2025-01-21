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
        Optional<User> userOptional = userService.findById(id);
        return userOptional.map(ResponseEntity::ok)
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
        Optional<User> userOptional = userService.findById(id);

        if (userOptional.isPresent()) {
            user.setId(id); // Se asegura de que el ID sea el correcto
            User updatedUser = userService.save(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Eliminar un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @return Mensaje indicando el resultado de la operación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);

        if (userOptional.isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
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
        Optional<User> userOptional = userService.findByUsername(username);
        return userOptional.map(ResponseEntity::ok)
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
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }
}
