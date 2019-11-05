package seedu.address.logic.parser;

/**
 * A class for all flags users input to find books
 */
public enum Flag {
    AVAILABLE, LOANED, OVERDUE, ALL;

    public static final String MESSAGE_CONSTRAINTS = "The only available flags are\n"
            + "-available\n"
            + "-loaned\n"
            + "-overdue\n"
            + "-all";

    public static final String FIND_FLAGS_MESSAGE_CONSTRAINTS = "The only available flags for find command are\n"
            + "-available\n"
            + "-loaned\n"
            + "-overdue";

    public static final String RETURN_AND_RENEW_FLAG_MESSAGE_CONSTRAINTS =
            "The only available flag for %1$s command is\n"
            + "-all";
}
