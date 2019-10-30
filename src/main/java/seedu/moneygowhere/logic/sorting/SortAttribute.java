package seedu.moneygowhere.logic.sorting;

/**
 * Attributes for sorting.
 */
public enum SortAttribute {
    NAME("Name"),
    COST("Cost"),
    DATE("Date"),
    DEADLINE("Deadline"),
    REMARK("Remark"),
    REMINDER_MESSAGE("Message")
  
    private final String identifier;

    SortAttribute(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return identifier;
    }
}
