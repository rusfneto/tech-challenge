package br.com.fiap.postech.techchallenge.repositories;

import br.com.fiap.postech.techchallenge.entities.TipoUsuario;
import br.com.fiap.postech.techchallenge.entities.Usuario;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class UsuarioRepositoryImp implements UsuarioRepository {

    private final JdbcClient jdbcClient;

    public UsuarioRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<Usuario> listarTodos() {
        String sql = """
            SELECT u.id,
                   u.nome,
                   u.email,
                   u.login,
                   u.senha,
                   u.data_ultima_alteracao,
                   u.endereco,
                   tu.id AS tipo_id,
                   tu.tipo_usuario AS tipo_descricao
            FROM usuarios u
            LEFT JOIN tipo_usuario tu ON u.tipo_usuario_id = tu.id
            ORDER BY u.id
            """;

        return jdbcClient.sql(sql)
                .query((rs, rowNum) -> {
                    Usuario u = new Usuario();
                    u.setId(rs.getLong("id"));
                    u.setNome(rs.getString("nome"));
                    u.setEmail(rs.getString("email"));
                    u.setLogin(rs.getString("login"));
                    u.setSenha(rs.getString("senha"));
                    u.setDataUltimaAlteracao(rs.getObject("data_ultima_alteracao", java.time.LocalDateTime.class));
                    u.setEndereco(rs.getString("endereco"));

                    Long tipoId = rs.getLong("tipo_id");
                    if (!rs.wasNull()) {
                        TipoUsuario tipo = new TipoUsuario();
                        tipo.setId(tipoId);
                        tipo.setTipoUsuario(rs.getString("tipo_descricao"));
                        u.setTipoUsuario(tipo);
                    }
                    return u;
                })
                .list();
    }

    @Override
    public List<Usuario> listarUsuariosPorTipo(int tamanho, int offset, Long tipoUsuarioId) {
        String sql = """
            SELECT u.id,
                   u.nome,
                   u.email,
                   u.login,
                   u.senha,
                   u.data_ultima_alteracao,
                   u.endereco,
                   tu.id AS tipo_id,
                   tu.tipo_usuario AS tipo_descricao
            FROM usuarios u
            INNER JOIN tipo_usuario tu ON u.tipo_usuario_id = tu.id
            WHERE tu.id = :tipoUsuarioId
            LIMIT :tamanho OFFSET :offset
            """;

        return jdbcClient.sql(sql)
                .param("tamanho", tamanho)
                .param("offset", offset)
                .param("tipoUsuarioId", tipoUsuarioId)
                .query((rs, rowNum) -> {
                    Usuario u = new Usuario();
                    u.setId(rs.getLong("id"));
                    u.setNome(rs.getString("nome"));
                    u.setEmail(rs.getString("email"));
                    u.setLogin(rs.getString("login"));
                    u.setSenha(rs.getString("senha"));
                    u.setDataUltimaAlteracao(
                            rs.getObject("data_ultima_alteracao", java.time.LocalDateTime.class)
                    );
                    u.setEndereco(rs.getString("endereco"));

                    TipoUsuario tipo = new TipoUsuario();
                    tipo.setId(rs.getLong("tipo_id"));
                    tipo.setTipoUsuario(rs.getString("tipo_descricao"));
                    u.setTipoUsuario(tipo);

                    return u;
                })
                .list();
    }


    @Override
    public List<Usuario> buscaUsuario(String texto) {

        String sql = """
        SELECT u.id,
               u.nome,
               u.email,
               u.login,
               u.senha,
               u.data_ultima_alteracao,
               u.endereco,
               tu.id AS tipo_id,
               tu.tipo_usuario AS tipo_descricao
        FROM usuarios u
        LEFT JOIN tipo_usuario tu ON u.tipo_usuario_id = tu.id
        WHERE u.nome LIKE CONCAT('%', :texto, '%')
        ORDER BY u.nome
        """;

        return jdbcClient.sql(sql)
                .param("texto", texto)
                .query((rs, rowNum) -> {
                    Usuario u = new Usuario();
                    u.setId(rs.getLong("id"));
                    u.setNome(rs.getString("nome"));
                    u.setEmail(rs.getString("email"));
                    u.setLogin(rs.getString("login"));
                    u.setSenha(rs.getString("senha"));
                    u.setDataUltimaAlteracao(
                            rs.getObject("data_ultima_alteracao", java.time.LocalDateTime.class)
                    );
                    u.setEndereco(rs.getString("endereco"));

                    Long tipoId = rs.getLong("tipo_id");
                    if (!rs.wasNull()) {
                        TipoUsuario tipo = new TipoUsuario();
                        tipo.setId(tipoId);
                        tipo.setTipoUsuario(rs.getString("tipo_descricao"));
                        u.setTipoUsuario(tipo);
                    }

                    return u;
                })
                .list();
    }


    @Override
    public int save(Usuario usuario) {
        String sql = """
        INSERT INTO usuarios
            (nome, email, login, senha, data_ultima_alteracao, endereco, tipo_usuario_id)
        VALUES
            (:nome, :email, :login, :senha, :dataUltimaAlteracao, :endereco, :tipoUsuarioId)
        """;

        return jdbcClient.sql(sql)
                .param("nome", usuario.getNome())
                .param("email", usuario.getEmail())
                .param("login", usuario.getLogin())
                .param("senha", usuario.getSenha())
                .param("dataUltimaAlteracao", usuario.getDataUltimaAlteracao())
                .param("endereco", usuario.getEndereco())
                .param("tipoUsuarioId",
                        usuario.getTipoUsuario() != null ? usuario.getTipoUsuario().getId() : null)
                .update();
    }

    @Override
    public Optional<Usuario> atualizarUsuario(Long id, Usuario usuario) {

        String sql = """
                UPDATE usuarios
                SET nome = :nome,
                    email = :email,
                    login = :login,
                    data_ultima_alteracao = :dataUltimaAlteracao,
                    endereco = :endereco,
                    tipo_usuario_id= :tipoUsuarioId
                WHERE id = :id
                """;

        int rows = jdbcClient.sql(sql)
                .param("id", id)
                .param("nome", usuario.getNome())
                .param("email", usuario.getEmail())
                .param("login", usuario.getLogin())
                .param("dataUltimaAlteracao", usuario.getDataUltimaAlteracao())
                .param("endereco", usuario.getEndereco())
                .param("tipoUsuarioId", usuario.getTipoUsuario().getId())
                .update();

        if (rows == 0) {
            return Optional.empty(); // id não existe
        }

        Optional<Usuario> usuarioAtualizado = findById(id);

        return usuarioAtualizado;
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        String sql = """                
                SELECT u.id,
                   u.nome,
                   u.email,
                   u.login,
                   u.senha,
                   u.data_ultima_alteracao,
                   u.endereco,
                   tu.id AS tipo_id,
                   tu.tipo_usuario AS tipo_descricao
                FROM usuarios u
                INNER JOIN tipo_usuario tu 
                    ON u.tipo_usuario_id = tu.id
                WHERE u.id = :id;
                """;
        
        Optional<Usuario> usuarioOptional = jdbcClient.sql(sql)
        .param("id", id)
        .query((rs, rowNum) -> {
            Usuario u = new Usuario();
            u.setId(rs.getLong("id"));
            u.setNome(rs.getString("nome"));
            u.setEmail(rs.getString("email"));
            u.setLogin(rs.getString("login"));
            u.setSenha(rs.getString("senha"));
            u.setDataUltimaAlteracao(rs.getObject("data_ultima_alteracao", java.time.LocalDateTime.class));
            u.setEndereco(rs.getString("endereco"));

            TipoUsuario tipo = new TipoUsuario();
            tipo.setId(rs.getLong("tipo_id"));
            tipo.setTipoUsuario(rs.getString("tipo_descricao"));
            u.setTipoUsuario(tipo);

            return u;
        }).optional();    
        
        return usuarioOptional;
    }

    @Override
    public int remove(Long id){
        String sql = "DELETE FROM usuarios u WHERE u.id = :id";
        
        return jdbcClient.sql(sql)
        .param("id", id)
        .update();
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

    @Override
    public int atualizarSenha(Long id, String novaSenha) {
        String sql = """
        UPDATE usuarios
        SET senha = :senha,
            data_ultima_alteracao = :dataUltimaAlteracao
        WHERE id = :id
        """;

        return jdbcClient.sql(sql)
                .param("id", id)
                .param("senha", novaSenha)
                .param("dataUltimaAlteracao", java.time.LocalDateTime.now())
                .update();
    }


}
