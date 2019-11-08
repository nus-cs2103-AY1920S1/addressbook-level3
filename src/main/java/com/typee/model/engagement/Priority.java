package com.typee.model.engagement;

/**
 * Enumeration for prioritising the Engagement class
 */
public enum Priority {
    LOW,
    MEDIUM,
    HIGH,
    NONE;

    private static final String MESSAGE_CONSTRAINTS = "Priority can only be low, medium, high or none!";

    /**
     * Returns the corresponding {@code Priority} value from the input {@code String}.
     *
     * @param priority {@code String} representing priority.
     * @return the corresponding {@code Priority}.
     * @throws IllegalArgumentException if the {@String priority} is invalid.
     */
    public static Priority of(String priority) throws IllegalArgumentException {
        if (priority.equalsIgnoreCase(Priority.LOW.name())) {
            return Priority.LOW;
        } else if (priority.equalsIgnoreCase(Priority.MEDIUM.name())) {
            return Priority.MEDIUM;
        } else if (priority.equalsIgnoreCase(Priority.HIGH.name())) {
            return Priority.HIGH;
        } else if (priority.equalsIgnoreCase(Priority.NONE.name())) {
            return Priority.NONE;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks if the {@code String string} is a valid priority level.
     *
     * @param string priority level.
     * @return true if the {@code String string} represents a valid priority level.
     */
    public static boolean isValid(String string) {
        return string.equalsIgnoreCase("low")
                || string.equalsIgnoreCase("medium")
                || string.equalsIgnoreCase("high")
                || string.equalsIgnoreCase("none");
    }

    /**
     * Returns the constraints that a {@code String} representing a {@code Priority} should adhere to.
     * @return constraint message.
     */
    public static String getMessageConstraints() {
        return MESSAGE_CONSTRAINTS;
    }
}
