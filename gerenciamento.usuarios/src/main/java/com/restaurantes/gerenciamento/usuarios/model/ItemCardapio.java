package com.restaurantes.gerenciamento.usuarios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "ITEM_CARDAPIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private Boolean disponivelSomenteNoLocal;

    private String foto;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurantes restaurante;

    // Getters e Setters
}
