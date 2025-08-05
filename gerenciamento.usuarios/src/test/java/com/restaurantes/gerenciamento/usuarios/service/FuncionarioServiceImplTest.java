package com.restaurantes.gerenciamento.usuarios.service;

import com.restaurantes.gerenciamento.usuarios.dto.CadastrarUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.dto.EnderecoDto;
import com.restaurantes.gerenciamento.usuarios.dto.FuncionarioResponseDto;
import com.restaurantes.gerenciamento.usuarios.exception.CepNuloException;
import com.restaurantes.gerenciamento.usuarios.exception.EmailNuloException;
import com.restaurantes.gerenciamento.usuarios.exception.FalhaAoSalvarException;
import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.model.Funcionarios;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.FuncionarioRepository;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.EnderecoService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceImplTest {

    @Mock
    private EnderecoService enderecoService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @InjectMocks
    private FuncionarioServiceImpl funcionarioService;

    private CadastrarUsuarioDto dto;
    private Endereco endereco;
    private Usuarios usuario;
    private Funcionarios funcionario;

    @BeforeEach
    void setUp() {
        EnderecoDto enderecoDto = new EnderecoDto();
        enderecoDto.setCep("12345-678");

        dto = new CadastrarUsuarioDto();
        dto.setEmail("teste@email.com");
        dto.setNome("João da Silva");
        dto.setEndereco(enderecoDto);

        endereco = new Endereco();
        endereco.setId(1L);

        usuario = new Usuarios();
        usuario.setId(2L);
        usuario.setEmail(dto.getEmail());

        funcionario = new Funcionarios();
        funcionario.setId(3L);
        funcionario.setUsuario(usuario);
    }

    @Test
    void deveCriarFuncionarioComSucesso() {
        // Arrange
        when(enderecoService.salvarEndereco(dto.getEndereco())).thenReturn(endereco);
        when(usuarioService.criarUsuario(dto, endereco)).thenReturn(usuario);
        when(funcionarioRepository.save(any(Funcionarios.class))).thenReturn(funcionario);

        // Act
        FuncionarioResponseDto response = funcionarioService.criarFuncionario(dto);

        // Assert
        assertNotNull(response);
        assertEquals(usuario.getId(), response.getIdUsuario());
        assertEquals(dto.getEmail(), response.getEmail());
        assertEquals(endereco.getId(), response.getIdEndereco());
    }

    @Test
    void deveLancarExcecaoQuandoCepNulo() {
        dto.getEndereco().setCep(null);

        assertThrows(CepNuloException.class, () -> funcionarioService.criarFuncionario(dto));
    }

    @Test
    void deveLancarExcecaoQuandoEmailVazio() {
        dto.setEmail("   ");

        assertThrows(EmailNuloException.class, () -> funcionarioService.criarFuncionario(dto));
    }

    @Test
    void deveLancarExcecaoAoFalharSalvarEndereco() {
        when(enderecoService.salvarEndereco(dto.getEndereco()))
                .thenThrow(new RuntimeException("Erro ao salvar"));

        Exception exception = assertThrows(FalhaAoSalvarException.class, () -> funcionarioService.criarFuncionario(dto));
        assertTrue(exception.getMessage().contains("Falha ao salvar endereço do funcionário"));
    }

    @Test
    void deveLancarExcecaoAoFalharCriarUsuario() {
        when(enderecoService.salvarEndereco(dto.getEndereco())).thenReturn(endereco);
        when(usuarioService.criarUsuario(dto, endereco))
                .thenThrow(new RuntimeException("Erro ao criar usuário"));

        Exception exception = assertThrows(FalhaAoSalvarException.class, () -> funcionarioService.criarFuncionario(dto));
        assertTrue(exception.getMessage().contains("Falha ao criar usuário base para o funcionário"));
    }

    @Test
    void deveLancarExcecaoAoFalharSalvarFuncionario() {
        when(enderecoService.salvarEndereco(dto.getEndereco())).thenReturn(endereco);
        when(usuarioService.criarUsuario(dto, endereco)).thenReturn(usuario);
        when(funcionarioRepository.save(any(Funcionarios.class)))
                .thenThrow(new RuntimeException("Erro ao salvar funcionário"));

        Exception exception = assertThrows(FalhaAoSalvarException.class, () -> funcionarioService.criarFuncionario(dto));
        assertTrue(exception.getMessage().contains("Falha ao persistir dados do funcionário"));
    }
}
