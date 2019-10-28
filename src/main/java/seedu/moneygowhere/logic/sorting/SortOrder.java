package seedu.moneygowhere.logic.sorting;

/**
 * Specifies a sort order.
 */
public enum SortOrder {
    ASCENDING("Ascending"),
    DESCENDING("Descending");

    private final String identifier;

    SortOrder(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return identifier;
    }
}
