package com.SENA.GOAPPv2.Entity;

import jakarta.persistence.*;
import java.security.SecureRandom;
import java.time.LocalDate;

/**
 * Entidad que representa un código único generado diariamente.
 */
@Entity
@Table(name = "code") // Nombre de la tabla en la base de datos
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    private Long id;

    @Column(nullable = false, unique = true, length = 16) // Código único con longitud fija
    private String code;

    @Column(name = "generation_date", nullable = false) // Fecha de generación del código
    private LocalDate generationDate;

    private boolean activated = false; // Estado activado

    // Constructor sin argumentos. (Requerido por JPA)
    public Code() {
    }

    // Constructor con argumentos.
    public Code(String code, LocalDate generationDate) {
        this.code = code;
        this.generationDate = generationDate;
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

    public LocalDate getGenerationDate() {
        return generationDate;
    }

    public void setGenerationDate(LocalDate generationDate) {
        this.generationDate = generationDate;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    // Método estático para generar un código aleatorio con longitud configurable.
    public static String generateRandomCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }

        return code.toString();
    }

    // Método estático para generar un nuevo código diario.
    public static Code generateNewDailyCode() {
        String newCode = generateRandomCode(16); // Longitud fija de 16 caracteres
        LocalDate today = LocalDate.now();
        return new Code(newCode, today);
    }

    @Override
    public String toString() {
        return "Code{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", generationDate=" + generationDate +
                ", activated=" + activated +
                '}';
    }
}
