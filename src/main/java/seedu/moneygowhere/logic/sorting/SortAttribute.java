package seedu.moneygowhere.logic.sorting;

/**
 * Attributes for sorting.
 */
public enum SortAttribute {
    NAME("Name"),
    COST("Cost"),
    DATE("Date"),
    REMARK("Remark");

    private final String identifier;

    SortAttribute(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return identifier;
    }
}
