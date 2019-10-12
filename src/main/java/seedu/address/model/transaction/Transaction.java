package seedu.address.model.transaction;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.Direction;
import seedu.address.model.transaction.stub.Category;
import seedu.address.model.transaction.stub.Description;



/**
 * Represents a Transaction in a TransactionList.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Transaction {

    private final Date date;
    private final Amount amount;
    private final Direction direction;
    private final Description description;
    private final Set<Category> categories = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Transaction(Date date, Amount amount, Direction direction, Description description,
                       Set<Category> categories) {
        requireAllNonNull(date, amount, direction, description, categories);
        this.date = date;
        this.amount = amount;
        this.direction = direction;
        this.amount = amount;
        this.description = description;
        this.categories.addAll(categories);
    }

    public Date getDate() {
        return date;
    }

    public Amount getAmount() {
        return amount;
    }

    public Direction getDirection() {
        return direction;
    }

    public Description getDescription() {
        return description;
    }

    public Set<Category> getCategories() {
        return categories;
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
        return otherTransaction.getDate().equals(date)
                && otherTransaction.getAmount().equals(amount)
                && otherTransaction.getDirection().equals(direction)
                && otherTransaction.getDescription().equals(description)
                && otherTransaction.getCategories().equals(categories);

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
                .append(direction.toString())
                .append(" on ")
                .append(getDate())
                .append(" Description: ")
                .append(getDescription())
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }
}
