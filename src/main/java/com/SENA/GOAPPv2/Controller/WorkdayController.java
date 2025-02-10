package com.SENA.GOAPPv2.Controller;

import com.SENA.GOAPPv2.Entity.Workday;
import com.SENA.GOAPPv2.IService.WorkdayIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/workday")
public class

WorkdayController {

    @Autowired
    private WorkdayIService workdayService;

    /**
     * Registrar una jornada de trabajo
     *
     * @param userId    ID del usuario
     * @param startTime Hora de inicio en formato ISO (yyyy-MM-dd'T'HH:mm:ss)
     * @param endTime   Hora de fin en formato ISO (yyyy-MM-dd'T'HH:mm:ss)
     * @return Respuesta con mensaje de éxito o error
     */
    @PostMapping("/register/{userId}")
    public ResponseEntity<String> registerWorkday(@PathVariable Long userId,
                                                  @RequestParam String startTime,
                                                  @RequestParam String endTime) {
        try {
            // Parseo de fechas
            LocalDateTime start = LocalDateTime.parse(startTime);
            LocalDateTime end = LocalDateTime.parse(endTime);

            // Validar que la hora de inicio no sea después de la hora de fin
            if (start.isAfter(end)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("La hora de inicio no puede ser posterior a la hora de fin.");
            }

            // Llamar al servicio para registrar la jornada
            workdayService.registerWorkday(userId, start, end);
            return ResponseEntity.ok("Jornada registrada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al registrar jornada: " + e.getMessage());
        }
    }

    /**
     * Obtener jornadas de trabajo de un usuario
     *
     * @param userId ID del usuario
     * @return Lista de jornadas o un mensaje si no se encuentran registros
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getWorkdaysByUser(@PathVariable Long userId) {
        try {
            List<Workday> workdays = (List<Workday>) workdayService.getWorkdaysByUser(userId);

            // Retornar una lista vacía si no se encuentran jornadas
            if (workdays.isEmpty()) {
                return ResponseEntity.ok(List.of()); // Devuelve una lista vacía en lugar de 404
            }
            return ResponseEntity.ok(workdays);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener jornadas: " + e.getMessage());
        }
    }

    /**
     * Obtener las horas trabajadas de una jornada específica
     *
     * @param workdayId ID de la jornada
     * @return Número de horas trabajadas o un mensaje de error
     */
    @GetMapping("/hours/{workdayId}")
    public ResponseEntity<?> getWorkedHours(@PathVariable Long workdayId) {
        try {
            long hoursWorked = workdayService.calculateWorkedHours(workdayId);
            return ResponseEntity.ok(hoursWorked);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al calcular horas trabajadas: " + e.getMessage());
        }
    }

    /**
     * Registrar solo la hora de inicio de una jornada de trabajo
     *
     * @param userId    ID del usuario
     * @param startTime Hora de inicio en formato ISO (yyyy-MM-dd'T'HH:mm:ss)
     * @return Respuesta con mensaje de éxito o error
     */
    @PostMapping("/start/{userId}")
    public ResponseEntity<String> startWorkday(@PathVariable Long userId,
                                               @RequestParam String startTime) {
        try {
            LocalDateTime start = LocalDateTime.parse(startTime);
            workdayService.registerWorkday(userId, start, null); // Sólo registra el inicio
            return ResponseEntity.ok("Inicio de jornada registrado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al registrar inicio de jornada: " + e.getMessage());
        }
    }

    /**
     * Registrar solo la hora de fin de una jornada de trabajo
     *
     * @param workdayId ID de la jornada
     * @param endTime   Hora de fin en formato ISO (yyyy-MM-dd'T'HH:mm:ss)
     * @return Respuesta con mensaje de éxito o error
     */
    @PostMapping("/end/{workdayId}")
    public ResponseEntity<String> endWorkday(@PathVariable Long workdayId,
                                             @RequestParam String endTime) {
        try {
            LocalDateTime end = LocalDateTime.parse(endTime);
            Workday workday = workdayService.getWorkdayById(workdayId);

            if (workday == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró una jornada con ID: " + workdayId);
            }

            // Actualiza la hora de fin y guarda
            workday.setEndTime(end);
            workdayService.save(workday);
            return ResponseEntity.ok("Fin de jornada registrado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al registrar fin de jornada: " + e.getMessage());
        }
    }
}
