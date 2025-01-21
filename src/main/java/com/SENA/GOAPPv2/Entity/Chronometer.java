package com.SENA.GOAPPv2.Entity;

import jakarta.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "chronometer")
public class Chronometer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;  // Código ingresado

    @Column(nullable = false)
    private LocalDateTime startTime;  // Tiempo de inicio del cronómetro

    @Column(nullable = false)
    private boolean running;  // Estado del cronómetro (en ejecución o detenido)

    @Column(nullable = false)
    private long elapsedTime;  // Tiempo transcurrido en milisegundos

    // Constructor vacío
    public Chronometer() {
        this.elapsedTime = 0;
        this.running = false;
    }

    // Constructor con parámetros
    public Chronometer(String code, LocalDateTime startTime) {
        this.code = code;
        this.startTime = startTime;
        this.running = true;
        this.elapsedTime = 0;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    // Método para actualizar el tiempo transcurrido
    public void updateElapsedTime() {
        if (running) {
            Duration duration = Duration.between(startTime, LocalDateTime.now());
            this.elapsedTime = duration.toMillis();
        }
    }

    @Override
    public String toString() {
        return "Chronometer{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", startTime=" + startTime +
                ", running=" + running +
                ", elapsedTime=" + elapsedTime +
                '}';
    }
}

