package com.SENA.GOAPPv2.Controller;

import com.SENA.GOAPPv2.Entity.Tasks;
import com.SENA.GOAPPv2.Service.TasksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks") // 📌 Ruta base del controlador
@CrossOrigin("*") // Permitir peticiones desde cualquier origen (frontend)
public class TasksController {

    private final TasksService taskService;

    // ✅ Inyección de dependencias por constructor (buena práctica)
    public TasksController(TasksService taskService) {
        this.taskService = taskService;
    }

    /**
     * 📌 Asigna un agente a una tienda. Solo un administrador puede hacer esto.
     * @param administradorId ID del administrador que asigna la tarea.
     * @param agenteId ID del agente que será asignado.
     * @param tiendaId ID de la tienda donde trabajará el agente.
     * @return La tarea asignada o un mensaje de error.
     */
    @PostMapping("/assign/{administradorId}/{agenteId}/{tiendaId}")
    public ResponseEntity<?> assignAgentToStore(
            @PathVariable Long administradorId,
            @PathVariable Long agenteId,
            @PathVariable Long tiendaId) {

        try {
            Tasks assignedTask = taskService.assignAgentToStore(administradorId, agenteId, tiendaId);
            return ResponseEntity.ok(assignedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("❌ Error: " + e.getMessage());
        }
    }

    /**
     * 📌 Obtiene todas las tareas registradas en la base de datos.
     * @return Lista de tareas o un mensaje si no hay tareas.
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllTasks() {
        List<Tasks> tasks = taskService.getAllTasks();

        if (tasks.isEmpty()) {
            return ResponseEntity.ok("📌 No hay tareas registradas.");
        }
        return ResponseEntity.ok(tasks);
    }
}
