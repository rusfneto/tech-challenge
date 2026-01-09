package br.com.fiap.postech.techchallenge.services;

import br.com.fiap.postech.techchallenge.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public boolean validarLogin(String login, String senha) {
        if (!StringUtils.hasText(login) || !StringUtils.hasText(senha)) {
            return false;
        }
        return usuarioRepository.existeLoginESenha(login, senha);
    }
}
