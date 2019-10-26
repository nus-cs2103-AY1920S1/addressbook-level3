package seedu.address.logic.parser;

/**
 * A class for all flags users input to find books
 */
public enum Flag {
    AVAILABLE, LOANED, OVERDUE;

    public static final String MESSAGE_CONSTRAINTS = "The only available flags are\n"
            + "-available\n"
            + "-loaned\n"
            + "-overdue";
}
