package com.SENA.GOAPPv2.IService;

import com.SENA.GOAPPv2.Entity.Code;
import com.SENA.GOAPPv2.Entity.User;

import java.util.Optional;

public interface AuthenticationIService {

    /**
     * Autentica al usuario utilizando un código proporcionado.
     *
     * @param userId ID del usuario.
     * @param inputCode Código ingresado por el usuario.
     * @return true si la autenticación es exitosa, false en caso contrario.
     */
    boolean authenticate(Long userId, String inputCode);

    /**
     * Asigna un código diario a un usuario.
     *
     * @param userId ID del usuario.
     * @return El código generado y asignado.
     */
    Code assignDailyCode(Long userId);

    /**
     * Obtiene un usuario por su ID.
     *
     * @param userId ID del usuario.
     * @return Usuario si existe, vacío en caso contrario.
     */
    Optional<User> getUserById(Long userId);

    void getElapsedTime( );

    long getElapsedTime(Long userId);

    void endSession(Long userId);
}
