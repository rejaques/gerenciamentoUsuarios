package com.restaurantes.gerenciamento.usuarios.repository.interfaces;

import com.restaurantes.gerenciamento.usuarios.dto.BuscaDadosDto;
import com.restaurantes.gerenciamento.usuarios.model.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Clientes, Long> {

    @Query("SELECT new com.restaurantes.gerenciamento.usuarios.dto.BuscaDadosDto(" +
            "u.id, u.nome, u.email, u.login, u.endereco) " +
            "FROM Clientes c " +
            "INNER JOIN c.usuario u " +
            "WHERE c.id = :clienteId")
    BuscaDadosDto bucarDadosClientes(@Param("clienteId") Long clienteId);

    Optional<Clientes> findByUsuario_Id(Long usuarioId);

}
