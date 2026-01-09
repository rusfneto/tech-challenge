package br.com.fiap.postech.techchallenge.repositories;

import br.com.fiap.postech.techchallenge.entities.Usuario;

import java.util.List;
import java.util.Optional;


public interface UsuarioRepository {

    Integer save(Usuario usuario);

    Integer update(Usuario usuario, Long id);

    Integer delete(Long id);

    boolean validarEmail(String email);

    Integer atualizarDadosUsuario(Usuario usuario, Long id);

    List<Usuario> listarUsuarios(int tamanho, int offset);

    List<Usuario> listarUsuariosPorTipo(int tamanho, int offset, int tipo);

    Optional<Usuario> findByNome(String nome);

    boolean existeLoginESenha(String login, String senha);


}
