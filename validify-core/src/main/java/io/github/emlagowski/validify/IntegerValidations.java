package io.github.emlagowski.validify;

import java.util.Optional;

public class IntegerValidations {

    private IntegerValidations() {
        // utility class
    }

    public static Validation<Integer> valueInRange(int min, int max) {
        return value -> Optional.ofNullable(value)
                .filter(v -> min <= v && v <= max)
                .map(v -> ValidationResult.valid())
                .orElseGet(() -> ValidationResult.invalid("Value '%s' should be in the range [%d, %d]", value, min, max));
    }

}
