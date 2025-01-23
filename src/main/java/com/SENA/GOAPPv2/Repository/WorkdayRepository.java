package com.SENA.GOAPPv2.Repository;

import com.SENA.GOAPPv2.Entity.Workday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkdayRepository extends JpaRepository<Workday, Long> {

    // Buscar jornadas por ID de usuario
    List<Workday> findByUserId(Long userId);





    //______________________________________

    //Por implementar

    //______________________________________

    //findByUserIdAndDateBetween: busca jornadas de trabajo de un usuario específico que estén dentro de un rango de fechas (startDate y endDate).
    List<Workday> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    //findByUserIdAndDate: busca jornadas de trabajo de un usuario en una fecha específica (date).

    List<Workday> findByUserIdAndDate(Long userId, LocalDate date);

    //countByUserId: devuelve el número de jornadas de trabajo registradas para un usuario específico. Esto es útil para obtener estadísticas rápidas.

    long countByUserId(Long userId);

    //findAllByOrderByDateAsc: obtiene todas las jornadas de trabajo ordenadas por fecha ascendente (de la más antigua a la más reciente).

    List<Workday> findAllByOrderByDateAsc();

    //findByHoursWorked: busca las jornadas de trabajo que tengan un número de horas trabajadas específico. Es útil si necesitas realizar consultas por el tiempo trabajado.

    List<Workday> findByHoursWorked(long hoursWorked);



}
