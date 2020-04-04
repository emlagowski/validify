package io.github.emlagowski.validify

import spock.lang.Specification
import spock.lang.Unroll

class LengthIsEqualTest extends Specification {

    @Unroll
    def 'when #value length is equal to specified #length validation result should be successful=#expectedSuccess'() {
        when:
        def result = CoreValidations.lengthIsEqual(length).apply(value)

        then:
        expectedSuccess == result.success
        messages == result.messages.size()

        where:
        value              | length || expectedSuccess | messages
        ""                 | 0      || true            | 0
        ""                 | 5      || false           | 1
        "1"                | 1      || true            | 0
        "1"                | 0      || false           | 1
        "a"                | 1      || true            | 0
        "a"                | 10     || false           | 1
        "1q2w3e4r5t6y7u8i" | 16     || true            | 0
        "1q2w3e4r5t6y7u8i" | 10     || false           | 1
        "  1         s  "  | 15     || true            | 0
        "  1         s  "  | 12     || false           | 1
    }

    def 'return message has all important information'() {
        when:
        def result = CoreValidations.lengthIsEqual(2).apply("ABCD")

        then:
        result.messages.size() == 1
        result.messages.first().message.contains("2")
        result.messages.first().message.contains("ABCD")
    }

}
