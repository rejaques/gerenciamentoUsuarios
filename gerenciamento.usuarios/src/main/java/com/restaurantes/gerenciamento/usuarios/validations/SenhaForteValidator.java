package com.restaurantes.gerenciamento.usuarios.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SenhaForteValidator implements ConstraintValidator<SenhaForte, String> {

    @Override
    public boolean isValid(String senha, ConstraintValidatorContext context) {
        if (senha == null) return false;

        return senha.length() >= 8 &&
                senha.matches(".*[A-Z].*") &&
                senha.matches(".*[a-z].*") &&
                senha.matches(".*\\d.*") &&
                senha.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }
}
