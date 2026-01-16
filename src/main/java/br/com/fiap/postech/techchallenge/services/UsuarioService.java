package br.com.fiap.postech.techchallenge.services;


import br.com.fiap.postech.techchallenge.dtos.UsuarioAtualizarSenhaRequestDTO;
import br.com.fiap.postech.techchallenge.dtos.UsuarioPatchDTO;
import br.com.fiap.postech.techchallenge.dtos.UsuarioRequestDTO;
import br.com.fiap.postech.techchallenge.entities.TipoUsuario;
import br.com.fiap.postech.techchallenge.entities.Usuario;
import br.com.fiap.postech.techchallenge.repositories.TipoUsuarioRepository;
import br.com.fiap.postech.techchallenge.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import br.com.fiap.postech.techchallenge.exceptions.ResourceNotFoundException;
import org.springframework.util.StringUtils;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.listarTodos();
    }

    public List<Usuario> buscaUsuario(String nome) {
        return usuarioRepository.buscaUsuario(nome);
    }




    public String salvarUsuario(UsuarioRequestDTO usuarioDto) {

        if (usuarioDto.tipoUsuario() == null) {
            throw new IllegalArgumentException("O campo tipoUsuario é obrigatório.");
        }

        tipoUsuarioRepository.findById(usuarioDto.tipoUsuario())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tipo de usuario nao encontrado: id=" + usuarioDto.tipoUsuario()
                ));

        var usuarioEntity = converteDTO(usuarioDto);
        int rows = usuarioRepository.save(usuarioEntity);

        if (rows != 1) {
            throw new IllegalStateException("Erro ao salvar usuario");
        }

        return "Usuario cadastrado com sucesso";
    }


    private Usuario converteDTO(UsuarioRequestDTO usuarioRequestDTO){
        var tipoUsuario = tipoUsuarioRepository.findById(usuarioRequestDTO.tipoUsuario())
                .orElseThrow(() -> new RuntimeException("Tipo de Usuário não encontrado."));

        return new Usuario(usuarioRequestDTO, tipoUsuario);
    }


    public Usuario atualizarUsuario(Long id, UsuarioPatchDTO usuarioPatchDto) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado: id=" + id));

        if (usuarioPatchDto.getTipoUsuarioId() != null) {
            TipoUsuario tipoUsuarioPatch = tipoUsuarioRepository.findById(usuarioPatchDto.getTipoUsuarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuario nao encontrado: id=" + usuarioPatchDto.getTipoUsuarioId()));
            usuario.setTipoUsuario(tipoUsuarioPatch);
        }

        if (usuarioPatchDto.getNome() != null) usuario.setNome(usuarioPatchDto.getNome());
        if (usuarioPatchDto.getEmail() != null) usuario.setEmail(usuarioPatchDto.getEmail());
        if (usuarioPatchDto.getLogin() != null) usuario.setLogin(usuarioPatchDto.getLogin());
        if (usuarioPatchDto.getEndereco() != null) usuario.setEndereco(usuarioPatchDto.getEndereco());

        usuario.setDataUltimaAlteracao(LocalDateTime.now());

        return usuarioRepository.atualizarUsuario(id, usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado: id=" + id));
    }

    public String excluirUsuario(Long id) {

        int rows = usuarioRepository.remove(id);

        if (rows == 0) {
            throw new ResourceNotFoundException("Usuario nao encontrado: id=" + id);
        }

        return "Usuario excluido com sucesso";
    }


    public void atualizarSenha(Long id, UsuarioAtualizarSenhaRequestDTO dto) {

        if (dto == null || !StringUtils.hasText(dto.getSenha())) {
            throw new IllegalArgumentException("A nova senha é obrigatoria");
        }

        String novaSenha = dto.getSenha().trim();

        if (novaSenha.length() < 6) {
            throw new IllegalArgumentException("A nova senha deve ter pelo menos 6 caracteres");
        }

        usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado: id=" + id));

        int rows = usuarioRepository.atualizarSenha(id, novaSenha);

        if (rows == 0) {
            throw new ResourceNotFoundException("Usuario nao encontrado: id=" + id);
        }
    }


}
