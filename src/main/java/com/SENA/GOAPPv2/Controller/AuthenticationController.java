package com.SENA.GOAPPv2.Controller;

import com.SENA.GOAPPv2.IService.AuthenticationIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationIService authenticationService;

    /**
     * Endpoint para autenticar a un usuario utilizando un código.
     *
     * @param loginRequest Objeto con los datos de inicio de sesión.
     * @return Respuesta HTTP indicando el resultado de la autenticación.
     */
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Long userId = loginRequest.getUserId();
        String code = loginRequest.getCode();

        // Intenta autenticar al usuario
        boolean isAuthenticated = authenticationService.authenticate(userId, code);

        if (isAuthenticated) {
            return ResponseEntity.ok("Usuario autenticado. Jornada iniciada.");
        } else {
            return ResponseEntity.badRequest().body("Autenticación fallida. Verifica el código o el usuario.");
        }
    }

    // Clase interna para manejar el cuerpo de la solicitud
    public static class LoginRequest {
        private Long userId;
        private String code;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
