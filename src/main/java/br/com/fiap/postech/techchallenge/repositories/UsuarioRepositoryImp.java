package br.com.fiap.postech.techchallenge.repositories;

import br.com.fiap.postech.techchallenge.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepositoryImp implements UsuarioRepository{

    @Autowired
    private JdbcClient jdbcClient;

    @Override
    public Integer save(Usuario usuarios) {
        return jdbcClient
                .sql("INSERT INTO usuarios (nome, email, login, senha, dataUltimaAlteracao, endereco, tipoUsuario) " +
                        "VALUES (:nome, :email, :login, :senha, :dataUltimaAlteracao, :endereco, :tipoUsuario)")
                .param("nome", usuarios.getNome())
                .param("email", usuarios.getEmail())
                .param("login", usuarios.getLogin())
                .param("senha", usuarios.getSenha())
                .param("dataUltimaAlteracao", usuarios.getDataUltimaAlteracao())
                .param("endereco", usuarios.getEndereco())
                .param("tipoUsuario", usuarios.getTipoUsuario().getId())
                .update();
    }

    @Override
    public Integer update(Usuario usuario, Long id) {
        return 0;
    }

    @Override
    public Integer delete(Long id) {
        return 0;
    }

    @Override
    public boolean validarEmail(String email) {
        return false;
    }

    @Override
    public Integer atualizarDadosUsuario(Usuario usuario, Long id) {
        return 0;
    }

    @Override
    public List<Usuario> listarUsuarios(int tamanho, int offset) {
        return List.of();
    }

    @Override
    public List<Usuario> listarUsuariosPorTipo(int tamanho, int offset, int tipo) {
        return List.of();
    }

    @Override
    public Optional<Usuario> findByNome(String nome) {
        return Optional.empty();
    }

    @Override
    public boolean existeLoginESenha(String login, String senha) {
        Integer count = jdbcClient
                .sql("SELECT COUNT(1) FROM usuarios WHERE login = :login AND senha = :senha")
                .param("login", login)
                .param("senha", senha)
                .query(Integer.class)
                .single();

        return count != null && count > 0;
    }

}
