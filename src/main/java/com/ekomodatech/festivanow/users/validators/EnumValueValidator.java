package com.ekomodatech.festivanow.users.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<EnumValue, Enum<?>> {
    private EnumValue annotation;

    @Override
    public void initialize(EnumValue annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are considered valid
        }

        Class<? extends Enum> enumClass = annotation.enumClass();
        Enum<?>[] enumValues = enumClass.getEnumConstants();

        for (Enum<?> enumValue : enumValues) {
            if (enumValue.name().equals(value.name())) {
                return true; // The enum value is valid
            }
        }

        return false; // The enum value is not valid
    }
}