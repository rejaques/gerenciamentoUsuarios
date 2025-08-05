package com.restaurantes.gerenciamento.usuarios.service;

import com.restaurantes.gerenciamento.usuarios.dto.AlterarEnderecoDto;
import com.restaurantes.gerenciamento.usuarios.dto.EnderecoDto;
import com.restaurantes.gerenciamento.usuarios.exception.EnderecoNaoEncontrado;
import com.restaurantes.gerenciamento.usuarios.exception.EnderecoNulo;
import com.restaurantes.gerenciamento.usuarios.exception.IdNulo;
import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.EnderecoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @InjectMocks
    private EnderecoServiceImpl enderecoService;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Test
    void deveSalvarEnderecoComSucesso() {
        EnderecoDto dto = new EnderecoDto("Rua A", "123", "Ap 1", "Bairro B", "Cidade C", "Estado D", "00000-000");
        Endereco enderecoSalvo = new Endereco(1L, dto.getRua(), dto.getNumero(), dto.getComplemento(), dto.getBairro(), dto.getCidade(), dto.getEstado(), dto.getCep());

        when(enderecoRepository.save(any(Endereco.class))).thenReturn(enderecoSalvo);

        Endereco resultado = enderecoService.salvarEndereco(dto);

        assertNotNull(resultado);
        assertEquals("Rua A", resultado.getRua());
        verify(enderecoRepository).save(any(Endereco.class));
    }

    @Test
    void deveLancarExcecaoQuandoDtoForNulo() {
        assertThrows(EnderecoNulo.class, () -> enderecoService.salvarEndereco(null));
    }

    @Test
    void deveAlterarEnderecoComSucesso() {
        AlterarEnderecoDto dto = new AlterarEnderecoDto();
        dto.setEnderecoId(1L);
        dto.setRua("Nova Rua");

        Endereco existente = new Endereco(1L, "Antiga Rua", "10", null, "Bairro", "Cidade", "Estado", "00000-000");
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(existente);

        Endereco atualizado = enderecoService.alterarEndereco(dto);

        assertEquals("Nova Rua", atualizado.getRua());
        verify(enderecoRepository).save(existente);
    }

    @Test
    void deveLancarExcecaoQuandoIdNuloAoAlterar() {
        AlterarEnderecoDto dto = new AlterarEnderecoDto(); // sem setar o ID
        assertThrows(IdNulo.class, () -> enderecoService.alterarEndereco(dto));
    }

    @Test
    void deveBuscarEnderecoComSucesso() {
        Endereco endereco = new Endereco(1L, "Rua", "123", null, "Bairro", "Cidade", "Estado", "00000-000");
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));

        Endereco resultado = enderecoService.buscarEndereco(1L);

        assertNotNull(resultado);
        assertEquals("Rua", resultado.getRua());
    }

    @Test
    void deveLancarExcecaoQuandoEnderecoNaoEncontrado() {
        when(enderecoRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EnderecoNaoEncontrado.class, () -> enderecoService.buscarEndereco(1L));
    }
}
