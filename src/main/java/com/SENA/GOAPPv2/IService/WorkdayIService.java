package com.SENA.GOAPPv2.IService;

import com.SENA.GOAPPv2.Entity.User;
import com.SENA.GOAPPv2.Entity.Workday;
import java.time.LocalDateTime;

public interface WorkdayIService {

    // Método para registrar una jornada de trabajo para un usuario
    void registerWorkday(Long userId, LocalDateTime startTime, LocalDateTime endTime);

    // Método para obtener la jornada de trabajo por ID
    Workday getWorkdayById(Long workdayId);

    // Método para obtener las jornadas de trabajo por usuario
    Iterable<Workday> getWorkdaysByUser(Long userId);

    // Método para calcular el total de horas trabajadas por el usuario en el día
    long calculateWorkedHours(Long workdayId);

    void createWorkday(User user, LocalDateTime endTime);

    void save(Workday workday);
}
