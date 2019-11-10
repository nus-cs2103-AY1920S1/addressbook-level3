package budgetbuddy.model.transaction;

import static budgetbuddy.commons.util.AppUtil.getDateFormatter;
import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import budgetbuddy.model.attributes.Amount;
import budgetbuddy.model.attributes.Category;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;

/**
 * Represents a Transaction in a TransactionList.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Transaction {

    private Direction direction;
    private Amount amount;
    private Description description;
    private Set<Category> categories = new HashSet<>();
    private LocalDate date;

    /**
     * Every field must be present and not null.
     */
    public Transaction(Direction direction, Amount amount, Description description, LocalDate date,
                       Category... categories) {
        requireAllNonNull(date, amount, direction, description);
        this.direction = direction;
        this.amount = amount;
        this.description = description;
        this.categories.addAll(Arrays.asList(categories));
        this.date = date;

    }

    /**
     * Constructor that allows categories to be entered as a @code{@literal List<Category>}
     */
    public Transaction(LocalDate date, Amount amount, Direction direction, Description description,
                       Set<Category> categories) {
        requireAllNonNull(date, amount, direction, description);
        this.direction = direction;
        this.amount = amount;
        this.description = description;
        this.categories.addAll(categories);
        this.date = date;
    }

    public Direction getDirection() {
        return direction;
    }

    public Amount getAmount() {
        return amount;
    }

    public Description getDescription() {
        return description;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public LocalDate getLocalDate() {
        return date;
    }

    /**
     * Returns true if both Transactions have all the same fields (date, amount, description, categories).
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Transaction)) {
            return false;
        }

        Transaction otherTransaction = (Transaction) other;
        return otherTransaction.getLocalDate().equals(getLocalDate())
                && otherTransaction.amount.equals(amount)
                && otherTransaction.direction.equals(direction)
                && otherTransaction.description.equals(description)
                && otherTransaction.categories.equals(categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, amount, direction, description, categories);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Amount: ")
                .append(getAmount())
                .append(" ")
                .append(direction.toString())
                .append(" on ")
                .append(getLocalDate().format(getDateFormatter()))
                .append(" Description: ")
                .append(getDescription())
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }
}
