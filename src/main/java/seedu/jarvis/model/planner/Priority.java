package seedu.jarvis.model.planner;


public enum Priority {
    /**
     * Values that a priority of a task can take
     */
        HIGH,
        MED,
        LOW;

    public static final String MESSAGE_CONSTRAINTS = "Priority levels can only have the following values:\n" +
                                                        "'high', 'med' or 'low'";

    /**
     * Returns if a given test is a valid Priority level
     * @param test the string in question
     * @return true if it is a valid Priority level, and false if it is not
     */
    public static boolean isValidPriority(String test) {
        return test.equals("high") || test.equals("med") || test.equals("low");
    }

}


