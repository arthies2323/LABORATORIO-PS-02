package com.autorent.controller;

import com.autorent.model.Usuario;
import com.autorent.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
public class UsuarioController {

    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;

    public UsuarioController(UsuarioRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid Usuario usuario, BindingResult result, @RequestParam(defaultValue = "CLIENTE") String tipo) {
        if (result.hasErrors()) {
            return "auth/register";
        }
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        if ("AGENTE".equalsIgnoreCase(tipo)) {
            usuario.setRole("ROLE_AGENTE");
        } else {
            usuario.setRole("ROLE_CLIENTE");
        }
        repo.save(usuario);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
}
