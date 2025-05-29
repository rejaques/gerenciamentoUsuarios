package com.restaurantes.gerenciamento.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioResponseDto {
    private Long idUsuario;
    private Long idFuncionario;
    private String email;
    private Long idEndereco;
}
