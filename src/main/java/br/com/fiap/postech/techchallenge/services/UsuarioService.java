package br.com.fiap.postech.techchallenge.services;

import br.com.fiap.postech.techchallenge.dtos.UsuarioRequestDTO;
import br.com.fiap.postech.techchallenge.entities.Usuario;
import br.com.fiap.postech.techchallenge.repositories.TipoUsuarioRepository;
import br.com.fiap.postech.techchallenge.repositories.UsuarioRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    public List<Usuario> listarUsuarios(int pagina, int tamanho, int tipo) {
        int offset = (pagina - 1) * tamanho;
        List<Usuario> usuarios = new ArrayList<>();

        if(ObjectUtils.isEmpty(tipo)){
            usuarios = usuarioRepository.listarUsuarios(tamanho, offset);
        } else {
            usuarios = usuarioRepository.listarUsuariosPorTipo(tamanho, offset, tipo);
        }

        return usuarios;
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
}
