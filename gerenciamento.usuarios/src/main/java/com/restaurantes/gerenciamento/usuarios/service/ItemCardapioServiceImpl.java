package com.restaurantes.gerenciamento.usuarios.service;

import com.restaurantes.gerenciamento.usuarios.model.ItemCardapio;
import com.restaurantes.gerenciamento.usuarios.model.Restaurantes;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.ItemCardapioRepository;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.RestauranteRepository;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.ItemCardapioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemCardapioServiceImpl implements ItemCardapioService {

    private final ItemCardapioRepository itemCardapioRepository;

    private final RestauranteRepository restauranteRepository;


    @Override
    public List<ItemCardapio> listarTodos() {
        return itemCardapioRepository.findAll();
    }

    @Override
    public List<ItemCardapio> listarPorRestaurante(Long restauranteId) {
        return itemCardapioRepository.findByRestauranteId(restauranteId);
    }

    @Override
    public ItemCardapio salvar(ItemCardapio item, Long restauranteId) {
        Restaurantes restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        item.setRestaurante(restaurante);
        return itemCardapioRepository.save(item);
    }

    @Override
    public Optional<ItemCardapio> buscarPorId(Long id) {
        return itemCardapioRepository.findById(id);
    }

    @Override
    public void deletar(Long id) {
        itemCardapioRepository.deleteById(id);
    }
}
