package io.github.emlagowski.validify;

import java.util.Arrays;
import java.util.List;

import static io.github.emlagowski.validify.CoreValidations.allCharactersAreDigits;
import static io.github.emlagowski.validify.CoreValidations.is;
import static io.github.emlagowski.validify.CoreValidations.isIn;
import static io.github.emlagowski.validify.CoreValidations.lengthInRange;

public class CoreValidationsExample {
    public static void main(String[] args) {
        List<String> allowedValues = Arrays.asList("A", "B", "C");
        String allowedValue = "N/A";

        Validation<String> validation = allCharactersAreDigits().and(lengthInRange(1, 2))
                .or(is(allowedValue))
                .or(isIn(allowedValues));

        ValidationResult validationResult = validation.apply("value that not apply");

        validationResult.getMessages().forEach(validationMessage -> System.out.println(validationMessage.getMessage()));
    }
}
