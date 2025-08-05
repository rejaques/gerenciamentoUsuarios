package com.restaurantes.gerenciamento.usuarios.service;

import com.restaurantes.gerenciamento.usuarios.dto.AlterarDadosUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.dto.CadastrarUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.exception.*;
import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.EnderecoRepository;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private CadastrarUsuarioDto cadastrarDto;
    private AlterarDadosUsuarioDto alterarDto;
    private Endereco endereco;
    private Usuarios usuario;

    @BeforeEach
    void setUp() {
        endereco = new Endereco();
        endereco.setId(1L);

        cadastrarDto = new CadastrarUsuarioDto();
        cadastrarDto.setNome("João da Silva");
        cadastrarDto.setLogin("joao123");
        cadastrarDto.setEmail("joao@email.com");
        cadastrarDto.setSenha("123456");

        alterarDto = new AlterarDadosUsuarioDto();
        alterarDto.setIdUsuario(1L);
        alterarDto.setNome("João Atualizado");
        alterarDto.setLogin("joaoAtualizado");
        alterarDto.setEmail("novo@email.com");
        alterarDto.setSenha("novaSenha");

        usuario = new Usuarios();
        usuario.setId(1L);
        usuario.setLogin("joao123");
        usuario.setEmail("joao@email.com");
        usuario.setNome("João da Silva");
        usuario.setSenha("123456");
        usuario.setEndereco(1L);
    }

    @Test
    void deveCriarUsuarioComSucesso() {
        when(usuarioRepository.existsByLogin(cadastrarDto.getLogin())).thenReturn(false);
        when(enderecoRepository.existsById(endereco.getId())).thenReturn(true);
        when(passwordEncoder.encode(cadastrarDto.getSenha())).thenReturn("senha-criptografada");
        when(usuarioRepository.save(any(Usuarios.class))).thenReturn(usuario);

        Usuarios criado = usuarioService.criarUsuario(cadastrarDto, endereco);

        assertNotNull(criado);
        assertEquals(usuario.getId(), criado.getId());
    }

    @Test
    void deveLancarExcecaoQuandoLoginJaExistir() {
        when(usuarioRepository.existsByLogin(cadastrarDto.getLogin())).thenReturn(true);

        assertThrows(LoginNaoDisponivelException.class,
                () -> usuarioService.criarUsuario(cadastrarDto, endereco));
    }

    @Test
    void deveLancarExcecaoQuandoEnderecoInvalido() {
        when(usuarioRepository.existsByLogin(cadastrarDto.getLogin())).thenReturn(false);
        when(enderecoRepository.existsById(endereco.getId())).thenReturn(false);

        assertThrows(IdNulo.class,
                () -> usuarioService.criarUsuario(cadastrarDto, endereco));
    }

    @Test
    void deveAlterarUsuarioComSucesso() {
        when(usuarioRepository.findById(alterarDto.getIdUsuario())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.encode(alterarDto.getSenha())).thenReturn("novaSenhaCriptografada");
        when(usuarioRepository.save(any(Usuarios.class))).thenReturn(usuario);

        Usuarios alterado = usuarioService.alterarUsuario(alterarDto, endereco);

        assertNotNull(alterado);
        assertEquals(usuario.getId(), alterado.getId());
    }

    @Test
    void deveLancarExcecaoQuandoIdNaoInformadoNaAlteracao() {
        alterarDto.setIdUsuario(null);

        assertThrows(IdNulo.class,
                () -> usuarioService.alterarUsuario(alterarDto, endereco));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoParaAlteracao() {
        when(usuarioRepository.findById(alterarDto.getIdUsuario())).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class,
                () -> usuarioService.alterarUsuario(alterarDto, endereco));
    }

    @Test
    void deveBuscarUsuarioPorEmailComSucesso() {
        when(usuarioRepository.findIdByEmail("joao@email.com")).thenReturn(Optional.of(usuario));

        Usuarios encontrado = usuarioService.buscaUsuarioPeloEmail("joao@email.com");

        assertNotNull(encontrado);
        assertEquals("joao@email.com", encontrado.getEmail());
    }

    @Test
    void deveLancarExcecaoQuandoEmailNuloOuVazio() {
        assertThrows(EmailNuloException.class,
                () -> usuarioService.buscaUsuarioPeloEmail("  "));
    }

    @Test
    void deveLancarExcecaoQuandoEmailNaoEncontrado() {
        when(usuarioRepository.findIdByEmail("nao@existe.com")).thenReturn(Optional.empty());

        assertThrows(EmailNaoEncontradoExcepition.class,
                () -> usuarioService.buscaUsuarioPeloEmail("nao@existe.com"));
    }

    @Test
    void deveBuscarUsuarioPorIdComSucesso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuarios encontrado = usuarioService.buscaUsuarioPeloId(1L);

        assertNotNull(encontrado);
        assertEquals(1L, encontrado.getId());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoPorId() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class,
                () -> usuarioService.buscaUsuarioPeloId(99L));
    }
}
