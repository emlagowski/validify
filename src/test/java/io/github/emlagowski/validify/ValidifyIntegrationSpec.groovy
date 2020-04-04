package io.github.emlagowski.validify

import spock.lang.Specification
import spock.lang.Unroll

import static io.github.emlagowski.validify.CoreValidations.*

class ValidifyIntegrationSpec extends Specification {

    @Unroll
    def 'Three validations connected with AND and OR result with correct result for #value'() {
        when:
        def validation = (andFirst & andSecond) | orThird
        def result = validation.apply(value)

        then:
        result.isSuccess() == expectedSuccess
        result.messages.size() == messagesCount

        where:
        andFirst            | andSecond                | orThird   | value    || expectedSuccess | messagesCount
        lengthIsEqual(2)    | allCharactersAreDigits() | isEmpty() | "asd"    || false           | 3
        lengthIsEqual(2)    | allCharactersAreDigits() | isEmpty() | "123"    || false           | 2
        lengthIsEqual(2)    | allCharactersAreDigits() | isEmpty() | "12"     || true            | 1
        lengthInRange(1, 2) | isIn(["a", "xd"])        | isEmpty() | "12"     || false           | 2
        lengthInRange(1, 2) | isIn(["a", "xd"])        | isEmpty() | "a"      || true            | 1
        lengthInRange(1, 2) | isIn(["a", "xd"])        | isEmpty() | "aaa"    || false           | 3
        lengthInRange(1, 2) | isIn(["a", "xd"])        | isEmpty() | "xd"     || true            | 1
        lengthInRange(1, 2) | isIn(["a", "xd"])        | isEmpty() | "xdxdxd" || false           | 3
        matches("ABC\\d*")  | lengthInRange(3, 6)      | is("ble") | "ABC123" || true            | 1
        matches("ABC\\d*")  | lengthInRange(3, 6)      | is("ble") | "ABC13"  || true            | 1
        matches("ABC\\d*")  | lengthInRange(3, 6)      | is("ble") | "ABC1"   || true            | 1
        matches("ABC\\d*")  | lengthInRange(3, 6)      | is("ble") | "ABC1"   || true            | 1
        matches("ABC\\d*")  | lengthInRange(3, 6)      | is("ble") | "ABC"    || true            | 1
        matches("ABC\\d*")  | lengthInRange(3, 6)      | is("B")   | "B"      || true            | 2
        isNotEmpty()        | allCharactersAreDigits() | is("A")   | ""       || false           | 2
        isNotEmpty()        | allCharactersAreDigits() | is("A")   | "A"      || true            | 1
        isNotEmpty()        | allCharactersAreDigits() | is("A")   | "123"    || true            | 1
    }

}
