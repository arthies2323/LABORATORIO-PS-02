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
        System.out.println("=== REGISTRO DE USUÁRIO ===");
        System.out.println("Username: " + usuario.getUsername());
        System.out.println("Tipo: " + tipo);
        
        if (result.hasErrors()) {
            System.out.println("Erros de validação: " + result.getAllErrors());
            return "auth/register";
        }
        
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        if ("AGENTE".equalsIgnoreCase(tipo)) {
            usuario.setRole("ROLE_AGENTE");
        } else {
            usuario.setRole("ROLE_CLIENTE");
        }
        
        System.out.println("Role definido: " + usuario.getRole());
        
        try {
            Usuario saved = repo.save(usuario);
            System.out.println("Usuário salvo com sucesso! ID: " + saved.getId());
            return "redirect:/login";
        } catch (Exception e) {
            System.err.println("Erro ao salvar usuário: " + e.getMessage());
            e.printStackTrace();
            return "auth/register";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
}
