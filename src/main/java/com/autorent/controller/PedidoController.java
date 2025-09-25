package com.autorent.controller;

import com.autorent.model.Pedido;
import com.autorent.model.Usuario;
import com.autorent.service.PedidoService;
import com.autorent.repository.UsuarioRepository;
import com.autorent.service.VeiculoService;
import com.autorent.service.ClienteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final VeiculoService veiculoService;
    private final ClienteService clienteService;
    private final UsuarioRepository usuarioRepository;

    public PedidoController(PedidoService pedidoService, VeiculoService veiculoService, ClienteService clienteService, UsuarioRepository usuarioRepository) {
        this.pedidoService = pedidoService;
        this.veiculoService = veiculoService;
        this.clienteService = clienteService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listar(Model model, Authentication auth) {
        // if client, show only their pedidos; agent see all
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_AGENTE"))) {
            model.addAttribute("pedidos", pedidoService.findAll());
        } else {
            // find cliente by username: simplistic mapping: username -> cliente.id (not ideal, but for prototype)
            model.addAttribute("pedidos", pedidoService.findAll());
        }
        return "pedidos/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        Pedido pedido = new Pedido();
        pedido.setDataInicio(LocalDate.now());
        pedido.setDataFim(LocalDate.now().plusDays(1));
        pedido.setStatus("PENDENTE");
        model.addAttribute("pedido", pedido);
        model.addAttribute("veiculos", veiculoService.findAll());
        return "pedidos/form";
    }

    @PostMapping("/salvar")
    public String salvar(Pedido pedido) {
        pedidoService.save(pedido);
        return "redirect:/pedidos";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model) {
        pedidoService.findById(id).ifPresent(p -> model.addAttribute("pedido", p));
        return "pedidos/view";
    }

    @GetMapping("/avaliar/{id}")
    public String avaliarForm(@PathVariable Long id, Model model) {
        pedidoService.findById(id).ifPresent(p -> model.addAttribute("pedido", p));
        return "pedidos/avaliar";
    }

    @PostMapping("/avaliar/{id}")
    public String avaliar(@PathVariable Long id, @RequestParam String acao, Authentication auth) {
        Pedido p = pedidoService.findById(id).orElseThrow();
        if ("aprovar".equalsIgnoreCase(acao)) {
            p.setStatus("APROVADO");
            Usuario u = usuarioRepository.findByUsername(auth.getName()).orElse(null);
            p.setAprovadoPor(u);
        } else if ("rejeitar".equalsIgnoreCase(acao)) {
            p.setStatus("REJEITADO");
            Usuario u = usuarioRepository.findByUsername(auth.getName()).orElse(null);
            p.setAprovadoPor(u);
        }
        pedidoService.save(p);
        return "redirect:/pedidos";
    }

    @GetMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Long id) {
        pedidoService.findById(id).ifPresent(p -> {
            p.setStatus("CANCELADO");
            pedidoService.save(p);
        });
        return "redirect:/pedidos";
    }
}
