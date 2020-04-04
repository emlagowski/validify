# Validify - Java validation library

![Sonar Quality Gate](https://img.shields.io/sonar/quality_gate/io.github.emlagowski:validify?server=https%3A%2F%2Fsonarcloud.io&style=flat)
![Sonar Violations (long format)](https://img.shields.io/sonar/violations/io.github.emlagowski:validify?format=long&server=https%3A%2F%2Fsonarcloud.io&style=flat)
![Sonar Tech Debt](https://img.shields.io/sonar/tech_debt/io.github.emlagowski:validify?server=https%3A%2F%2Fsonarcloud.io&sonarVersion=8.2&style=flat)
[![codecov](https://codecov.io/gh/emlagowski/validify/branch/master/graph/badge.svg)](https://codecov.io/gh/emlagowski/validify)
![GitHub tag (latest SemVer)](https://img.shields.io/github/v/tag/emlagowski/validify?style=flat)
![GitHub](https://img.shields.io/github/license/emlagowski/validify?style=flat)

<!-- ![Sonar Test Count](https://img.shields.io/sonar/total_tests/io.github.emlagowski:validify?server=https%3A%2F%2Fsonarcloud.io&sonarVersion=8.2&style=for-the-badge) -->
<!-- ![Sonar Tests](https://img.shields.io/sonar/tests/io.github.emlagowski:validify?compact_message&server=https%3A%2F%2Fsonarcloud.io&style=for-the-badge) -->

The main purpose of this library is to provide a simple way to create more complicated validations and to accurately return information about errors that occurred during the validation.
The secondary goal is to not use any external dependencies.

## Available validations

At this moment some factory classes provides few out-of-the box self-explanatory (I hope so) validations.

```shell script
CoreValidations
  - lengthIsEqual
  - lengthInRange
  - matches
  - allCharactersAreDigits
  - isEmpty
  - isNotEmpty
  - is
  - isIn
  - startsWith
  - endsWith
IntegerValidations
  - valueInRange
```

### Custom validation

Creating custom validations is as simple as implementing `io.github.emlagowski.validify.Validation` functional interface. 
It have to return `ValidationResult` and to do that methods `ValidationsResult.valid()` and `ValidationResult invalid(String message, Object... args)` can be used.

Example of providing custom validation with implementing Validation interface.

```java
public class IntegerValueInRangeValidation implements Validation<Integer> {

    private final Integer min;
    private final Integer max;

    public IntegerValueInRangeValidation(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public ValidationResult apply(Integer value) {
        return Optional.ofNullable(value)
                .filter(v -> min <= v && v <= max)
                .map(v -> ValidationResult.valid())
                .orElseGet(() -> ValidationResult.invalid("Value '%s' should be in the range [%d, %d]", value, min, max));
    }
}
```

Or it can be even shorter if you would use lambda expression like

```java
public static Validation<Integer> valueInRange(int min, int max) {
    return value -> Optional.ofNullable(value)
            .filter(v -> min <= v && v <= max)
            .map(v -> ValidationResult.valid())
            .orElseGet(() -> ValidationResult.invalid("Value '%s' should be in the range [%d, %d]", value, min, max));
}
```

## Validation API

On all Validations you can use `and` and `or` methods to combine them into one Validation.

### Usage example

Example below shows how to achieve validation that checks if 
(value is 1-2 length string with only digits characters)
or 
(value is predefined 'N/A' value)
or
(value is one of other predefined allowed values).

File: `io.github.emlagowski.validify.CoreValidationsExample`
```java
List<String> allowedValues = Arrays.asList("A", "B", "C");
String allowedValue = "N/A";

Validation<String> validation = allCharactersAreDigits().and(lengthInRange(1, 2))
        .or(is(allowedValue))
        .or(isIn(allowedValues));

ValidationResult validationResult = validation.apply("value that not apply");

validationResult.getMessages().forEach(validationMessage -> System.out.println(validationMessage.getMessage()));
```

Result of above is

```shell script
Not all character are digits in 'value that not apply'
Length of 'value that not apply' not in range [1, 2]
'value that not apply' is not 'N/A'
'value that not apply' is not in [A, B, C]
```

### More examples

For more examples please see `examples` module or tests inside the `validify-core` module.


