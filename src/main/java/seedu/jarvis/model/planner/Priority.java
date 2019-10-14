package seedu.jarvis.model.planner;


public enum Priority {
    /**
     * Values that a priority of a task can take
     */
        HIGH,
        MED,
        LOW;

    private static final String MESSAGE_CONSTRAINTS = "Priority levels can only have the following values:\n" +
                                                        "'high', 'med' or 'low'";

}


