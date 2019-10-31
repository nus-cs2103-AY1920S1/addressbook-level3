package seedu.address.model.earnings;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.classid.ClassId;

/**
 * Represents a Tutor's earnings.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Earnings {

    private static Amount totalEarnings = new Amount("00.00");

    // Identity fields
    private final Date date;
    private final ClassId classId;
    private final Amount amount;
    private final Type type;

    /**
     * Every field must be present and not null.
     */
    public Earnings(Date date, ClassId classId, Amount amount, Type type) {
        requireAllNonNull(date, classId, amount);
        this.date = date;
        this.classId = classId;
        this.amount = amount;

        this.type = type;
        addToTotalEarnings();
    }

    public Date getDate() {
        return date;
    }

    public ClassId getClassId() {
        return classId;
    }

    public Amount getAmount() {
        return amount;
    }

    public Type getType() {
        return type;
    }

    public void addToTotalEarnings() {
        totalEarnings = totalEarnings.addAmount(this.amount);
    }

    public static Amount getTotalEarnings() {
        return totalEarnings;
    }
    /**
     * Returns true if both earnings of the same date and classId have an identity field that is the same.
     * This defines a weaker notion of equality between two earnings.
     */
    public boolean isSameEarnings(Earnings otherEarnings) {
        if (otherEarnings == this) {
            return true;
        }

        return otherEarnings != null
                && otherEarnings.getDate().equals(getDate())
                && otherEarnings.getClassId().equals(getClassId())
                && otherEarnings.getType().equals(getType());
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
                && otherEarnings.getClassId().equals(getClassId())
                && otherEarnings.getAmount().equals(getAmount())
                && otherEarnings.getType().equals(getType());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date, classId, amount);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Date: ")
                .append(getDate())
                .append(" Type: ")
                .append(getType())
                .append(" ClassId: ")
                .append(getClassId())
                .append(" Amount: ")
                .append(getAmount());
        return builder.toString();
    }
}
