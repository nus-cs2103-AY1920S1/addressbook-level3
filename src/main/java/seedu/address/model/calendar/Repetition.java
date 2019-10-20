package seedu.address.model.calendar;

/**
 * Represents repetition of a reminder.
 */
public enum Repetition {
    /**
     * No repetition.
     */
    Once,
    /**
     * Repeats everyday.
     */
    Daily,
    /**
     * Repeats everyweek.
     */
    Weekly;

    public static final String MESSAGE_CONSTRAINTS = "Repetition can only take value 'none', 'daily' or 'weekly'" +
            "(case insensitive)";

    /**
     * Returns true if a given string is a valid repetition.
     */
    public static boolean isValidRepetition(String repetition) {
        String lowerRepetition = repetition.toLowerCase();
        return lowerRepetition.equals("none") || lowerRepetition.equals("daily") || lowerRepetition.equals("weekly");
    }

    /**
     * Returns a Repetition corresponding to the given string.
     */
    public static Repetition of(String repetition) {
        String lowerRepetition = repetition.toLowerCase();
        switch (lowerRepetition) {
        case "none":
            return Repetition.Once;
        case "daily":
            return Repetition.Daily;
        case "weekly":
            return Repetition.Weekly;
        default:
            return Repetition.Once;
        }
    }

}
