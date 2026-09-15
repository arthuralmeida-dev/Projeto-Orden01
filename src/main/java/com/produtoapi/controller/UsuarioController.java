package com.produtoapi.controller;

import com.produtoapi.model.Produto;
import com.produtoapi.model.Usuario;
import com.produtoapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuarios")

public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping
    public Usuario salvar(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @PostMapping("/login")
    public Usuario login(@RequestBody Usuario dadosLogin) {
        Usuario usuario = usuarioRepository.findByEmail(dadosLogin.getEmail())
                .orElseThrow(() -> new RuntimeException("E-mail ou senha incorretos"));

        if (!usuario.getSenha().equals(dadosLogin.getSenha())) {
            throw new RuntimeException("E-mail ou senha incorretos");
        }

        return usuario;
    }
}


