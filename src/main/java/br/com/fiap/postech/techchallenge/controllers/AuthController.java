package br.com.fiap.postech.techchallenge.controllers;

import br.com.fiap.postech.techchallenge.dtos.AuthValidarRequestDTO;
import br.com.fiap.postech.techchallenge.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/validar")
    public ResponseEntity<Void> validar(@RequestBody AuthValidarRequestDTO request) {
        boolean ok = authService.validarLogin(request.login(), request.senha());
        return ok ? ResponseEntity.ok().build()
                : ResponseEntity.status(401).build();
    }
}
