package seedu.address.model.earnings;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Tutor's earnings.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Earnings {

    // Identity fields
    private final Date date;
    private final Module module;
    private final Amount amount;

    /**
     * Every field must be present and not null.
     */
    public Earnings(Date date, Module module, Amount amount) {
        requireAllNonNull(date, module, amount);
        this.date = date;
        this.module = module;
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public Module getModule() {
        return module;
    }

    public Amount getAmount() {
        return amount;
    }

    /**
     * Returns true if both earnings of the same date and module have an identity field that is the same.
     * This defines a weaker notion of equality between two earnings.
     */
    public boolean isSameEarnings(Earnings otherEarnings) {
        if (otherEarnings == this) {
            return true;
        }

        return otherEarnings != null
                && otherEarnings.getDate().equals(getDate())
                && otherEarnings.getModule().equals(getModule());
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

        if (!(other instanceof Earnings)) {
            return false;
        }

        Earnings otherEarnings = (Earnings) other;
        return otherEarnings.getDate().equals(getDate())
                && otherEarnings.getModule().equals(getModule())
                && otherEarnings.getAmount().equals(getAmount());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date, module, amount);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Date: ")
                .append(getDate())
                .append(" Module: ")
                .append(getModule())
                .append(" Amount: ")
                .append(getAmount());
        return builder.toString();
    }
}
