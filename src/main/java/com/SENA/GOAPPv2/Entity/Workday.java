package com.SENA.GOAPPv2.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity // Indica que esta clase es una entidad gestionada por JPA.
@Table(name = "work_day") // Especifica que esta entidad se almacenará en la tabla "work_day" en la base de datos.
public class Workday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera automáticamente el ID único para cada registro.
    private Long id;

    @ManyToOne // Relación de muchos a uno: muchas jornadas de trabajo pueden estar asociadas a un solo usuario.
    @JoinColumn(name = "user_id", nullable = false) // Especifica la columna de la base de datos que será la clave foránea (user_id).
    private User user;

    @Column(nullable = false) // La fecha de la jornada es obligatoria.
    private LocalDate date;

    @Column(nullable = false) // Hora de inicio de sesión, también obligatoria.
    private LocalDateTime startTime;

    private LocalDateTime endTime; // Hora de finalización de sesión (puede ser nula hasta que se registre).

    private long hoursWorked; // Campo calculado: horas trabajadas en la jornada.

    private long totalPay; // Campo calculado: total a pagar en pesos.

    // Constructor vacío para que JPA pueda instanciar la entidad.
    public Workday() {
    }

    // Constructor con parámetros iniciales.
    public Workday(User user, LocalDate date, LocalDateTime startTime, LocalDateTime endTime) {
        this.user = user;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        calculateWorkingHoursAndPayment(); // Calcula horas trabajadas y el pago al inicializar.
    }

    // Getters y setters para acceder y modificar los campos privados.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        calculateWorkingHoursAndPayment(); // Recalcula las horas trabajadas si cambia la hora de inicio.
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        calculateWorkingHoursAndPayment(); // Recalcula las horas trabajadas si cambia la hora de finalización.
    }

    public long getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(long hoursWorked) {
        this.hoursWorked = hoursWorked; // Solo se usa si necesitas ajustar manualmente las horas trabajadas.
    }

    public long getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(long totalPay) {
        this.totalPay = totalPay; // Solo se usa si necesitas ajustar manualmente el total a pagar.
    }

    // Método para calcular horas trabajadas y el total a pagar.
    private void calculateWorkingHoursAndPayment() {
        if (startTime != null && endTime != null) {
            // Calcula la diferencia en horas entre la hora de inicio y la hora de finalización.
            this.hoursWorked = ChronoUnit.HOURS.between(startTime, endTime);

            // Calcula el total a pagar multiplicando las horas trabajadas por 9000 pesos.
            this.totalPay = this.hoursWorked * 9000;
        }
    }
}
