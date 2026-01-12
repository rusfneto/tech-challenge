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

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(
            @RequestParam("pagina") int pagina,
            @RequestParam("tamanho") int tamanho,
            @RequestParam(name = "tipo", required = false) Long tipo) {
        var usuarios = usuarioService.listarUsuarios(pagina, tamanho, tipo);

        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<Void> salvarUsuario(@RequestBody UsuarioRequestDTO usuario) {
        usuarioService.salvarUsuario(usuario);
        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioPatchDTO usuario){
        
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuario);
        
        return ResponseEntity.status(200).body(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id){

        return usuarioService.excluirUsuario(id)
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(404).build();        
    }

    @PutMapping("/{id}/trocar-senha")
    public ResponseEntity<Void> atualizarSenha(@PathVariable Long id, @RequestBody UsuarioAtualizarSenhaRequestDTO reqBody){

        usuarioService.atualizarSenha(id, reqBody);
        return ResponseEntity.status(200).build();
                
    }

}
