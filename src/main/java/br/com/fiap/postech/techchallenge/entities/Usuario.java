package br.com.fiap.postech.techchallenge.entities;

import br.com.fiap.postech.techchallenge.dtos.UsuarioRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private LocalDate dataUltimaAlteracao;
    private String endereco;
    private TipoUsuario tipoUsuario;

    public Usuario(UsuarioRequestDTO usuarioDTO, TipoUsuario tipoUsuario) {
        this.nome = usuarioDTO.nome();
        this.email = usuarioDTO.email();
        this.login = usuarioDTO.login();
        this.senha = usuarioDTO.senha();
        this.endereco = usuarioDTO.endereco();
        this.tipoUsuario = tipoUsuario;
        this.dataUltimaAlteracao = LocalDate.now();
    }
}
