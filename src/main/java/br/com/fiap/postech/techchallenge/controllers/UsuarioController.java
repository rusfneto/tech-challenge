package br.com.fiap.postech.techchallenge.controllers;

import br.com.fiap.postech.techchallenge.dtos.UsuarioRequestDTO;
import br.com.fiap.postech.techchallenge.entities.Usuario;
import br.com.fiap.postech.techchallenge.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(@RequestParam("pagina") int pagina, @RequestParam("tamanho") int tamanho, @RequestParam("tipo") int tipo) {
        var usuarios = usuarioService.listarUsuarios(pagina, tamanho, tipo);

        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<Void> salvarUsuario(@RequestBody UsuarioRequestDTO usuario) {
        usuarioService.salvarUsuario(usuario);
        return ResponseEntity.status(201).build();
    }

}
