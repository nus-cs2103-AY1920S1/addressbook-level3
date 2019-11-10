package seedu.elisa.commons.core.item;

/**
 * Available priority levels for tasks and events.
 */
public enum Priority {
    HIGH, MEDIUM, LOW;
    /**
     * Creates a Priority object from a string.
     * @param priorityString the string that represents the Priority
     * @return the Priority object that is created
     */
    public static Priority fromJson(String priorityString) {
        return valueOf(priorityString.toUpperCase());
    }
}
