package com.SENA.GOAPPv2.Service;

import com.SENA.GOAPPv2.Entity.Code;
import com.SENA.GOAPPv2.IService.CodeIService;
import com.SENA.GOAPPv2.Repository.CodeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class CodeService implements CodeIService {

    @Autowired
    private CodeRepository codeRepository;

    @Override
    public Code save(Code code) {
        return codeRepository.save(code);
    }

    @Override
    public Optional<Code> findByGenerationDate(LocalDate generationDate) {
        System.out.println("Buscando código para la fecha: " + generationDate);
        return codeRepository.findByGenerationDate(generationDate);
    }

    @Override
    public Optional<Code> findByCode() {
        return findByCode(null);
    }

    @Override
    public Optional<Code> findByCode(String code) {
        return Optional.ofNullable(codeRepository.findByCode(code));
    }

    @Override
    public boolean existsByGenerationDate(LocalDate generationDate) {
        return codeRepository.existsByGenerationDate(generationDate);
    }

    @Override
    public Optional<Code> getCodeForToday() {
        LocalDate today = LocalDate.now();
        System.out.println("Buscando código para hoy: " + today);
        return findByGenerationDate(today);
    }

    @Override
    public Optional<Code> getCodeByDate(LocalDate date) {
        // Busca el código por la fecha proporcionada utilizando el repositorio
        return codeRepository.findByGenerationDate(date);
    }


    @Override
    public String generateCode(Long userId) {
        return generateCode();
    }

    @Override
    public boolean validateCode(String code) {
        return false;
    }

    // Método para generar un código único
    public String generateCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }

    // Método programado para generar el código diariamente a medianoche
    @Scheduled(cron = "0 0 0 * * ?", zone = "America/Bogota")
    public void generateDailyCode() {
        LocalDate today = LocalDate.now();
        if (!existsByGenerationDate(today)) {
            Code code = new Code();
            code.setCode(generateCode());
            code.setGenerationDate(today);
            save(code);
            System.out.println("Código generado para la fecha: " + today + " -> " + code.getCode());
        } else {
            System.out.println("El código ya existe para la fecha: " + today);
        }
    }

    // Genera el código al iniciar la aplicación
    @PostConstruct
    public void generateCodeOnStartup() {
        LocalDate today = LocalDate.now();
        if (!existsByGenerationDate(today)) {
            System.out.println("Generando código al iniciar la aplicación...");
            generateDailyCode();
        } else {
            System.out.println("El código ya existe al iniciar la aplicación: " + today);
        }
    }
}
