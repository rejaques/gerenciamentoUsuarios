package com.restaurantes.gerenciamento.usuarios.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SenhaForteValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SenhaForte {
    String message() default "Senha fraca: deve conter letras maiúsculas, minúsculas, números, caractere especial e no mínimo 8 caracteres";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
