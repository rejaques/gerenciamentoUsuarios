package com.restaurantes.gerenciamento.usuarios.dto;

import com.restaurantes.gerenciamento.usuarios.validations.SenhaForte;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadastrarUsuarioDto {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Usuário é obrigatório")
    private String login;

    @SenhaForte
    private String senha;

    @NotBlank(message = "Email é obrigatório")
    private String email;

    private EnderecoDto endereco;
}
