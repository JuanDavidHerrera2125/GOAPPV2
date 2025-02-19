package com.SENA.GOAPPv2.Controller;

import com.SENA.GOAPPv2.Entity.Tasks;
import com.SENA.GOAPPv2.Service.TasksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks") // Indica que todos los endpoints de este controlador estarán bajo la ruta base "/api/tasks"
@CrossOrigin("*") // Permite solicitudes desde cualquier origen (útil para desarrollo frontend/backend separados)
public class TasksController {

    // Inyección del servicio TasksService para manejar la lógica de negocio
    private final TasksService taskService;

    // Constructor que permite que Spring inyecte automáticamente la instancia de TasksService
    public TasksController(TasksService taskService) {
        this.taskService = taskService;
    }

    /**
     * Endpoint para asignar un agente a una tienda. Este endpoint requiere los IDs
     * del administrador, del agente y de la tienda como parámetros.
     * @param administradorId ID del administrador que realiza la asignación
     * @param agenteId ID del agente a ser asignado
     * @param tiendaId ID de la tienda donde se asignará el agente
     * @return Una respuesta con el objeto de la tarea asignada o un mensaje de error
     */
    @PostMapping("/assign") // Define que este método manejará solicitudes POST en "/api/tasks/assign"
    public ResponseEntity<?> assignAgentToStore(
            @RequestParam Long administradorId, // Parámetro obligatorio en la solicitud
            @RequestParam Long agenteId, // ID del agente
            @RequestParam Long tiendaId // ID de la tienda
    ) {
        try {
            // Llama al servicio para asignar el agente a la tienda
            Tasks assignedTask = taskService.assignAgentToStore(administradorId, agenteId, tiendaId);
            // Devuelve una respuesta exitosa con la tarea asignada
            return ResponseEntity.ok(assignedTask);
        } catch (RuntimeException e) {
            // Manejo de excepciones: devuelve un mensaje de error en caso de fallo
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * Endpoint para obtener todas las tareas registradas.
     * @return Una lista de todas las tareas o un mensaje indicando que no hay tareas
     */
    @GetMapping("/all") // Maneja solicitudes GET en "/api/tasks/all"
    public ResponseEntity<?> getAllTasks() {
        try {
            // Obtiene todas las tareas a través del servicio
            List<Tasks> tasks = taskService.getAllTasks();

            // Verifica si la lista está vacía
            if (tasks.isEmpty()) {
                // Devuelve un mensaje indicando que no hay tareas registradas
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay tareas registradas.");
            }

            // Devuelve la lista de tareas con un estado 200 (OK)
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            // Maneja errores inesperados y devuelve un estado 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error al obtener las tareas: " + e.getMessage());
        }
    }

}