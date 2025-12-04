package br.com.fiap.postech.techchallenge.repositories;

import br.com.fiap.postech.techchallenge.entities.TipoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TipoUsuarioImp implements TipoUsuarioRepository{

    @Autowired
    private JdbcClient jdbcClient;

    @Override
    public Optional<TipoUsuario> findById(Long id) {
        return jdbcClient
                .sql("SELECT * FROM tipo_usuario WHERE id = :id")
                .param("id", id)
                .query(TipoUsuario.class)
                .optional();
    }
}
