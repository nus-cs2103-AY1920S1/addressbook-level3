package seedu.address.model.entity;

/**
 * Represents an Issue type(the type of issue raised by the user).
 * Issue type can be of the form Todo, Deadline, Event.
 */
public enum IssueType {
    DEADLINE("Deadline"),
    EVENT("Event"),
    TODO("Todo");

    public static final String MESSAGE_CONSTRAINTS = "IssueType must be one of the following: Todo, Deadline, Event";
    private final String stringFormat;
    IssueType(String stringFormat) {
        this.stringFormat = stringFormat;
    }

    @Override
    public String toString() {
        return this.stringFormat;
    }

    /**
     * Returns if a given string is a valid issueType.
     *
     * @param issueType String of issueType.
     * @return boolean whether issueType is an instance of IssueType.
     */
    public static boolean isValidIssueType(String issueType) {
        try {
            IssueType validIssueType = IssueType.valueOf(issueType);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
