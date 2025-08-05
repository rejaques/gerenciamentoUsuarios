package com.restaurantes.gerenciamento.usuarios.service;

import com.restaurantes.gerenciamento.usuarios.dto.BuscaDadosDto;
import com.restaurantes.gerenciamento.usuarios.dto.CadastrarUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.dto.ClienteResponseDto;
import com.restaurantes.gerenciamento.usuarios.dto.EnderecoDto;
import com.restaurantes.gerenciamento.usuarios.model.Clientes;
import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.ClienteRepository;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.EnderecoService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Mock
    private EnderecoService enderecoService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private ClienteRepository clienteRepository;

    @Test
    void deveCriarClienteComSucesso() {
        // Arrange (dados de entrada simulados)
        EnderecoDto enderecoDto = new EnderecoDto();
        enderecoDto.setCep("12345678");
        enderecoDto.setRua("Rua Teste");
        enderecoDto.setNumero("100");

        CadastrarUsuarioDto dto = new CadastrarUsuarioDto();
        dto.setNome("Renan");
        dto.setEmail("renan@email.com");
        dto.setEndereco(enderecoDto);

        Endereco endereco = new Endereco();
        endereco.setId(1L);

        Usuarios usuario = new Usuarios();
        usuario.setId(2L);
        usuario.setEmail(dto.getEmail());

        Clientes cliente = new Clientes();
        cliente.setId(3L);
        cliente.setUsuario(usuario);

        // Mocking dos serviços utilizados internamente
        when(enderecoService.salvarEndereco(any(EnderecoDto.class))).thenReturn(endereco);
        when(usuarioService.criarUsuario(eq(dto), eq(endereco))).thenReturn(usuario);
        when(clienteRepository.save(any(Clientes.class))).thenReturn(cliente);

        // Act
        ClienteResponseDto response = clienteService.criarCliente(dto);

        // Assert
        assertNotNull(response);
        assertEquals(usuario.getId(), response.getIdUsuario());
        assertEquals(cliente.getId(), response.getIdFuncionario());
        assertEquals(usuario.getEmail(), response.getEmail());
        assertEquals(endereco.getId(), response.getIdEndereco());

        // Verifica chamadas
        verify(enderecoService).salvarEndereco(any(EnderecoDto.class));
        verify(usuarioService).criarUsuario(eq(dto), eq(endereco));
        verify(clienteRepository).save(any(Clientes.class));
    }
}
