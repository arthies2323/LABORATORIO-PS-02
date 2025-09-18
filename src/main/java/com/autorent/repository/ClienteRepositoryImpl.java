package com.autorent.repository;

import com.autorent.model.Cliente;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private final Map<Long, Cliente> clientes = new HashMap<>();
    private final AtomicLong counter = new AtomicLong(1);

    @Override
    public List<Cliente> findAll() {
        return new ArrayList<>(clientes.values());
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return Optional.ofNullable(clientes.get(id));
    }

    @Override
    public Cliente save(Cliente cliente) {
        if (cliente.getId() == null) {
            // Novo cliente
            cliente.setId(counter.getAndIncrement());
        }
        clientes.put(cliente.getId(), cliente);
        return cliente;
    }

    @Override
    public void deleteById(Long id) {
        clientes.remove(id);
    }
}