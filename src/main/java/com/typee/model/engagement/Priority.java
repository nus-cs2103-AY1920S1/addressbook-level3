package com.typee.model.engagement;

/**
 * Enumeration for prioritising the Engagement class
 */
public enum Priority {
    LOW,
    MEDIUM,
    HIGH;

    public static Priority of(String priority) throws IllegalArgumentException {
        if (priority.equalsIgnoreCase(Priority.LOW.name())) {
            return Priority.LOW;
        } else if (priority.equalsIgnoreCase(Priority.MEDIUM.name())) {
            return Priority.MEDIUM;
        } else if (priority.equalsIgnoreCase(Priority.HIGH.name())) {
            return Priority.HIGH;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static boolean isValid(String string) {
        return string.equalsIgnoreCase("low")
                || string.equalsIgnoreCase("medium")
                || string.equalsIgnoreCase("high");
    }

    public static String getMessageConstraints() {
        return "Priority can only be low, medium or high!";
    }
}
