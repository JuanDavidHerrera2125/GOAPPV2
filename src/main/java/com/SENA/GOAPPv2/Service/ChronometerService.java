package com.SENA.GOAPPv2.Service;

import com.SENA.GOAPPv2.Entity.Chronometer;
import com.SENA.GOAPPv2.Repository.ChronometerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ChronometerService {

    @Autowired
    private ChronometerRepository chronometerRepository;

    // Método para crear un nuevo cronómetro al ingresar un código
    public Chronometer startChronometer(String code) {
        Chronometer chronometer = new Chronometer(code, LocalDateTime.now());
        return chronometerRepository.save(chronometer);
    }

    // Método para obtener el cronómetro por código
    public Optional<Chronometer> getChronometerByCode(String code) {
        return chronometerRepository.findByCode(code);
    }

    // Método para actualizar el tiempo transcurrido
    public void updateElapsedTime(String code) {
        Optional<Chronometer> optionalChronometer = getChronometerByCode(code);
        if (optionalChronometer.isPresent()) {
            Chronometer chronometer = optionalChronometer.get();
            chronometer.updateElapsedTime();
            chronometerRepository.save(chronometer);  // Guardar los cambios en la BD
        }
    }

    // Método programado que actualiza todos los cronómetros en ejecución
    @Scheduled(cron = "0 * * * * ?")  // Se ejecuta cada minuto
    public void updateAllChronometers() {
        for (Chronometer chronometer : chronometerRepository.findAll()) {
            if (chronometer.isRunning()) {
                chronometer.updateElapsedTime();
                chronometerRepository.save(chronometer);
            }
        }
    }
}
