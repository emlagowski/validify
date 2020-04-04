package io.github.emlagowski.validify

import spock.lang.Specification
import spock.lang.Unroll

class EndsWithTest extends Specification {

    @Unroll
    def 'validation should check if value #value ends with #startingValue and result with success=#expectedSuccess'() {
        when:
        def result = CoreValidations.endsWith(endingValue).apply(value)

        then:
        expectedSuccess == result.success
        messages == result.messages.size()

        where:
        value    | endingValue || expectedSuccess | messages
        ""       | ""          || true            | 0
        "a"      | "a"         || true            | 0
        "ab"     | "b"         || true            | 0
        "123456" | "456"       || true            | 0
        "132435" | "235"       || false           | 1
        "132435" | ""          || true            | 0
        ""       | "asdqwe"    || false           | 1
    }

    def 'return message has all important information'() {
        when:
        def result = CoreValidations.endsWith("XY").apply("ABCD")

        then:
        result.messages.size() == 1
        result.messages.first().message.contains("XY")
        result.messages.first().message.contains("ABCD")
    }

}
