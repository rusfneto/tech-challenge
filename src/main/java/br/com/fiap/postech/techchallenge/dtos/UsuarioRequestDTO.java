package br.com.fiap.postech.techchallenge.dtos;

import br.com.fiap.postech.techchallenge.entities.TipoUsuario;

public record UsuarioRequestDTO(
        String nome,
        String email,
        String login,
        String senha,
        String endereco,
        Long tipoUsuario
    ) {
}