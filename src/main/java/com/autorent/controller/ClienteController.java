package com.autorent.controller;

import com.autorent.model.Cliente;
import com.autorent.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "clientes/lista";
    }

    @GetMapping("/novo")
    public String novoCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/formulario";
    }

    @PostMapping
    public String salvarCliente(@Valid @ModelAttribute("cliente") Cliente cliente,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "clientes/formulario";
        }
        
        clienteRepository.save(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/{id}/editar")
    public String editarCliente(@PathVariable Long id, Model model) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com id: " + id));
        model.addAttribute("cliente", cliente);
        return "clientes/formulario";
    }

    @GetMapping("/{id}")
    public String visualizarCliente(@PathVariable Long id, Model model) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com id: " + id));
        model.addAttribute("cliente", cliente);
        return "clientes/visualizar";
    }

    @PostMapping("/{id}/deletar")
    public String deletarCliente(@PathVariable Long id) {
        clienteRepository.deleteById(id);
        return "redirect:/clientes";
    }

    // Para adicionar uma nova entidade empregadora no formulário
    @GetMapping("/adicionarEmpregadora")
    public String adicionarEmpregadora(Model model) {
        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", new Cliente());
        }
        
        Cliente cliente = (Cliente) model.getAttribute("cliente");
        if (cliente.getEntidadesEmpregadoras().size() < 3) {
            cliente.getEntidadesEmpregadoras().add(new Cliente.EntidadeEmpregadora());
        }
        
        return "clientes/formulario";
    }

    // Para remover uma entidade empregadora do formulário
    @GetMapping("/removerEmpregadora/{index}")
    public String removerEmpregadora(@PathVariable int index, Model model) {
        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", new Cliente());
        }
        
        Cliente cliente = (Cliente) model.getAttribute("cliente");
        if (index >= 0 && index < cliente.getEntidadesEmpregadoras().size()) {
            cliente.getEntidadesEmpregadoras().remove(index);
        }
        
        return "clientes/formulario";
    }
}