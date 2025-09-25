package com.autorent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String cpf;

    private String rg;
    private String endereco;
    private String profissao;

    @ElementCollection
    @CollectionTable(name = "cliente_empregadoras", joinColumns = @JoinColumn(name = "cliente_id"))
    private java.util.List<Empregador> empregadores = new java.util.ArrayList<>();

    public Cliente() {}

    // getters e setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getRg() { return rg; }
    public void setRg(String rg) { this.rg = rg; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getProfissao() { return profissao; }
    public void setProfissao(String profissao) { this.profissao = profissao; }

    public java.util.List<Empregador> getEmpregadores() { return empregadores; }
    public void setEmpregadores(java.util.List<Empregador> empregadores) { this.empregadores = empregadores; }
}
