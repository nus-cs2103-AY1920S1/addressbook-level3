package seedu.sugarmummy.model.achievements;

/**
 * Enumeration representing various types of units duration can be given in.
 */
public enum DurationUnit {
    DAY, WEEK, MONTH, YEAR;

    public String toLowerCase() {
        return this.toString().toLowerCase();
    }
}
