package com.SENA.GOAPPv2.Controller;

import com.SENA.GOAPPv2.IService.AuthenticationIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationIService authenticationService; // Servicio para manejar la lógica de autenticación

    /**
     * Endpoint para autenticar a un usuario utilizando un código.
     *
     * @param loginRequest Objeto que contiene los datos de inicio de sesión enviados por el cliente.
     * @return Respuesta HTTP indicando si la autenticación fue exitosa o no.
     */
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Obtener los datos de inicio de sesión del objeto LoginRequest
        Long userId = loginRequest.getUserId();
        String code = loginRequest.getCode();

        // Intenta autenticar al usuario
        boolean isAuthenticated = authenticationService.authenticate(userId, code);

        // Retorna una respuesta HTTP basada en el resultado de la autenticación
        if (isAuthenticated) {
            return ResponseEntity.ok("Usuario autenticado. Jornada iniciada."); // Respuesta 200 OK
        } else {
            return ResponseEntity.badRequest().body("Autenticación fallida. Verifica el código o el usuario."); // Respuesta 400 Bad Request
        }
    }

    /**
     * Clase interna para manejar el cuerpo de la solicitud de inicio de sesión.
     *
     * Este objeto contiene los campos necesarios para que el cliente envíe los datos
     * requeridos para la autenticación.
     */
    public static class LoginRequest {
        private Long userId; // ID del usuario que intenta autenticarse
        private String code; // Código proporcionado por el usuario

        // Getters y setters para userId
        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        // Getters y setters para code
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
