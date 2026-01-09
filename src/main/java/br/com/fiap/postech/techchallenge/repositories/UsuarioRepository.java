package br.com.fiap.postech.techchallenge.repositories;

import br.com.fiap.postech.techchallenge.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    // lista paginada, sem filtro de tipo
    List<Usuario> listarUsuarios(int tamanho, int offset);

    // lista por nome (like)
    List<Usuario> buscaUsuario(String nome);

    // lista paginada filtrando pelo id do tipo de usuário
    List<Usuario> listarUsuariosPorTipo(int tamanho, int offset, Long tipoUsuarioId);

    // salva um usuário
    int save(Usuario usuario);

    Optional<Usuario> atualizarUsuario(Long id, Usuario usuario);

    Optional<Usuario> findById(Long id);
}
