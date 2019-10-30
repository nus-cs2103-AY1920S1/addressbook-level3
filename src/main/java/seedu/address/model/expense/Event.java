package seedu.address.model.expense;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.budget.Budget;
import seedu.address.model.category.Category;
import seedu.address.model.expense.util.UniqueIdentifierGenerator;

/**
 * Represents an Event in MooLah.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {

    private final Description description;
    private final Price price;
    private final Timestamp timestamp;
    private final Category category;
    private Description budgetName;

    /**
     * Every field must be present and not null.
     */
    public Event(Description description, Price price, Category category, Timestamp timestamp) {
        requireAllNonNull(description, price, timestamp);
        this.description = description;
        this.price = price;
        this.category = category;
        this.timestamp = timestamp;
        this.budgetName = null;
    }

    public Event(Description description, Price price, Category category,
                 Timestamp timestamp, Description budgetName) {
        requireAllNonNull(description, price, timestamp);
        this.description = description;
        this.price = price;
        this.category = category;
        this.timestamp = timestamp;
        this.budgetName = budgetName;
    }

    public Description getDescription() {
        return description;
    }

    public Price getPrice() {
        return price;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Category getCategory() {
        return category;
    }

    public Description getBudgetName() {
        return budgetName;
    }

    public void setBudget(Budget budget) {
        budgetName = budget.getDescription();
    }

    /**
     * Returns true if both events of the same description have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getDescription().equals(getDescription())
                && (otherEvent.getPrice().equals(getPrice()))
                && (otherEvent.getTimestamp().equals(getTimestamp()));
    }

    /**
     * Returns true if both events have the same identity and data fields.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getDescription().equals(getDescription())
                && otherEvent.getPrice().equals(getPrice())
                && otherEvent.getCategory().equals(getCategory())
                && (otherEvent.getTimestamp().equals(getTimestamp()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, price, timestamp, category);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Description: ")
                .append(getDescription())
                .append(" Price: ")
                .append(getPrice())
                .append(" Category: ")
                .append(getCategory())
                .append(" Timestamp: ")
                .append(getTimestamp());
        return builder.toString();
    }

    /**
     * Converts the transpired Event into its corresponding Expense.
     * @return The Event's corresponding Expense.
     */
    public Expense convertToExpense() {
        Expense convertedExpense = new Expense(description, price, category,
                timestamp, budgetName, UniqueIdentifierGenerator.generateRandomUniqueIdentifier());
        return convertedExpense;
    }
}
