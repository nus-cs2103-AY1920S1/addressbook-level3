package seedu.ichifund.model.budget;

import static seedu.ichifund.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.ichifund.model.Description;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Category;

/**
 * Represents a Budget in the fund book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Budget {

    // Identity fields
    private final Description description;
    private final Amount amount;

    // Constraints fields
    private final Month month;
    private final Year year;
    private final Category category;

    /**
     * Description and amount must be present and not null.
     * Month and year should either both be present or absence.
     */
    public Budget(Description description, Amount amount, Month month, Year year, Category category) {
        requireAllNonNull(description, amount);
        assert((month == null && year == null) || month != null && year != null);
        this.description = description;
        this.amount = amount;
        this.month = month;
        this.year = year;
        this.category = category;
    }

    public Description getDescription() {
        return description;
    }

    public Amount getAmount() {
        return amount;
    }

    public Month getMonth() {
        return month;
    }

    public Year getYear() {
        return year;
    }

    public Category getCategory() {
        return category;
    }

    /**
     * Returns true if both budgets have the same description.
     * This defines a weaker notion of equality between two budgets.
     */
    public boolean isSameBudget(Budget otherBudget) {
        if (otherBudget == this) {
            return true;
        }

        return otherBudget != null && otherBudget.getDescription().equals(getDescription())
                && ((otherBudget.getMonth() == null && getMonth() == null)
                || otherBudget.getMonth().equals(getMonth()))
                && ((otherBudget.getYear() == null && getYear() == null)
                || otherBudget.getYear().equals(getYear()));
    }

    /**
     * Returns a string describing the criterion of the budget.
     */
    public String toCriterionString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Applicable to all transactions");

        if (category != null) {
            builder.append(" under ")
                    .append(getCategory());
        }

        if (month != null && year != null) {
            builder.append(" in ")
                    .append(getMonth().wordString())
                    .append(" ")
                    .append(getYear());
        }

        builder.append(".");
        return builder.toString();
    }

    /**
     * Returns true if both budgets have the same identity and data fields.
     * This defines a stronger notion of equality between two budgets.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Budget)) {
            return false;
        }

        Budget otherBudget = (Budget) other;
        return otherBudget.getDescription().equals(getDescription())
                && otherBudget.getAmount().equals(getAmount())
                && ((otherBudget.getMonth() == null && getMonth() == null)
                || otherBudget.getMonth().equals(getMonth()))
                && ((otherBudget.getYear() == null && getYear() == null)
                || otherBudget.getYear().equals(getYear()))
                && ((otherBudget.getCategory() == null && getCategory() == null)
                || otherBudget.getCategory().equals(getCategory()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, amount, month, year, category);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" Amount: ")
                .append(getAmount());

        if (month != null && year != null) {
            builder.append(" Month: ")
                    .append(getMonth())
                    .append(" Year: ")
                    .append(getYear());
        }

        if (category != null) {
            builder.append(" Categories: ")
                    .append(getCategory());
        }

        return builder.toString();
    }

}
