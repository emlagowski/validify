package io.github.emlagowski.validify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ValidationResult {
    private final boolean success;
    private final Collection<ValidationMessage> messages;

    public static ValidationResult valid() {
        return new ValidationResult(true, Collections.emptyList());
    }

    public static ValidationResult invalid(String message, Object... args) {
        String formattedMessage = String.format(message, args);
        return new ValidationResult(false, Collections.singleton(new ValidationMessage(formattedMessage)));
    }

    public ValidationResult(boolean success, Collection<ValidationMessage> messages) {
        this.success = success;
        this.messages = messages;
    }

    public Collection<ValidationMessage> getMessages() {
        return Collections.unmodifiableCollection(messages);
    }

    public boolean isSuccess() {
        return success;
    }

    public ValidationResult and(ValidationResult otherResult) {
        boolean combineSuccess = this.success & otherResult.success;
        return new ValidationResult(combineSuccess, merge(this.messages, otherResult.messages));
    }

    public ValidationResult or(ValidationResult otherResult) {
        boolean combineSuccess = this.success | otherResult.success;
        return new ValidationResult(combineSuccess, merge(this.messages, otherResult.messages));
    }

    private static  Collection<ValidationMessage> merge(Collection<ValidationMessage> first, Collection<ValidationMessage> second) {
        ArrayList<ValidationMessage> results = new ArrayList<>();
        results.addAll(first);
        results.addAll(second);
        return Collections.unmodifiableCollection(results);
    }
 }
