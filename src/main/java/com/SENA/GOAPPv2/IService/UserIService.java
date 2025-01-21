package com.SENA.GOAPPv2.IService;

import com.SENA.GOAPPv2.Entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la entidad User.
 * Proporciona métodos para realizar operaciones CRUD y búsquedas personalizadas.
 */
public interface UserIService {

    // === CRUD BÁSICO ===

    /**
     * Obtener todos los usuarios.
     *
     * @return Lista de todos los usuarios.
     */
    List<User> getAllUsers();

    /**
     * Obtener un usuario por su ID.
     *
     * @param id ID del usuario.
     * @return Usuario encontrado o vacío si no existe.
     */
    Optional<User> getUserById(Long id);

    /**
     * Crear un nuevo usuario.
     *
     * @param user Objeto de usuario a crear.
     * @return Usuario creado.
     */
    User createUser(User user);

    /**
     * Actualizar un usuario existente.
     *
     * @param id   ID del usuario a actualizar.
     * @param user Objeto de usuario con los datos actualizados.
     * @return Usuario actualizado.
     */
    User updateUser(Long id, User user);

    /**
     * Eliminar un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     */
    void deleteUser(Long id);

    /**
     * Guardar o actualizar un usuario.
     *
     * @param user Objeto de usuario a guardar o actualizar.
     * @return Usuario guardado o actualizado.
     */
    User save(User user);

    /**
     * Eliminar un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     */
    void deleteById(Long id);

    // === BÚSQUEDAS PERSONALIZADAS ===

    /**
     * Buscar un usuario por su ID.
     *
     * @param id ID del usuario.
     * @return Usuario encontrado o vacío si no existe.
     */
    Optional<User> findById(Long id);

    /**
     * Buscar un usuario por su nombre de usuario.
     *
     * @param username Nombre de usuario.
     * @return Usuario encontrado o vacío si no existe.
     */
    Optional<User> findByUsername(String username);

    /**
     * Verificar si un nombre de usuario ya existe.
     *
     * @param username Nombre de usuario.
     * @return true si existe, false en caso contrario.
     */
    boolean existsByUsername(String username);

    /**
     * Buscar usuarios por su rol.
     *
     * @param role Rol del usuario.
     * @return Lista de usuarios con el rol especificado.
     */
    List<User> findByRole(String role);
}
