package seedu.ichifund.model.budget;

import static seedu.ichifund.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.ichifund.model.Amount;
import seedu.ichifund.model.Description;

/**
 * Represents a Budget in the fund book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Budget {

    // Identity fields
    private final Description description;
    private final Amount amount;

    /**
     * Every field must be present and not null.
     */
    public Budget(Description description, Amount amount) {
        requireAllNonNull(description, amount);
        this.description = description;
        this.amount = amount;
    }

    public Description getDescription() {
        return description;
    }

    public Amount getAmount() {
        return amount;
    }

    /**
     * Returns true if both budgets have the same description.
     * This defines a weaker notion of equality between two budgets.
     */
    public boolean isSameBudget(Budget otherBudget) {
        if (otherBudget == this) {
            return true;
        }

        return otherBudget != null && otherBudget.getDescription().equals(getDescription());
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
                && otherBudget.getAmount().equals(getAmount());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, amount);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" Amount: ")
                .append(getAmount());
        return builder.toString();
    }

}
