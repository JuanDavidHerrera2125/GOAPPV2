package com.SENA.GOAPPv2.Repository;

import com.SENA.GOAPPv2.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Buscar usuario por nombre de usuario
    Optional<User> findByUsername(String username);

    // Verificar si existe un usuario con un nombre de usuario
    boolean existsByUsername(String username);

    // Buscar usuarios por rol
    List<User> findByRole(String role);
}
