package seedu.ichifund.model.transaction;

/**
 * Encapsulates the type of a {@code Transaction} object.
 */
public enum TransactionType {
    EXPENDITURE("-"),
    INCOME("+");

    /** Symbol associated with a particular TaskType. */
    private String typeSymbol;

    /**
     * Constructor for TaskType objects.
     *
     * @param typeSymbol Symbol associated with the TaskType.
     */
    TransactionType(String typeSymbol) {
        this.typeSymbol = typeSymbol;
    }

    @Override
    public String toString() {
        return typeSymbol;
    }
}
