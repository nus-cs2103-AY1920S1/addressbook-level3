package seedu.jarvis.model.planner.enums;

/**
 * Represents the priority of a task
 */
public enum Priority {
    /**
     * Values that a priority of a task can take
     */
    HIGH,
    MED,
    LOW;

    public static final String MESSAGE_CONSTRAINTS = "Priority levels can only have the following values:\n"
                                                       + "'high', 'medium' or 'low'";
    public static final String PRIORITY_HIGH = "high";
    public static final String PRIORITY_MED = "medium";
    public static final String PRIORITY_LOW = "low";


    /**
     * Returns if a given test is a valid Priority level
     * @param test the string in question
     * @return true if it is a valid Priority level, and false if it is not
     */
    public static boolean isValidPriority(String test) {
        return test.equals(PRIORITY_HIGH) || test.equals(PRIORITY_MED) || test.equals(PRIORITY_LOW);
    }

    @Override
    public String toString() {
        if (this == HIGH) {
            return "High";
        } else if (this == MED) {
            return "Medium";
        } else {
            return "Low";
        }
    }

}


