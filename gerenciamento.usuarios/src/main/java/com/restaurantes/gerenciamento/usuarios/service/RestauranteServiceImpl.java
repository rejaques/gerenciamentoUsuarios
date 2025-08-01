package com.restaurantes.gerenciamento.usuarios.service;

import com.restaurantes.gerenciamento.usuarios.model.Funcionarios;
import com.restaurantes.gerenciamento.usuarios.model.Restaurantes;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.FuncionarioRepository;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.RestauranteRepository;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestauranteServiceImpl implements RestauranteService {

    private final RestauranteRepository restauranteRepository;

    private final FuncionarioRepository funcionarioRepository;

    public List<Restaurantes> listarTodos() {
        return restauranteRepository.findAll();
    }

    public Restaurantes salvar(Restaurantes restaurante, Long donoId) {
        Funcionarios dono = funcionarioRepository.findById(donoId)
                .orElseThrow(() -> new RuntimeException("Dono não encontrado"));

        restaurante.setDono(dono);
        return restauranteRepository.save(restaurante);
    }

    public Optional<Restaurantes> buscarPorId(Long id) {
        return restauranteRepository.findById(id);
    }

    public void deletar(Long id) {
        restauranteRepository.deleteById(id);
    }
}
