package com.restaurantes.gerenciamento.usuarios.dto;

import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DadosAlteradosDto {

    Usuarios usuario;
    Endereco endereco;
}
