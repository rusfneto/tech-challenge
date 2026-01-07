package br.com.fiap.postech.techchallenge.services;

import br.com.fiap.postech.techchallenge.dtos.UsuarioPatchDTO;
import br.com.fiap.postech.techchallenge.dtos.UsuarioRequestDTO;
import br.com.fiap.postech.techchallenge.entities.TipoUsuario;
import br.com.fiap.postech.techchallenge.entities.Usuario;
import br.com.fiap.postech.techchallenge.repositories.TipoUsuarioRepository;
import br.com.fiap.postech.techchallenge.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    public List<Usuario> listarUsuarios(int pagina, int tamanho, Long tipo) {
        int offset = (pagina - 1) * tamanho;

        if (tipo == null) {
            return usuarioRepository.listarUsuarios(tamanho, offset);
        } else {
            return usuarioRepository.listarUsuariosPorTipo(tamanho, offset, tipo);
        }
    }


    public void salvarUsuario(UsuarioRequestDTO usuario) {
        var usuarioEntity = converteDTO(usuario);
        var save = usuarioRepository.save(usuarioEntity);
        Assert.state(save == 1, "Erro ao salvar usuario");
    }

    private Usuario converteDTO(UsuarioRequestDTO usuarioRequestDTO){
        var tipoUsuario = tipoUsuarioRepository.findById(usuarioRequestDTO.tipoUsuario())
                .orElseThrow(() -> new RuntimeException("Tipo de Usuário não encontrado."));

        return new Usuario(usuarioRequestDTO, tipoUsuario);
    }


    public Usuario atualizarUsuario(Long id, UsuarioPatchDTO usuarioPatchDto) {
        
        TipoUsuario tipoUsuarioPatch = null;
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));        
        
        if (usuarioPatchDto.getTipoUsuarioId() != null){
            tipoUsuarioPatch = tipoUsuarioRepository.findById(usuarioPatchDto.getTipoUsuarioId()).orElseThrow(() -> new RuntimeException("Tipo de usuário não encontrado"));
        }
        

        if(usuarioPatchDto.getNome() != null) usuario.setNome(usuarioPatchDto.getNome());
        if(usuarioPatchDto.getEmail() != null) usuario.setEmail(usuarioPatchDto.getEmail());

        if(usuarioPatchDto.getLogin() != null) usuario.setLogin(usuarioPatchDto.getLogin());
        if(usuarioPatchDto.getSenha() != null) usuario.setSenha(usuarioPatchDto.getSenha());
        if(usuarioPatchDto.getEndereco() != null) usuario.setEndereco(usuarioPatchDto.getEndereco());
        
        if(tipoUsuarioPatch != null) usuario.setTipoUsuario(tipoUsuarioPatch);

        usuario.setDataUltimaAlteracao(LocalDateTime.now());

        Optional<Usuario> usuarioAtualizadoOptional = usuarioRepository.atualizarUsuario(id, usuario);

        Usuario usuarioAtualizado = usuarioAtualizadoOptional.orElseThrow(() -> new RuntimeException("Erro ao recuperar o usuário após atualização"));

        //alterar
        return usuarioAtualizado;
        
    }


    public boolean excluirUsuario(Long id) {
        
        int qtdRegistrosExcluidos = usuarioRepository.remove(id);

        return qtdRegistrosExcluidos > 0;
    }
}
