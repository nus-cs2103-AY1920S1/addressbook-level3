package seedu.address.model.transaction;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.Description;
import seedu.address.model.Amount;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Transaction in IchiFund.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Transaction {
    private final Amount amount;
    private final Description description;
    private final Category category;

    /**
     * Every field must be present and not null.
     */
    public Transaction(Description description, Amount amount, Category category) {
        requireAllNonNull(description, amount, category);
        this.amount = amount;
        this.description = description;
        this.category = category;
    }


    public Amount getAmount() {
        return amount;
    }

    public Description getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
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
        return otherTransaction.getDescription().equals(getDescription())
                && otherTransaction.getAmount().equals(getAmount())
                && otherTransaction.getCategory().equals(getCategory());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(amount, description, category);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" Amount: ")
                .append(getAmount())
                .append(" Category: ")
                .append(getCategory());
        return builder.toString();
    }

}
