package com.autorent.service;

import com.autorent.model.Pedido;
import com.autorent.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    private final PedidoRepository repo;

    public PedidoService(PedidoRepository repo) { this.repo = repo; }

    public List<Pedido> findAll() { return repo.findAll(); }
    public List<Pedido> findByClienteId(Long clienteId) { return repo.findByClienteId(clienteId); }
    public Optional<Pedido> findById(Long id) { return repo.findById(id); }
    public Pedido save(Pedido p) { return repo.save(p); }
    public void deleteById(Long id) { repo.deleteById(id); }
}
