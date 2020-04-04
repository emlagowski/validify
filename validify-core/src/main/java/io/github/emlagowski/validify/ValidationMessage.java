package io.github.emlagowski.validify;

public class ValidationMessage {
    private final String message;

    public ValidationMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
