package com.restaurantes.gerenciamento.usuarios.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teste")
public class TestController {

    @GetMapping
    public String test() {
        return "Sucesso! Autenticado!";
    }
}
