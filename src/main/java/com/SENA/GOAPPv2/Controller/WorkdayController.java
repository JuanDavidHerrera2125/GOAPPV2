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
public class WorkdayController {

    @Autowired
    private WorkdayIService workdayService;

    // Registrar una jornada de trabajo
    @PostMapping("/register/{userId}")
    public ResponseEntity<String> registerWorkday(@PathVariable Long userId,
                                                  @RequestParam String startTime,
                                                  @RequestParam String endTime) {
        try {
            LocalDateTime start = LocalDateTime.parse(startTime);
            LocalDateTime end = LocalDateTime.parse(endTime);
            workdayService.registerWorkday(userId, start, end);
            return ResponseEntity.ok("Jornada registrada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al registrar jornada: " + e.getMessage());
        }
    }

    // Obtener jornadas de un usuario por su ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getWorkdaysByUser(@PathVariable Long userId) {
        try {
            List<Workday> workdays = (List<Workday>) workdayService.getWorkdaysByUser(userId);
            if (workdays.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron jornadas para el usuario con ID: " + userId);
            }
            return ResponseEntity.ok(workdays);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener jornadas: " + e.getMessage());
        }
    }

    // Obtener las horas trabajadas de una jornada específica
    @GetMapping("/hours/{workdayId}")
    public ResponseEntity<?> getWorkedHours(@PathVariable Long workdayId) {
        try {
            long hoursWorked = workdayService.calculateWorkedHours(workdayId);
            return ResponseEntity.ok(hoursWorked);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al calcular horas trabajadas: " + e.getMessage());
        }
    }
}
