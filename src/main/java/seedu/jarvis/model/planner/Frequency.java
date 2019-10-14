package seedu.jarvis.model.planner;

public enum Frequency {
    /**
     * Values that frequency can take
     */
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY;

    public static final String MESSAGE_CONSTRAINTS = "Frequency levels can only have the following values:\n" +
                                                        "'daily', 'weekly', 'monthly' or 'yearly'";


    public static boolean isValidFrequency(String test) {
        return test.equals("daily") || test.equals("weekly") || test.equals("monthly") || test.equals("yearly");
    }
}
