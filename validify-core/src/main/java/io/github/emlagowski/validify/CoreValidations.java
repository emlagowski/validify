package io.github.emlagowski.validify;

import java.util.Collection;
import java.util.Optional;

public class CoreValidations {

    private CoreValidations() {
        // utility class
    }

    public static Validation<String> lengthIsEqual(int length) {
        return value -> Optional.ofNullable(value)
                .filter(v -> v.length() == length)
                .map(v -> ValidationResult.valid())
                .orElseGet(() -> ValidationResult.invalid("Length of '%s' not equal to %d", value, length));
    }

    public static Validation<String> lengthInRange(int min, int max) {
        return value -> Optional.ofNullable(value)
                .filter(v -> v.length() >= min && v.length() <= max)
                .map(v -> ValidationResult.valid())
                .orElseGet(() -> ValidationResult.invalid("Length of '%s' not in range [%d, %d]", value, min, max));
    }

    public static Validation<String> matches(String pattern) {
        return value -> Optional.ofNullable(value)
                .filter(v -> v.matches(pattern))
                .map(v -> ValidationResult.valid())
                .orElseGet(() -> ValidationResult.invalid("'%s' does not match '%s' pattern", value, pattern));
    }

    public static Validation<String> allCharactersAreDigits() {
        return value -> Optional.ofNullable(value)
                .filter(v -> v.chars().allMatch(Character::isDigit))
                .map(v -> ValidationResult.valid())
                .orElseGet(() -> ValidationResult.invalid("Not all character are digits in '%s'", value));
    }

    public static Validation<String> isEmpty() {
        return value -> Optional.ofNullable(value)
                .filter(String::isEmpty)
                .map(v -> ValidationResult.valid())
                .orElseGet(() -> ValidationResult.invalid("'%s' not empty", value));
    }

    public static Validation<String> isNotEmpty() {
        return value -> Optional.ofNullable(value)
                .filter(v -> !v.isEmpty())
                .map(v -> ValidationResult.valid())
                .orElseGet(() -> ValidationResult.invalid("'%s' is not empty", value));
    }

    public static Validation<String> is(String otherValue) {
        return value -> Optional.ofNullable(value)
                .filter(v -> v.equals(otherValue))
                .map(v -> ValidationResult.valid())
                .orElseGet(() -> ValidationResult.invalid("'%s' is not '%s'", value, otherValue));
    }

    public static Validation<String> isIn(Collection<String> values) {
        return value -> Optional.ofNullable(value)
                .filter(values::contains)
                .map(v -> ValidationResult.valid())
                .orElseGet(() -> ValidationResult.invalid("'%s' is not in %s", value, values));
    }

    public static Validation<String> startsWith(String startingValue) {
        return value -> Optional.ofNullable(value)
                .filter(v -> v.startsWith(startingValue))
                .map(v -> ValidationResult.valid())
                .orElseGet(() -> ValidationResult.invalid("'%s' does not start with '%s'", value, startingValue));
    }

    public static Validation<String> endsWith(String endingValue) {
        return value -> Optional.ofNullable(value)
                .filter(v -> v.endsWith(endingValue))
                .map(v -> ValidationResult.valid())
                .orElseGet(() -> ValidationResult.invalid("'%s' does not end with '%s'", value, endingValue));
    }
}
