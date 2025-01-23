package com.SENA.GOAPPv2.Service;

import com.SENA.GOAPPv2.Entity.User;
import com.SENA.GOAPPv2.Entity.Workday;
import com.SENA.GOAPPv2.IService.WorkdayIService;
import com.SENA.GOAPPv2.Repository.WorkdayRepository;
import com.SENA.GOAPPv2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static com.SENA.GOAPPv2.Service.AuthenticationService.HOURLY_RATE;

/**
 * Servicio para manejar la lógica de negocio relacionada con las jornadas de trabajo.
 */
@Service
public class WorkdayService implements WorkdayIService {

    @Autowired
    private WorkdayRepository workdayRepository; // Repositorio para gestionar las jornadas de trabajo

    @Autowired
    private UserRepository userRepository; // Repositorio para gestionar los usuarios

    /**
     * Registra una nueva jornada de trabajo para un usuario dado.
     * @param userId ID del usuario
     * @param startTime Hora de inicio de la jornada
     * @param endTime Hora de finalización de la jornada
     */
    @Override
    public void registerWorkday(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Crear y guardar la jornada de trabajo
            Workday workday = new Workday(user, startTime.toLocalDate(), startTime, endTime);
            workdayRepository.save(workday);
            System.out.println("Jornada registrada exitosamente.");
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    /**
     * Obtiene una jornada de trabajo específica por su ID.
     * @param workdayId ID de la jornada
     * @return La jornada encontrada, o null si no existe
     */
    @Override
    public Workday getWorkdayById(Long workdayId) {
        Optional<Workday> workdayOptional = workdayRepository.findById(workdayId);
        return workdayOptional.orElse(null);
    }

    /**
     * Obtiene todas las jornadas de trabajo asociadas a un usuario.
     * @param userId ID del usuario
     * @return Iterable con todas las jornadas asociadas al usuario
     */
    @Override
    public Iterable<Workday> getWorkdaysByUser(Long userId) {
        // Aquí deberías filtrar las jornadas por el usuario; esta implementación devuelve todas las jornadas.
        return workdayRepository.findAll();
    }

    /**
     * Calcula las horas trabajadas en una jornada específica.
     * @param workdayId ID de la jornada
     * @return Número de horas trabajadas
     */
    @Override
    public long calculateWorkedHours(Long workdayId) {
        Optional<Workday> workdayOptional = workdayRepository.findById(workdayId);
        if (workdayOptional.isPresent()) {
            Workday workday = workdayOptional.get();
            return workday.getHoursWorked(); // Retorna las horas trabajadas pre-calculadas
        }
        return 0;
    }

    /**
     * Crea y guarda una nueva jornada de trabajo basada en los datos del usuario.
     * @param user Usuario que registra la jornada
     * @param endTime Hora de finalización de la jornada
     */
    @Override
    public void createWorkday(User user, LocalDateTime endTime) {
        // Calcula las horas trabajadas
        long hoursWorked = ChronoUnit.HOURS.between(user.getStartTime(), endTime);

        // Calcula el total a pagar basado en una tarifa horaria definida
        long totalPay = hoursWorked * HOURLY_RATE;

        // Crear la jornada de trabajo
        Workday workday = new Workday();
        workday.setUser(user);
        workday.setDate(LocalDate.now());
        workday.setStartTime(user.getStartTime());
        workday.setEndTime(endTime);
        workday.setHoursWorked(hoursWorked);
        workday.setTotalPay(totalPay);

        // Guardar la jornada en la base de datos
        workdayRepository.save(workday);

        // Actualizar el estado del usuario para finalizar su jornada
        user.setStartTime(null); // Elimina la hora de inicio del usuario
        user.setActive(false); // Marca al usuario como inactivo
        userRepository.save(user);
    }

    @Override
    public void save(Workday workday) {

    }
}
