package com.SENA.GOAPPv2.Controller;

import com.SENA.GOAPPv2.Entity.Tasks;
import com.SENA.GOAPPv2.Service.TasksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar tareas en la aplicación.
 * Este controlador maneja operaciones CRUD sobre tareas, así como la asignación de agentes y tiendas.
 */
@RestController
@RequestMapping("/api/tasks") // Prefijo para todos los endpoints de este controlador
@CrossOrigin("*") // Permite solicitudes desde cualquier origen (importante para frontend separado)
public class TasksController {

    // Servicio para manejar la lógica de negocio relacionada con tareas
    private final TasksService taskService;

    /**
     * Constructor con inyección de dependencia del servicio de tareas.
     * @param taskService Servicio que maneja la lógica de tareas.
     */
    public TasksController(TasksService taskService) {
        this.taskService = taskService;
    }

    /**
     * Asigna un agente a una tienda a través del administrador.
     * @param administradorId ID del administrador que realiza la asignación
     * @param agenteId ID del agente a ser asignado
     * @param tiendaId ID de la tienda donde se asignará el agente
     * @return La tarea asignada o un mensaje de error en caso de fallo
     */
    @PostMapping("/assign")
    public ResponseEntity<?> assignAgentToStore(
            @RequestParam Long administradorId,
            @RequestParam Long agenteId,
            @RequestParam Long tiendaId
    ) {
        try {
            Tasks assignedTask = taskService.assignAgentToStore(administradorId, agenteId, tiendaId);
            return ResponseEntity.ok(assignedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * Obtiene todas las tareas registradas en la base de datos.
     * @return Lista de tareas o un mensaje si no hay tareas registradas.
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllTasks() {
        try {
            List<Tasks> tasks = taskService.getAllTasks();
            if (tasks.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay tareas registradas.");
            }
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * Obtiene una tarea específica por su ID.
     * @param id ID de la tarea a buscar.
     * @return La tarea encontrada o un mensaje de error si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        try {
            Tasks task = taskService.getTaskById(id);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    /**
     * Actualiza los datos de una tarea existente en la base de datos.
     * @param id ID de la tarea a actualizar.
     * @param updatedTask Objeto con los nuevos datos de la tarea.
     * @return La tarea actualizada o un mensaje de error si no se encuentra.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Tasks updatedTask) {
        try {
            Tasks task = taskService.updateTask(id, updatedTask);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    /**
     * Elimina una tarea de la base de datos por su ID.
     * @param id ID de la tarea a eliminar.
     * @return Mensaje de confirmación o error si la tarea no existe.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.ok("Tarea eliminada con éxito.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    /**
     * Asigna un agente y una tienda a una tarea ya existente.
     * @param taskId ID de la tarea a actualizar.
     * @param administradorId ID del administrador que realiza la asignación.
     * @param agenteId ID del agente asignado.
     * @param tiendaId ID de la tienda asignada.
     * @return La tarea actualizada o un mensaje de error si no se encuentra.
     */
    @PostMapping("/assign-to-task")
    public ResponseEntity<?> assignAgentAndStoreToTask(
            @RequestParam Long taskId,
            @RequestParam Long administradorId,
            @RequestParam Long agenteId,
            @RequestParam Long tiendaId) {
        try {
            Tasks existingTask = taskService.getTaskById(taskId);
            Tasks updatedTask = taskService.asignarAgenteATienda(existingTask, administradorId, agenteId, tiendaId);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}

@GetMapping("/assigned-agents")
public ResponseEntity<List<Tasks>> getAssignedAgents() {
    List<Tasks> assignedAgents = tasksService.

    if (assignedAgents.isEmpty()) {
        return ResponseEntity.noContent().build(); // 204 si no hay agentes asignados
    }

    return ResponseEntity.ok(assignedAgents);
}



}
