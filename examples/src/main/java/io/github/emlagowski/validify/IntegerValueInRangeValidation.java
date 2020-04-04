package io.github.emlagowski.validify;

import java.util.Optional;

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
