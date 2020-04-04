package io.github.emlagowski.validify

import spock.lang.Specification
import spock.lang.Unroll

class ValueInRangeTest extends Specification {

    @Unroll
    def 'validation should check if value #value is between #min and #max and result with success=#expectedSuccess'() {
        when:
        def result = IntegerValidations.valueInRange(min, max).apply(value)

        then:
        expectedSuccess == result.success
        messages == result.messages.size()

        where:
        value | min | max || expectedSuccess | messages
        0     | 0   | 1   || true            | 0
        1     | 0   | 1   || true            | 0
        2     | 0   | 1   || false           | 1
        2     | 100 | 200 || false           | 1
        -3    | -5  | 5   || true            | 0
        null  | -5  | 5   || false           | 1
    }

    def 'return message has all important information'() {
        when:
        def result = IntegerValidations.valueInRange(1, 2).apply(15)

        then:
        result.messages.size() == 1
        result.messages.first().message.contains("1")
        result.messages.first().message.contains("2")
        result.messages.first().message.contains("15")
    }

}
