package com.SENA.GOAPPv2.Service;

import com.SENA.GOAPPv2.Entity.Code;
import com.SENA.GOAPPv2.Entity.User;
import com.SENA.GOAPPv2.Entity.Workday;
import com.SENA.GOAPPv2.IService.AuthenticationIService;
import com.SENA.GOAPPv2.IService.WorkdayIService;
import com.SENA.GOAPPv2.Repository.CodeRepository;
import com.SENA.GOAPPv2.Repository.UserRepository;
import com.SENA.GOAPPv2.Repository.WorkdayRepository;  // Importa el repositorio de Workday
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
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
    private WorkdayRepository workdayRepository;  // Inyectamos el repositorio de Workday

    @Autowired
    private WorkdayIService workdayService; // Inyección del servicio Workday

    @Override
    public boolean authenticate(Long userId, String inputCode) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            System.out.println("Usuario no encontrado con ID: " + userId);
            return false;
        }

        User user = optionalUser.get();
        Optional<Code> dailyCode = codeRepository.findByGenerationDate(LocalDate.now());

        if (dailyCode.isEmpty()) {
            System.out.println("No se encontró un código válido para hoy.");
            return false;
        }

        if (!dailyCode.get().getCode().equals(inputCode)) {
            System.out.println("Código incorrecto.");
            return false;
        }

        if (user.isActive()) {
            System.out.println("El usuario ya está autenticado.");
            return false;
        }

        user.setActive(true);
        user.setStartTime(LocalDateTime.now());
        userRepository.save(user);

        createWorkdayForUser(user);  // Crear y registrar la jornada

        System.out.println("Autenticación exitosa.");
        return true;
    }

    private void createWorkdayForUser(User user) {
        Workday workday = new Workday();
        workday.setUser(user);
        workday.setDate(LocalDate.now());
        workday.setStartTime(user.getStartTime());
        workdayRepository.save(workday);  // Guardamos la jornada en la base de datos

        System.out.println("Jornada de trabajo registrada para el usuario con ID: " + user.getId());
    }

    @Override
    public Code assignDailyCode(Long userId) {
        return null;
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void getElapsedTime() {

    }

    @Override
    public long getElapsedTime(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getStartTime() != null) {
                return ChronoUnit.SECONDS.between(user.getStartTime(), LocalDateTime.now());
            }
        }
        return 0;
    }

    @Override
    public void endSession(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getStartTime() != null) {
                LocalDateTime endTime = LocalDateTime.now();
                long hoursWorked = ChronoUnit.HOURS.between(user.getStartTime(), endTime);
                long totalPay = hoursWorked * HOURLY_RATE;

                Workday workday = new Workday();
                workday.setUser(user);
                workday.setDate(LocalDate.now());
                workday.setStartTime(user.getStartTime());
                workday.setEndTime(endTime);
                workday.setHoursWorked(hoursWorked);
                workday.setTotalPay(totalPay);

                workdayRepository.save(workday);  // Guardar la jornada

                user.setActive(false);
                user.setStartTime(null);
                userRepository.save(user);  // Guardamos el usuario actualizado

                System.out.println("Sesión terminada y jornada registrada para el usuario con ID: " + userId);
            } else {
                System.out.println("El usuario no tiene una jornada activa para finalizar.");
            }
        } else {
            System.out.println("Usuario no encontrado con ID: " + userId);
        }
    }

    public void generateDailyCode() {
        String dailyCode = generateCodeForDay();

        Optional<Code> existingCode = codeRepository.findByGenerationDate(LocalDate.now());
        if (existingCode.isEmpty()) {
            Code newCode = new Code();
            newCode.setCode(dailyCode);
            newCode.setGenerationDate(LocalDate.now());
            codeRepository.save(newCode);
        }
    }

    private String generateCodeForDay() {
        return "CODE-" + LocalDate.now().toString();
    }

    // Método programado que se ejecuta cada minuto para revisar las jornadas activas
    @Scheduled(fixedRate = 60000) // Ejecutar cada 60 segundos
    public void checkActiveSessions() {
        // Obtener todos los usuarios activos
        List<User> activeUsers = userRepository.findByIsActiveTrue();

        for (User user : activeUsers) {
            if (user.getStartTime() != null) {
                // Calcula el tiempo transcurrido desde el inicio de la sesión
                long hoursElapsed = ChronoUnit.HOURS.between(user.getStartTime(), LocalDateTime.now());

                if (hoursElapsed >= MAX_SESSION_HOURS) {
                    // Finaliza la sesión del usuario
                    endSession(user.getId());
                    System.out.println("Sesión finalizada automáticamente para el usuario con ID: " + user.getId());
                }
            }
        }
    }
}