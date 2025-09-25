package com.autorent.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Empregador {
    private String nome;
    private Double renda;

    public Empregador() {}

    public Empregador(String nome, Double renda) {
        this.nome = nome;
        this.renda = renda;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Double getRenda() { return renda; }
    public void setRenda(Double renda) { this.renda = renda; }
}
