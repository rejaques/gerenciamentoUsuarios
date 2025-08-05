package com.restaurantes.gerenciamento.usuarios.service;

import com.restaurantes.gerenciamento.usuarios.model.ItemCardapio;
import com.restaurantes.gerenciamento.usuarios.model.Restaurantes;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.ItemCardapioRepository;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.RestauranteRepository;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.ItemCardapioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemCardapioServiceTest {

    @InjectMocks
    private ItemCardapioServiceImpl service;

    @Mock
    private ItemCardapioRepository itemRepo;

    @Mock
    private RestauranteRepository restauranteRepo;

    private Restaurantes restaurante;
    private ItemCardapio item;

    @BeforeEach
    void setUp() {
        restaurante = new Restaurantes();
        restaurante.setId(1L);
        restaurante.setNome("Bom Sabor");

        item = new ItemCardapio();
        item.setId(10L);
        item.setNome("Feijoada");
        item.setDescricao("Feijoada completa");
        item.setPreco(new BigDecimal("39.90"));
        item.setDisponivelSomenteNoLocal(false);
        item.setFoto("/img/feijoada.png");
        item.setRestaurante(restaurante);
    }

    @Test
    void deveSalvarItemComRestaurante() {
        when(restauranteRepo.findById(1L)).thenReturn(Optional.of(restaurante));
        when(itemRepo.save(any())).thenReturn(item);

        ItemCardapio salvo = service.salvar(item, 1L);

        assertNotNull(salvo);
        assertEquals("Feijoada", salvo.getNome());
        verify(itemRepo).save(any(ItemCardapio.class));
    }

    @Test
    void deveListarTodosOsItens() {
        when(itemRepo.findAll()).thenReturn(java.util.List.of(item));

        List<ItemCardapio> lista = service.listarTodos();

        assertEquals(1, lista.size());
        assertEquals("Feijoada", lista.get(0).getNome());
    }

    @Test
    void deveListarItensPorRestaurante() {
        when(itemRepo.findByRestauranteId(1L)).thenReturn(java.util.List.of(item));

        List<ItemCardapio> lista = service.listarPorRestaurante(1L);

        assertFalse(lista.isEmpty());
        assertEquals("Feijoada", lista.get(0).getNome());
    }

    @Test
    void deveBuscarItemPorId() {
        when(itemRepo.findById(10L)).thenReturn(Optional.of(item));

        Optional<ItemCardapio> resultado = service.buscarPorId(10L);

        assertTrue(resultado.isPresent());
        assertEquals("Feijoada", resultado.get().getNome());
    }

    @Test
    void deveDeletarItemPorId() {
        doNothing().when(itemRepo).deleteById(10L);

        service.deletar(10L);

        verify(itemRepo).deleteById(10L);
    }
}
