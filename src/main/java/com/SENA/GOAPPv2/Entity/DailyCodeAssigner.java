package com.SENA.GOAPPv2.Entity;

import com.SENA.GOAPPv2.Repository.CodeRepository;
import com.SENA.GOAPPv2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class DailyCodeAssigner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CodeRepository codeRepository;

    /**
     * Método que se ejecuta automáticamente a las 12:00 AM todos los días
     * para asignar el código diario a los usuarios.
     */
    @Scheduled(cron = "0 0 0 * * ?") // A las 12:00 AM todos los días
    public void assignCodesToAllUsers() {
        // Buscar el código diario generado para la fecha actual
        Optional<Code> dailyCodeOptional = codeRepository.findByGenerationDate(LocalDate.now());

        if (dailyCodeOptional.isEmpty()) {
            System.out.println("No se encontró código generado para hoy.");
            return;
        }

        // Obtener el código diario de la instancia Optional
        Code dailyCode = dailyCodeOptional.get();

        // Obtener todos los usuarios y asignarles el código diario
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getCode() == null) { // Asignar solo si no tienen un código asignado
                user.setCode(dailyCode);
                userRepository.save(user);
            }
        }
        System.out.println("Códigos asignados a todos los usuarios.");
    }
}
