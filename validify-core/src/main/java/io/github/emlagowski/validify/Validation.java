package io.github.emlagowski.validify;

import java.util.function.Function;

@FunctionalInterface
public interface Validation<T> extends Function<T, ValidationResult> {

    default Validation<T> and(Validation<T> other) {
        return (T value) -> {
            ValidationResult thisResult = this.apply(value);
            ValidationResult otherResult = other.apply(value);
            return thisResult.and(otherResult);
        };
    }

    default Validation<T> or(Validation<T> other) {
        return (T value) -> {
            ValidationResult thisResult = this.apply(value);
            ValidationResult otherResult = other.apply(value);
            return thisResult.or(otherResult);
        };
    }

}
