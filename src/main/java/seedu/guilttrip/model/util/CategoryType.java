package seedu.guilttrip.model.util;

/**
 * Contains the type of categories whether it be Income or Expense.
 */
public enum CategoryType {
    INCOME("Income"), EXPENSE("Expense");

    private final String catType;

    CategoryType(String catType) {
        this.catType = catType;
    }

    public String getCatType() {
        return toString();
    }

    @Override
    public String toString() {
        return catType;
    }

    /**
     * Parses the type of Category specified by the User.
     *
     * @param catType the type Of Category Specified
     * @return the enum of category Type.
     */
    public static CategoryType parse(String catType) {
        String sanitized = catType.trim().toLowerCase();
        if (sanitized.equalsIgnoreCase(EXPENSE.getCatType())) {
            return EXPENSE;
        } else {
            return INCOME;
        }
    }
}
