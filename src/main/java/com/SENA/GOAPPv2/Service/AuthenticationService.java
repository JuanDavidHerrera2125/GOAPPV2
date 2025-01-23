package com.SENA.GOAPPv2.Service;

import com.SENA.GOAPPv2.Entity.Code;
import com.SENA.GOAPPv2.Entity.User;
import com.SENA.GOAPPv2.Entity.Workday;
import com.SENA.GOAPPv2.IService.AuthenticationIService;
import com.SENA.GOAPPv2.IService.WorkdayIService;
import com.SENA.GOAPPv2.Repository.CodeRepository;
import com.SENA.GOAPPv2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class AuthenticationService implements AuthenticationIService {

    private static final long MAX_SESSION_HOURS = 8; // Máximo de horas de sesión por día
    static final long HOURLY_RATE = 9000;    // Tarifa por hora en pesos colombianos

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private WorkdayIService workdayService; // Inyección del servicio Workday

    /**
     * Método para autenticar al usuario con el código del día.
     * Recibe el ID del usuario y el código ingresado, y valida si corresponde al código generado para el día.
     *
     * @param userId El ID del usuario.
     * @param inputCode El código ingresado por el usuario.
     * @return true si el código es válido, false si no lo es.
     */
    @Override
    public boolean authenticate(Long userId, String inputCode) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            // Si no se encuentra al usuario, se retorna false
            System.out.println("Usuario no encontrado con ID: " + userId);
            return false;
        }

        User user = optionalUser.get();

        // Obtener el código generado para el día actual
        Optional<Code> dailyCode = codeRepository.findByGenerationDate(LocalDate.now());

        if (dailyCode.isEmpty()) {
            // Si no hay un código para el día, se informa y se retorna false
            System.out.println("No se encontró un código válido para hoy.");
            return false;
        }

        // Verifica que el código ingresado sea el mismo que el generado para el día
        if (!dailyCode.get().getCode().equals(inputCode)) {
            System.out.println("Código incorrecto.");
            return false;
        }

        // Si el código es válido, el usuario puede iniciar su jornada
        if (user.isActive()) {
            // Si ya está activo, no permitimos un segundo login sin finalizar la sesión anterior.
            System.out.println("El usuario ya está autenticado.");
            return false;
        }

        user.setActive(true); // Se marca al usuario como activo
        user.setStartTime(LocalDateTime.now()); // Se registra el momento de inicio
        userRepository.save(user); // Se guarda al usuario con los cambios

        System.out.println("Autenticación exitosa.");
        return true;
    }

    @Override
    public Code assignDailyCode(Long userId) {
        // Este método podría implementarse si en algún momento quieres asignar códigos personalizados
        // a cada usuario. Actualmente no lo necesitamos, por lo que se retorna null.
        return null;
    }

    /**
     * Método para obtener un usuario por su ID.
     *
     * @param userId El ID del usuario a buscar.
     * @return Un Optional que contiene al usuario si existe, o está vacío si no se encuentra.
     */
    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void getElapsedTime() {
        // Este método está vacío, puedes usarlo si más adelante necesitas algún tipo de funcionalidad
        // adicional relacionada con el tiempo transcurrido. Se deja vacío por ahora.
    }

    /**
     * Método para calcular el tiempo transcurrido desde que un usuario inició sesión.
     * Este método devuelve el tiempo en segundos desde el inicio de la sesión del usuario.
     *
     * @param userId El ID del usuario cuyo tiempo transcurrido se quiere conocer.
     * @return El tiempo transcurrido en segundos desde que el usuario inició su sesión.
     */
    @Override
    public long getElapsedTime(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getStartTime() != null) {
                // Devuelve el tiempo transcurrido en segundos
                return ChronoUnit.SECONDS.between(user.getStartTime(), LocalDateTime.now());
            }
        }
        // Si no se encuentra al usuario o no tiene una hora de inicio registrada, se devuelve 0
        return 0;
    }

    /**
     * Método para finalizar la sesión de un usuario.
     * Esto marca al usuario como inactivo y limpia el tiempo de inicio.
     *
     * @param userId El ID del usuario cuya sesión se va a finalizar.
     */
    @Override
    public void endSession(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Marca al usuario como inactivo y elimina el tiempo de inicio
            user.setActive(false);
            user.setStartTime(null);
            userRepository.save(user); // Guarda los cambios realizados al usuario
            System.out.println("Sesión terminada para el usuario con ID: " + userId);
        }
    }

    /**
     * Método para generar un código único para el día.
     * Si no existe un código para el día actual, genera y lo guarda.
     */
    public void generateDailyCode() {
        String dailyCode = generateCodeForDay(); // Este método genera un código (puedes elegir el formato que prefieras)

        // Verifica si ya existe un código para el día
        Optional<Code> existingCode = codeRepository.findByGenerationDate(LocalDate.now());
        if (existingCode.isEmpty()) {
            Code newCode = new Code();
            newCode.setCode(dailyCode); // Asigna el código generado
            newCode.setGenerationDate(LocalDate.now()); // Asigna la fecha de generación
            codeRepository.save(newCode); // Guarda el nuevo código en la base de datos
        }
    }

    /**
     * Método privado que genera un código para el día.
     * Este código puede ser generado de la manera que prefieras (aquí se usa la fecha).
     *
     * @return El código generado para el día.
     */
    private String generateCodeForDay() {
        // Lógica para generar un código único para el día
        return "CODE-" + LocalDate.now().toString(); // Ejemplo simple: "CODE-2025-01-22"
    }

}
