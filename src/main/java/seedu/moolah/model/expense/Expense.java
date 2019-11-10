package seedu.moolah.model.expense;

import static seedu.moolah.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.menu.MenuItem;

/**
 * Represents a Expense in MooLah.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Expense {

    // identity fields
    private final UniqueIdentifier uniqueIdentifier;
    // data fields
    private final Description description;
    private final Price price;
    private final Timestamp timestamp;
    private Description budgetName;
    private final Category category;

    /**
     * Every field must be present and not null.
     */
    public Expense(Description description, Price price, Category category, UniqueIdentifier uniqueIdentifier) {
        requireAllNonNull(description, price, category, uniqueIdentifier);
        this.description = description;
        this.price = price;
        this.uniqueIdentifier = uniqueIdentifier;
        this.category = category;
        this.timestamp = Timestamp.getCurrentTimestamp();
        this.budgetName = null;
    }

    public Expense(Description description, Price price, Category category,
                   Timestamp timestamp, UniqueIdentifier uniqueIdentifier) {
        requireAllNonNull(description, price, category, uniqueIdentifier);
        this.description = description;
        this.price = price;
        this.uniqueIdentifier = uniqueIdentifier;
        this.category = category;
        this.timestamp = timestamp;
        this.budgetName = null;
    }

    public Expense(Description description, Price price, Category category, Timestamp timestamp, Description budgetName,
                   UniqueIdentifier uniqueIdentifier) {
        requireAllNonNull(description, price, category, timestamp, budgetName, uniqueIdentifier);
        this.description = description;
        this.price = price;
        this.uniqueIdentifier = uniqueIdentifier;
        this.category = category;
        this.timestamp = timestamp;
        this.budgetName = budgetName;
    }

    public Expense(MenuItem menuItem, UniqueIdentifier uniqueIdentifier) {
        this(menuItem.getDescription(), menuItem.getPrice(), new Category("FOOD"),
                uniqueIdentifier);
    }

    public Expense(MenuItem menuItem, Timestamp timestamp, UniqueIdentifier uniqueIdentifier) {
        this(menuItem.getDescription(), menuItem.getPrice(), new Category("FOOD"),
                timestamp, uniqueIdentifier);
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

    public UniqueIdentifier getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public Description getBudgetName() {
        return budgetName;
    }

    public void setBudget(Budget budget) {
        this.budgetName = budget.getDescription();
    }

    public void removeBudget() {
        this.budgetName = Budget.DEFAULT_BUDGET_DESCRIPTION;
    }

    public boolean uniqueIdIs(String id) {
        return this.uniqueIdentifier.value.equals(id);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Returns true if both expenses of the same unique identifier.
     * This defines a weaker notion of equality between two expenses.
     */
    public boolean isSameExpense(Expense otherExpense) {
        if (otherExpense == this) {
            return true;
        }

        return otherExpense != null
                && otherExpense.getUniqueIdentifier().equals(getUniqueIdentifier());
    }

    /**
     * Returns true if both expenses have the same unique identifier and data fields.
     * This defines a stronger notion of equality between two expenses.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Expense)) {
            return false;
        }

        Expense otherExpense = (Expense) other;
        return otherExpense.uniqueIdentifier.equals(uniqueIdentifier)
                && otherExpense.description.equals(description)
                && otherExpense.price.equals(price)
                && otherExpense.timestamp.equals(timestamp)
                && otherExpense.category.equals(category)
                && otherExpense.budgetName.equals(budgetName);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, price, timestamp, budgetName, category, uniqueIdentifier);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Description: ")
                .append(getDescription())
                .append(" Price: ")
                .append(getPrice())
                .append(" Date: ")
                .append(getTimestamp())
                .append(" Category: ")
                .append(getCategory())
                .append(" Timestamp: ")
                .append(getTimestamp());
        return builder.toString();
    }
}
