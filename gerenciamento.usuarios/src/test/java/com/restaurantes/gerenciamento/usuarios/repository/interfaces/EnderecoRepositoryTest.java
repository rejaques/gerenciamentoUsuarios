package com.restaurantes.gerenciamento.usuarios.repository.interfaces;

import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class EnderecoRepositoryTest {

    @Autowired
    private EnderecoRepository enderecoRepository;

    private Endereco endereco;

    @BeforeEach
    void setUp() {
        endereco = new Endereco();
        endereco.setRua("Av. Brasil");
        endereco.setNumero("1000");
        endereco.setComplemento("Bloco A");
        endereco.setBairro("Centro");
        endereco.setCidade("Porto Alegre");
        endereco.setEstado("RS");
        endereco.setCep("90000-000");

        endereco = enderecoRepository.save(endereco);
    }

    @Test
    void deveSalvarEVerificarSeEnderecoExiste() {
        boolean existe = enderecoRepository.existsById(endereco.getId());
        assertTrue(existe);
    }

    @Test
    void deveBuscarEnderecoPorId() {
        Optional<Endereco> resultado = enderecoRepository.findById(endereco.getId());
        assertTrue(resultado.isPresent());
        assertEquals("Av. Brasil", resultado.get().getRua());
    }
}
