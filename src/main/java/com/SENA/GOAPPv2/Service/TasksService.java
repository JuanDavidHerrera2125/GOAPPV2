package com.SENA.GOAPPv2.Service;

import com.SENA.GOAPPv2.Entity.Tasks;
import com.SENA.GOAPPv2.Entity.User;
import com.SENA.GOAPPv2.IService.TasksIService;
import com.SENA.GOAPPv2.Repository.TasksRepository;
import com.SENA.GOAPPv2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TasksService implements TasksIService {

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Método para asignar una tarea.
     */
    @Override
    public Tasks assignTask(Tasks task) {
        // Validar que el usuario que asigna la tarea es un ADMINISTRADOR
        if (task.getAdministrador() == null || task.getAdministrador().getRole() != User.RoleType.ADMINISTRADOR) {
            throw new IllegalArgumentException("Solo un administrador puede asignar tareas.");
        }

        // Validar que el usuario asignado es un AGENTE
        if (task.getAgente() == null || task.getAgente().getRole() != User.RoleType.AGENTE) {
            throw new IllegalArgumentException("El usuario asignado debe tener el rol AGENTE.");
        }

        // Asignar la fecha actual
        task.setAssignedAt(LocalDateTime.now());

        // Guardar la tarea en la base de datos
        return tasksRepository.save(task);
    }

    /**
     * Método para obtener todas las tareas.
     */
    @Override
    public List<Tasks> getAllTasks() {
        return tasksRepository.findAll();
    }

    /**
     * Método para asignar un agente a una tienda.
     */
    @Override
    public Tasks assignAgentToStore(Tasks task, Long administradorId, Long agenteId, Long tiendaId) {
        // Buscar al administrador en la base de datos
        User administrador = userRepository.findById(administradorId)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado."));

        // Validar que el usuario es un ADMINISTRADOR
        if (administrador.getRole() != User.RoleType.ADMINISTRADOR) {
            throw new RuntimeException("Solo un administrador puede asignar un agente a una tienda.");
        }

        // Buscar al agente en la base de datos
        User agente = userRepository.findById(agenteId)
                .orElseThrow(() -> new RuntimeException("Agente no encontrado."));

        // Validar que el usuario tenga el rol de AGENTE
        if (agente.getRole() != User.RoleType.AGENTE) {
            throw new RuntimeException("El usuario asignado no es un agente.");
        }

        // Buscar la tienda en la base de datos
        User tienda = userRepository.findById(tiendaId)
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada."));

        // Validar que el usuario tenga el rol de TIENDA
        if (tienda.getRole() != User.RoleType.TIENDA) {
            throw new RuntimeException("El usuario asignado no es una tienda válida.");
        }

        // Asignar los valores a la tarea
        task.setAdministrador(administrador);
        task.setAgente(agente);
        task.setTienda(tienda);
        task.setAssignedAt(LocalDateTime.now());

        // Guardar la tarea en la base de datos
        return tasksRepository.save(task);
    }

    /**
     * Método para crear y asignar una tarea a una tienda sin recibir un objeto Tasks.
     */
    @Override
    public Tasks assignAgentToStore(Long administradorId, Long agenteId, Long tiendaId) {
        // Buscar al administrador
        User administrador = userRepository.findById(administradorId)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado."));

        // Validar que es un administrador
        if (administrador.getRole() != User.RoleType.ADMINISTRADOR) {
            throw new RuntimeException("Solo un administrador puede asignar un agente a una tienda.");
        }

        // Buscar al agente
        User agente = userRepository.findById(agenteId)
                .orElseThrow(() -> new RuntimeException("Agente no encontrado."));

        // Validar que es un agente
        if (agente.getRole() != User.RoleType.AGENTE) {
            throw new RuntimeException("El usuario asignado no es un agente.");
        }

        // Buscar la tienda
        User tienda = userRepository.findById(tiendaId)
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada."));

        // Validar que es una tienda
        if (tienda.getRole() != User.RoleType.TIENDA) {
            throw new RuntimeException("El usuario asignado no es una tienda válida.");
        }

        // Crear una nueva tarea
        Tasks task = new Tasks(administrador, agente, tienda);
        task.setAssignedAt(LocalDateTime.now());

        // Guardar la tarea en la base de datos
        return tasksRepository.save(task);
    }
}
