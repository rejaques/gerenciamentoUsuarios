package com.restaurantes.gerenciamento.usuarios.repository.interfaces;

import com.restaurantes.gerenciamento.usuarios.model.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Clientes, Long> {
}
