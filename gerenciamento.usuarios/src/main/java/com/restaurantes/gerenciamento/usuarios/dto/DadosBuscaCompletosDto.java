package com.restaurantes.gerenciamento.usuarios.dto;

import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DadosBuscaCompletosDto {
    Usuarios dadosCadastrais;
    Optional<Endereco> dadosEndereco;

}
