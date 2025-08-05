package com.restaurantes.gerenciamento.usuarios.service;

import com.restaurantes.gerenciamento.usuarios.model.Funcionarios;
import com.restaurantes.gerenciamento.usuarios.model.Restaurantes;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.FuncionarioRepository;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.RestauranteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteServiceImplTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @InjectMocks
    private RestauranteServiceImpl restauranteService;

    @Test
    void deveListarTodosOsRestaurantes() {
        List<Restaurantes> restaurantes = List.of(new Restaurantes(), new Restaurantes());

        when(restauranteRepository.findAll()).thenReturn(restaurantes);

        List<Restaurantes> resultado = restauranteService.listarTodos();

        assertEquals(2, resultado.size());
        verify(restauranteRepository, times(1)).findAll();
    }

    @Test
    void deveSalvarRestauranteComDono() {
        Long donoId = 1L;
        Funcionarios dono = new Funcionarios();
        dono.setId(donoId);

        Restaurantes restaurante = new Restaurantes();
        restaurante.setNome("Restaurante Teste");

        when(funcionarioRepository.findById(donoId)).thenReturn(Optional.of(dono));
        when(restauranteRepository.save(any(Restaurantes.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Restaurantes salvo = restauranteService.salvar(restaurante, donoId);

        assertNotNull(salvo);
        assertEquals(dono, salvo.getDono());
        verify(funcionarioRepository).findById(donoId);
        verify(restauranteRepository).save(restaurante);
    }

    @Test
    void deveLancarExcecaoQuandoDonoNaoEncontrado() {
        Long donoId = 999L;
        Restaurantes restaurante = new Restaurantes();

        when(funcionarioRepository.findById(donoId)).thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(RuntimeException.class, () -> {
            restauranteService.salvar(restaurante, donoId);
        });

        assertEquals("Dono não encontrado", excecao.getMessage());
        verify(funcionarioRepository).findById(donoId);
        verify(restauranteRepository, never()).save(any());
    }

    @Test
    void deveBuscarRestaurantePorId() {
        Long id = 1L;
        Restaurantes restaurante = new Restaurantes();
        restaurante.setId(id);

        when(restauranteRepository.findById(id)).thenReturn(Optional.of(restaurante));

        Optional<Restaurantes> resultado = restauranteService.buscarPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
        verify(restauranteRepository).findById(id);
    }

    @Test
    void deveDeletarRestaurantePorId() {
        Long id = 1L;

        doNothing().when(restauranteRepository).deleteById(id);

        restauranteService.deletar(id);

        verify(restauranteRepository).deleteById(id);
    }
}
