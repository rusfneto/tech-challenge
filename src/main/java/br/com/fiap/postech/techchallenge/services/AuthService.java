package br.com.fiap.postech.techchallenge.services;

import br.com.fiap.postech.techchallenge.exceptions.UnauthorizedException;
import br.com.fiap.postech.techchallenge.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void validarLogin(String login, String senha) {
        if (!StringUtils.hasText(login) || !StringUtils.hasText(senha)) {
            // 400 faz mais sentido que 401 quando faltam dados
            throw new IllegalArgumentException("Login e senha sao obrigatorios");
        }

        boolean ok = usuarioRepository.existeLoginESenha(login.trim(), senha);

        if (!ok) {
            throw new UnauthorizedException("Login ou senha invalidos");
        }
    }
}
