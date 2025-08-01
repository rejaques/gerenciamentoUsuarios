package com.restaurantes.gerenciamento.usuarios.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "RESTAURANTE")
@Data
public class Restaurantes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String endereco;

    private String tipoCozinha;

    private String horarioFuncionamento;

    @ManyToOne
    @JoinColumn(name = "dono_id", nullable = false)
    private Funcionarios dono;

    // Getters e Setters
}

