package seedu.guilttrip.model.util;

public enum CategoryType {
    INCOME("Income"),EXPENSE("Expense");

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

    public static CategoryType parse(String stringFreq) {
        String sanitized = stringFreq.trim().toLowerCase();
        if (sanitized.equalsIgnoreCase(EXPENSE.getCatType())) {
            return EXPENSE;
        } else {
            return INCOME;
        }
    }
}
