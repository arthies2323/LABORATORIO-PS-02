package com.autorent.service;

import com.autorent.model.Veiculo;
import com.autorent.repository.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {
    private final VeiculoRepository repo;

    public VeiculoService(VeiculoRepository repo) {
        this.repo = repo;
    }

    public List<Veiculo> findAll() { return repo.findAll(); }
    public Optional<Veiculo> findById(Long id) { return repo.findById(id); }
    public Veiculo save(Veiculo v) { return repo.save(v); }
    public void deleteById(Long id) { repo.deleteById(id); }
}
