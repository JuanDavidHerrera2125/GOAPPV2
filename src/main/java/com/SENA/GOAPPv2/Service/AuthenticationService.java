package com.SENA.GOAPPv2.Service;

import com.SENA.GOAPPv2.Entity.Code;
import com.SENA.GOAPPv2.Entity.User;
import com.SENA.GOAPPv2.IService.AuthenticationIService;
import com.SENA.GOAPPv2.Repository.CodeRepository;
import com.SENA.GOAPPv2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthenticationService implements AuthenticationIService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CodeRepository codeRepository;

    @Override
    public boolean authenticate(Long userId, String inputCode) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            System.out.println("Usuario no encontrado con ID: " + userId);
            return false;
        }

        User user = optionalUser.get();

        // Asignar el código diario al usuario si no tiene uno
        if (user.getCode() == null) {
            assignDailyCodeToUser(user);
        }

        // Verificar si el código es correcto
        Code dailyCode = user.getCode();
        if (dailyCode == null) {
            System.out.println("Código diario no encontrado o no asignado.");
            return false;
        }

        // Verificar el código de entrada
        if (!dailyCode.getCode().equals(inputCode)) {
            System.out.println("Código incorrecto.");
            return false;
        }

        // Verificar si el código corresponde al día actual
        if (!dailyCode.getGenerationDate().equals(LocalDate.now())) {
            System.out.println("El código no corresponde al día de hoy.");
            return false;
        }

        // Verificar si el código ya fue activado
        if (dailyCode.isActivated()) {
            System.out.println("El código ya fue activado.");
            return false;
        }

        // Código válido: activar usuario y código
        user.setActive(true);
        user.setStartTime(LocalDateTime.now());
        dailyCode.setActivated(true);

        // Guardar el estado actualizado
        userRepository.save(user);
        codeRepository.save(dailyCode);

        System.out.println("Autenticación exitosa.");
        return true;
    }

    @Override
    public Code assignDailyCode(Long userId) {
        return null;  // Este método no se está utilizando en este contexto.
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return Optional.empty();
    }

    // Método para asignar el código diario al usuario si no tiene uno
    private void assignDailyCodeToUser(User user) {
        Optional<Code> dailyCodeOptional = codeRepository.findByGenerationDate(LocalDate.now());

        // Verificar si el código está presente y asignarlo al usuario
        if (dailyCodeOptional.isPresent()) {
            Code dailyCode = dailyCodeOptional.get();
            user.setCode(dailyCode);
            userRepository.save(user);
        } else {
            System.out.println("No hay código diario generado para hoy.");
        }
    }
}
