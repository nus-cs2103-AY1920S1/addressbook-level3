package seedu.address.model.Entity;

public enum IssueType {
    TODO("Todo"),
    DEADLINE("Deadline"),
    EVENT("Event");

    private final String stringFormat;

    private IssueType(String stringFormat) {
        this.stringFormat = stringFormat;
    }

    @Override
    public String toString() {
        return this.stringFormat;
    }
}
