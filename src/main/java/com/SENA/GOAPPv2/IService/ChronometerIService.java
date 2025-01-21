package com.SENA.GOAPPv2.IService;

import com.SENA.GOAPPv2.Entity.Chronometer;

import java.util.Optional;

public interface ChronometerIService {

    // Método para crear un nuevo cronómetro al ingresar un código
    Chronometer startChronometer(String code);

    // Método para obtener el cronómetro por código
    Optional<Chronometer> getChronometerByCode(String code);

    // Método para actualizar el tiempo transcurrido de un cronómetro específico
    void updateElapsedTime(String code);

    // Método para actualizar todos los cronómetros en ejecución
    void updateAllChronometers();
}
