package seedu.jarvis.model.planner;

public enum Frequency {
    /**
     * Values that frequency can take
     */
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY;

    private static final String MESSAGE_CONSTRAINTS = "Frequency levels can only have the following values:\n" +
                                                        "'daily', 'weekly', 'monthly' or 'yearly'";


}
