package com.restaurantes.gerenciamento.usuarios.repository.interfaces;

import com.restaurantes.gerenciamento.usuarios.model.ItemCardapio;
import com.restaurantes.gerenciamento.usuarios.model.Restaurantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaRepositories(basePackageClasses = {
        ItemCardapioRepository.class,
        RestauranteRepository.class
})
class ItemCardapioRepositoryTest {

    @Autowired
    private ItemCardapioRepository itemRepo;

    @Autowired
    private RestauranteRepository restauranteRepo;

    private Restaurantes restaurante;

    @BeforeEach
    void setup() {
        restaurante = new Restaurantes();
        restaurante.setNome("Sabores do Sul");
        restaurante.setEndereco("Rua da Praia, 100");
        restaurante.setTipoCozinha("Gaúcha");
        restaurante.setHorarioFuncionamento("10h às 22h");

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