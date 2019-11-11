package seedu.address.model.finance.budget;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import seedu.address.model.finance.attributes.Amount;

/**
 * Represents a budget in the finance log.
 */
public class Budget {

    public static final String MESSAGE_BUDGETYPE_CONSTRAINTS =
            "Allowed budget types: <met>, <cat>, <place>";

    // Basic fields associated with a budget
    protected final Amount amount;
    protected final Date startDate;
    protected final Date endDate;
    protected final String budgetType; // either met, cat or place
    protected final String budgetTypeValue;

    public Budget(Amount amount, Date startDate, Date endDate,
                  String budgetType, String budgetTypeValue) {
        requireAllNonNull(amount, startDate, endDate,
                budgetType);
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budgetType = budgetType;
        this.budgetTypeValue = budgetTypeValue;
    }

    public Amount getAmount() {
        return amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getBudgetType() {
        return budgetType;
    }

    public String getBudgetTypeValue() {
        return budgetTypeValue;
    }

    /**
     * Only budget types 'met', 'cat', 'place' are allowed
     */
    public static boolean isValidBudgetType(String budgetType) {
        String trimmedBudgetType = budgetType.trim();
        switch (trimmedBudgetType) {
        case "all":
        case "met":
        case "cat":
        case "place":
            return true;
        default:
            return false;
        }
    }

    /**
     * Converts a date to string
     */
    public static String toStringDate(Date date) {
        SimpleDateFormat validFormat = new SimpleDateFormat("dd-MM-yyyy");
        return validFormat.format(date);
    }

    /**
     * Returns true if both budgets have the same fields.
     */
    public boolean isSameBudget(Budget otherBudget) {
        if (otherBudget == this) {
            return true;
        }

        String thisBudgetTypeValue = getBudgetTypeValue();
        String otherBudgetTypeValue = otherBudget.getBudgetTypeValue();
        boolean budgetTypeValueEquals;
        if (thisBudgetTypeValue == null && otherBudgetTypeValue == null) {
            budgetTypeValueEquals = true;
        } else if (thisBudgetTypeValue == null || otherBudgetTypeValue == null) {
            budgetTypeValueEquals = false;
        } else {
            budgetTypeValueEquals = otherBudget.getBudgetTypeValue().equals(getBudgetTypeValue());
        }

        return otherBudget != null
                && otherBudget.getAmount().equals(getAmount())
                && (otherBudget.getStartDate().equals(getStartDate())
                && otherBudget.getEndDate().equals(getEndDate()))
                && otherBudget.getBudgetType().equals(getBudgetType())
                && budgetTypeValueEquals;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(this.getClass() == other.getClass())) {
            return false;
        }

        Budget otherLogEntry = (Budget) other;
        return otherLogEntry.getAmount().equals(getAmount())
                && otherLogEntry.getStartDate().equals(getStartDate())
                && otherLogEntry.getEndDate().equals(getEndDate())
                && otherLogEntry.getBudgetType().equals(getBudgetType())
                && otherLogEntry.getBudgetTypeValue().equals(getBudgetTypeValue());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Amount: ")
                .append(getAmount())
                .append(" Start Date: ")
                .append(toStringDate(getStartDate()))
                .append(" End Date: ")
                .append(toStringDate(getEndDate()))
                .append(" Type: ")
                .append(getBudgetType())
                .append(" Value: ")
                .append(getBudgetTypeValue());
        return builder.toString();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(amount, startDate, endDate, budgetType, budgetTypeValue);
    }

}
