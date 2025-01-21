package com.SENA.GOAPPv2.Repository;

import com.SENA.GOAPPv2.Entity.Chronometer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ChronometerRepository extends JpaRepository<Chronometer, Long> {
    Optional<Chronometer> findByCode(String code);
}
