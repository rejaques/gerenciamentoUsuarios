package com.restaurantes.gerenciamento.usuarios.repository.interfaces;

import com.restaurantes.gerenciamento.usuarios.model.Funcionarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionarios, Long> {
}
