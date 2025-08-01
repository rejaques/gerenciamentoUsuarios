package com.restaurantes.gerenciamento.usuarios.controller;

import com.restaurantes.gerenciamento.usuarios.model.ItemCardapio;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.ItemCardapioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapio")
@RequiredArgsConstructor
public class ItemCardapioController {

    private final ItemCardapioService itemCardapioService;

    @GetMapping
    public List<ItemCardapio> listarTodos() {
        return itemCardapioService.listarTodos();
    }

    @GetMapping("/por-restaurante/{id}")
    public List<ItemCardapio> listarPorRestaurante(@PathVariable Long id) {
        return itemCardapioService.listarPorRestaurante(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemCardapio> buscar(@PathVariable Long id) {
        return itemCardapioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ItemCardapio> criar(@RequestBody ItemCardapio item,
                                                   @RequestParam Long restauranteId) {
        return ResponseEntity.ok(itemCardapioService.salvar(item, restauranteId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        itemCardapioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

