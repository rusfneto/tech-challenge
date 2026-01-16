package br.com.fiap.postech.techchallenge.dtos;


public record UsuarioRequestDTO(
        String nome,
        String email,
        String login,
        String senha,
        String endereco,
        Long tipoUsuario
    ) {
}