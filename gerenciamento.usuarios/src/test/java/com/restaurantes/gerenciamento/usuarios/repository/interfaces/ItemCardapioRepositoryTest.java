package com.restaurantes.gerenciamento.usuarios.repository.interfaces;

import com.restaurantes.gerenciamento.usuarios.model.Funcionarios;
import com.restaurantes.gerenciamento.usuarios.model.ItemCardapio;
import com.restaurantes.gerenciamento.usuarios.model.Restaurantes;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemCardapioRepositoryTest {

    @Autowired
    private ItemCardapioRepository itemRepo;

    @Autowired
    private RestauranteRepository restauranteRepo;

    @Autowired
    private FuncionarioRepository funcionariosRepo;

    @Autowired
    private UsuarioRepository usuariosRepo;

    private Restaurantes restaurante;

    @BeforeEach
    void setup() {
        // Cria e salva o usuário
        Usuarios usuario = new Usuarios();
        usuario.setLogin("teste");
        usuario.setSenha("123456");
        usuario.setEmail("teste@email.com");
        usuario = usuariosRepo.save(usuario);

        // Cria e salva o funcionário vinculado ao usuário
        Funcionarios funcionario = new Funcionarios();
        funcionario.setUsuario(usuario);
        funcionario.setDonoId(null); // ou algum valor se necessário
        funcionario = funcionariosRepo.save(funcionario);

        // Cria e salva o restaurante com dono
        restaurante = new Restaurantes();
        restaurante.setNome("Sabores do Sul");
        restaurante.setEndereco("Rua da Praia, 100");
        restaurante.setTipoCozinha("Gaúcha");
        restaurante.setHorarioFuncionamento("10h às 22h");
        restaurante.setDono(funcionario); // <- importante!

        restaurante = restauranteRepo.save(restaurante);
    }

    @Test
    void deveSalvarEListarItemCardapio() {
        ItemCardapio item = new ItemCardapio();
        item.setNome("Churrasco");
        item.setDescricao("Picanha, costela e linguiça");
        item.setPreco(new BigDecimal("59.90"));
        item.setFoto("/img/churrasco.jpg");
        item.setDisponivelSomenteNoLocal(false);
        item.setRestaurante(restaurante);

        itemRepo.save(item);

        List<ItemCardapio> itens = itemRepo.findByRestauranteId(restaurante.getId());

        assertFalse(itens.isEmpty());
        assertEquals("Churrasco", itens.get(0).getNome());
    }
}
