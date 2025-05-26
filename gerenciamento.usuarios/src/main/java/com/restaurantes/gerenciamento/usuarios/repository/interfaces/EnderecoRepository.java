package com.restaurantes.gerenciamento.usuarios.repository.interfaces;

import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    boolean existsById(Long id);
}
