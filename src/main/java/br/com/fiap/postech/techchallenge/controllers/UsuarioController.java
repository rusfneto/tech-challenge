package br.com.fiap.postech.techchallenge.controllers;

import br.com.fiap.postech.techchallenge.dtos.UsuarioPatchDTO;
import br.com.fiap.postech.techchallenge.dtos.UsuarioRequestDTO;
import br.com.fiap.postech.techchallenge.dtos.UsuarioAtualizarSenhaRequestDTO;
import br.com.fiap.postech.techchallenge.entities.Usuario;
import br.com.fiap.postech.techchallenge.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarOuBuscar(
            @RequestParam(name = "nome", required = false) String nome
    )  {
        if (nome != null) {
            String n = nome.trim();
            if (n.isEmpty()) {
                throw new IllegalArgumentException("O parametro 'nome' nao pode ser vazio.");
            }
            if (n.length() < 3) {
                throw new IllegalArgumentException("O parametro 'nome' deve ter pelo menos 3 caracteres.");
            }
            return ResponseEntity.ok(usuarioService.buscaUsuario(n));
        }

        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> salvarUsuario(@RequestBody UsuarioRequestDTO usuario) {
        usuarioService.salvarUsuario(usuario);
        return ResponseEntity.status(201).body(Map.of("message", "Usuario cadastrado com sucesso"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioPatchDTO usuario){
        
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuario);
        
        return ResponseEntity.status(200).body(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> excluir(@PathVariable Long id) {
        String msg = usuarioService.excluirUsuario(id);
        return ResponseEntity.ok(Map.of("message", msg));
    }


    @PutMapping("/{id}/password")
    public ResponseEntity<Map<String, String>> atualizarSenha(
            @PathVariable Long id,
            @RequestBody UsuarioAtualizarSenhaRequestDTO reqBody
    ) {
        usuarioService.atualizarSenha(id, reqBody);
        return ResponseEntity.ok(Map.of("message", "Senha atualizada com sucesso"));
    }

}
