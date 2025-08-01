package com.restaurantes.gerenciamento.usuarios.service.interfaces;

import com.restaurantes.gerenciamento.usuarios.model.ItemCardapio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ItemCardapioService {

    List<ItemCardapio> listarTodos();

    List<ItemCardapio> listarPorRestaurante(Long restauranteId);

    ItemCardapio salvar(ItemCardapio item, Long restauranteId);

    Optional<ItemCardapio> buscarPorId(Long id);

    void deletar(Long id);
}
