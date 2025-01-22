package com.SENA.GOAPPv2.Service;

import com.SENA.GOAPPv2.Entity.Code;
import com.SENA.GOAPPv2.Entity.User;
import com.SENA.GOAPPv2.IService.AuthenticationIService;
import com.SENA.GOAPPv2.Repository.CodeRepository;
import com.SENA.GOAPPv2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthenticationService implements AuthenticationIService {

    @Autowired
    private UserRepository userRepository; // Repositorio para acceder a los datos de los usuarios

    @Autowired
    private CodeRepository codeRepository; // Repositorio para acceder al código diario

    /**
     * Método que autentica a un usuario usando su ID y un código diario.
     *
     * @param userId    ID del usuario que intenta autenticarse.
     * @param inputCode Código ingresado por el usuario.
     * @return true si la autenticación es exitosa, false en caso contrario.
     */
    @Override
    public boolean authenticate(Long userId, String inputCode) {
        // Buscar al usuario en la base de datos por su ID
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            System.out.println("Usuario no encontrado con ID: " + userId);
            return false; // Usuario no existe
        }

        User user = optionalUser.get();

        // Buscar el código diario generado para la fecha actual
        Optional<Code> dailyCodeOptional = codeRepository.findByGenerationDate(LocalDate.now());
        if (dailyCodeOptional.isEmpty()) {
            System.out.println("No hay código diario generado para hoy.");
            return false; // No se encontró un código para el día actual
        }

        Code dailyCode = dailyCodeOptional.get();

        // Verificar si el código ingresado coincide con el código diario
        if (!dailyCode.getCode().equals(inputCode)) {
            System.out.println("Código incorrecto.");
            return false; // El código ingresado no coincide
        }

        // Activar al usuario y registrar su hora de inicio de jornada
        user.setActive(true); // Cambiar el estado del usuario a activo
        user.setStartTime(LocalDateTime.now()); // Registrar la hora actual como hora de inicio
        userRepository.save(user); // Guardar los cambios en la base de datos

        System.out.println("Autenticación exitosa para el usuario con ID: " + userId);
        return true; // Autenticación exitosa
    }

    /**
     * Método no implementado que asigna un código diario a un usuario específico.
     */
    @Override
    public Code assignDailyCode(Long userId) {
        return null; // Este método no se utiliza en esta implementación
    }

    /**
     * Método no implementado que busca un usuario por su ID.
     */
    @Override
    public Optional<User> getUserById(Long userId) {
        return Optional.empty(); // Este método no se utiliza en esta implementación
    }
}
