package com.autorent.service;

import com.autorent.model.Cliente;
import com.autorent.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public List<Cliente> findAll() { return repo.findAll(); }
    public Optional<Cliente> findById(Long id) { return repo.findById(id); }
    public Cliente save(Cliente c) { return repo.save(c); }
    public void deleteById(Long id) { repo.deleteById(id); }
}
