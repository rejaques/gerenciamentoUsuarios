package com.restaurantes.gerenciamento.usuarios.repository.interfaces;

import com.restaurantes.gerenciamento.usuarios.dto.BuscaDadosDto;
import com.restaurantes.gerenciamento.usuarios.model.Clientes;
import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private EnderecoRepository enderecoRepo;

    @Autowired
    private EntityManager entityManager;

    private Usuarios usuario;
    private Endereco endereco;

    @BeforeEach
    void setup() {
        // Cria e salva endereço
        endereco = new Endereco();
        endereco.setRua("Rua das Flores");
        endereco.setNumero("123");
        endereco.setComplemento("Apto 101");
        endereco.setBairro("Centro");
        endereco.setCidade("Porto Alegre");
        endereco.setEstado("RS");
        endereco.setCep("90000-000");
        endereco = enderecoRepo.save(endereco);

        // Cria e salva usuário
        usuario = new Usuarios();
        usuario.setNome("João da Silva");
        usuario.setLogin("joaosilva");
        usuario.setSenha("senha123");
        usuario.setEmail("joao@email.com");
        usuario.setEndereco(1L);
        usuario = usuarioRepo.save(usuario);

        // Cria e salva cliente vinculado ao usuário
        Clientes cliente = new Clientes();
        cliente.setUsuario(usuario);
        clienteRepo.save(cliente);

        // Garante persistência antes dos testes
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void deveBuscarDadosDoClientePorId() {
        // Busca o cliente pelo ID via repositório customizado
        Optional<Clientes> clienteOptional = clienteRepo.findByUsuario_Id(usuario.getId());
        assertTrue(clienteOptional.isPresent());

        Clientes cliente = clienteOptional.get();
        BuscaDadosDto dto = clienteRepo.bucarDadosClientes(cliente.getId());

        assertNotNull(dto);
        assertEquals("João da Silva", dto.getNome());
        assertEquals("joao@email.com", dto.getEmail());
        assertEquals("joaosilva", dto.getLogin());
        assertNotNull(dto.getEndereco());
    }
}
