package com.SENA.GOAPPv2.Repository;

import com.SENA.GOAPPv2.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    // Buscar personas por nombre (primer nombre o apellido)
    List<Person> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);

    // Buscar una persona por email
    Optional<Person> findByEmail(String email);

    // Buscar personas por teléfono
    List<Person> findByPhone(String phone);

    // Buscar personas por dirección
    List<Person> findByAddressContaining(String address);

    // Buscar persona por documento
    Optional<Person> findByDocument(String document);

    // Buscar personas por fecha de nacimiento
    List<Person> findByBirthDate(Date birthDate);
}
