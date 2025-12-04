package br.com.fiap.postech.techchallenge.repositories;

import br.com.fiap.postech.techchallenge.entities.TipoUsuario;

import java.util.Optional;

public interface TipoUsuarioRepository {
    Optional<TipoUsuario> findById(Long id);
}
