package com.produtoapi.controller;

import com.produtoapi.model.Usuario;
import com.produtoapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    // O "triturador" de senhas — transforma "123456" em "$2a$10$xK3rZ..."
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // CADASTRO — criptografa a senha antes de salvar
    @PostMapping
    public Usuario salvar(@RequestBody Usuario usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    // LOGIN — compara a senha digitada com a senha criptografada do banco
    @PostMapping("/login")
    public Usuario login(@RequestBody Usuario dadosLogin) {
        Usuario usuario = usuarioRepository.findByEmail(dadosLogin.getEmail())
                .orElseThrow(() -> new RuntimeException("E-mail ou senha incorretos"));

        // encoder.matches() compara "123456" com "$2a$10$xK3rZ..." e retorna true/false
        if (!encoder.matches(dadosLogin.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("E-mail ou senha incorretos");
        }

        return usuario;
    }
}