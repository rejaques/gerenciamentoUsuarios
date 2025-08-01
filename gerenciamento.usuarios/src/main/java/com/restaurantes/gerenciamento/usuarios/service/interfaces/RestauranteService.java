package com.restaurantes.gerenciamento.usuarios.service.interfaces;

import com.restaurantes.gerenciamento.usuarios.model.Restaurantes;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.FuncionarioRepository;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RestauranteService {

    void deletar(Long id);

    Optional<Restaurantes> buscarPorId(Long id);

    Restaurantes salvar(Restaurantes restaurante, Long donoId);

    public List<Restaurantes> listarTodos();


}
