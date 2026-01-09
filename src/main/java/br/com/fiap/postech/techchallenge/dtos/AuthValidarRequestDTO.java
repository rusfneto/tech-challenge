package br.com.fiap.postech.techchallenge.dtos;

public record AuthValidarRequestDTO(
        String login,
        String senha
) {}