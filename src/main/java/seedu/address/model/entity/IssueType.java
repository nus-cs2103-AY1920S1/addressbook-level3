package seedu.address.model.entity;

public enum IssueType {
    TODO("Todo"),
    DEADLINE("Deadline"),
    EVENT("Event");

    private final String stringFormat;
    public static final String MESSAGE_CONSTRAINTS = "IssueType must be one of the following: Todo, Deadline, Event";

    private IssueType(String stringFormat) {
        this.stringFormat = stringFormat;
    }

    @Override
    public String toString() {
        return this.stringFormat;
    }

    public static boolean isValidIssueType(String issueType) {
        try {
            IssueType validIssueType = IssueType.valueOf(issueType);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
