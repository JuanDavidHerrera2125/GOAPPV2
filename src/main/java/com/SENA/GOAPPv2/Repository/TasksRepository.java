package com.SENA.GOAPPv2.Repository;

import com.SENA.GOAPPv2.Entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasksRepository extends JpaRepository<Tasks, Long> {
}