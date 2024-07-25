package com.walefy.restaurantorders.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

  final List<String> valueList = new ArrayList<>();

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value != null && valueList.contains(value.toUpperCase());
  }

  @Override
  public void initialize(EnumValidator constraintAnnotation) {
    Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClazz();

    Enum<?>[] enumValArr = enumClass.getEnumConstants();

    for (Enum<?> enumVal : enumValArr) {
      valueList.add(enumVal.toString().toUpperCase());
    }
  }

}