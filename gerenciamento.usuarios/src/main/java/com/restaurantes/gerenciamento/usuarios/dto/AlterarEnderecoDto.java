package com.restaurantes.gerenciamento.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlterarEnderecoDto {

    @NotNull(message = "Informe o id")
    private Long enderecoId;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    @NotBlank(message = "CEP não pode ser nulo")
    private String cep;
}
