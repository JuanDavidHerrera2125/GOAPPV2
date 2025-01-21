package com.SENA.GOAPPv2.IService;

import com.SENA.GOAPPv2.Entity.Code;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Interfaz de servicio para la entidad Code.
 * Proporciona métodos para la creación, validación, y búsqueda de códigos.
 */
public interface CodeIService {

    // === CRUD Y OPERACIONES BÁSICAS ===

    /**
     * Guardar un código.
     *
     * @param code Código a guardar.
     * @return Código guardado.
     */
    Code save(Code code);

    /**
     * Verificar si ya existe un código generado en una fecha específica.
     *
     * @param generationDate Fecha de generación del código.
     * @return true si existe, false en caso contrario.
     */
    boolean existsByGenerationDate(LocalDate generationDate);

    /**
     * Generar un código para un usuario específico basado en su ID.
     *
     * @param userId ID del usuario para generar el código.
     * @return Código generado.
     */
    String generateCode(Long userId);

    /**
     * Validar si un código es válido.
     *
     * @param code Código a validar.
     * @return true si el código es válido, false si no lo es.
     */
    boolean validateCode(String code);

    /**
     * Obtener el código generado para el día de hoy.
     *
     * @return Código generado hoy si existe, vacío en caso contrario.
     */
    Optional<Code> getCodeForToday();

    /**
     * Obtener un código generado en una fecha específica.
     *
     * @param date Fecha del código.
     * @return Código generado en esa fecha.
     */
    Optional<Code> getCodeByDate(LocalDate date);

    // === GENERACIÓN DE CÓDIGOS ===

    /**
     * Generar un código sin parámetros específicos.
     *
     * @return Código generado.
     */
    String generateCode();

    // === BÚSQUEDAS PERSONALIZADAS ===

    /**
     * Buscar un código.
     *
     * @return Código encontrado.
     */
    Optional<Code> findByCode();

    /**
     * Buscar un código por su valor.
     *
     * @param code Valor del código.
     * @return Código encontrado.
     */
    Optional<Code> findByCode(String code);

    /**
     * Generar un código diario.
     * Este código será generado automáticamente cada día.
     */
    void generateDailyCode();

    /**
     * Buscar un código por su fecha de generación.
     *
     * @param generationDate Fecha de generación del código.
     * @return Código generado en esa fecha.
     */
    Optional<Code> findByGenerationDate(LocalDate generationDate);
}
