package com.SENA.GOAPPv2.Repository;

import com.SENA.GOAPPv2.Entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code, Long> {

    boolean existsByGenerationDate(LocalDate generationDate);

    Optional<Code> findByGenerationDate(LocalDate generationDate);

    Code findByCode(String code);


}
