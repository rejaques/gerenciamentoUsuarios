package com.restaurantes.gerenciamento.usuarios.dto;

import com.restaurantes.gerenciamento.usuarios.validations.SenhaForte;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlterarDadosUsuarioDto {

    @NotNull
    private Long idUsuario;

    @Size(max = 50)
    private String nome;

    private String login;

    @SenhaForte
    private String senha;

    @Email
    private String email;

    private AlterarEnderecoDto endereco;
}
