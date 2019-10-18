package seedu.jarvis.model.planner;

/**
 * Represents the frequency of a task
 */
public enum Frequency {
    /**
     * Values that frequency can take
     */
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY;

    public static final String MESSAGE_CONSTRAINTS = "Frequency levels can only have the following values:\n"
                                                        + "'daily', 'weekly', 'monthly' or 'yearly'";
    public static final String FREQ_DAILY = "daily";
    public static final String FREQ_WEEKLY = "weekly";
    public static final String FREQ_MONTHLY = "monthly";
    public static final String FREQ_YEARLY = "yearly";


    /**
     * Checks if the frequency level given by user is valid
     * @param test frequency level provided by user
     * @return true if it is "daily", "weekly", "monthly" or "yearly",
     * and false if it is anything else
     */
    public static boolean isValidFrequency(String test) {
        return test.equals(FREQ_DAILY) || test.equals(FREQ_WEEKLY)
                || test.equals(FREQ_MONTHLY) || test.equals(FREQ_YEARLY);
    }
}
