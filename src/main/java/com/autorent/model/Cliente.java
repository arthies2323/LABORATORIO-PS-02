package com.autorent.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Cliente {
    private Long id;

    @NotBlank(message = "O RG é obrigatório")
    private String rg;

    @NotBlank(message = "O CPF é obrigatório")
    private String cpf;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O endereço é obrigatório")
    private String endereco;

    @NotBlank(message = "A profissão é obrigatória")
    private String profissao;

    // Lista de entidades empregadoras (máximo 3)
    @Size(max = 3, message = "Máximo de 3 entidades empregadoras permitidas")
    private List<EntidadeEmpregadora> entidadesEmpregadoras = new ArrayList<>();

    // Classe interna para representar entidade empregadora e rendimento
    @Data
    @NoArgsConstructor
    public static class EntidadeEmpregadora {
        @NotBlank(message = "Nome da entidade empregadora é obrigatório")
        private String nome;
        
        @NotNull(message = "Rendimento é obrigatório")
        private Double rendimento;
    }
}