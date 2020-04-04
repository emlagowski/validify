package io.github.emlagowski.validify

import spock.lang.Specification
import spock.lang.Unroll

class StartsWithTest extends Specification {

    @Unroll
    def 'validation should check if value #value starts with #startingValue and result with success=#expectedSuccess'() {
        when:
        def result = CoreValidations.startsWith(startingValue).apply(value)

        then:
        expectedSuccess == result.success
        messages == result.messages.size()

        where:
        value    | startingValue || expectedSuccess | messages
        ""       | ""            || true            | 0
        "a"      | "a"           || true            | 0
        "ab"     | "a"           || true            | 0
        "123456" | "123"         || true            | 0
        "132435" | "123"         || false           | 1
        "132435" | ""            || true            | 0
        ""       | "asdqwe"      || false           | 1
    }

    def 'return message has all important information'() {
        when:
        def result = CoreValidations.startsWith("XY").apply("ABCD")

        then:
        result.messages.size() == 1
        result.messages.first().message.contains("XY")
        result.messages.first().message.contains("ABCD")
    }

}
