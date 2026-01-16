package br.com.fiap.postech.techchallenge.controllers;

import br.com.fiap.postech.techchallenge.dtos.AuthValidarRequestDTO;
import br.com.fiap.postech.techchallenge.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthValidarRequestDTO request) {
        authService.validarLogin(request.login(), request.senha());
        return ResponseEntity.ok(Map.of("message", "Login realizado com sucesso"));
    }

}
