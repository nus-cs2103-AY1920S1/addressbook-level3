package seedu.address.model.record;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.model.calendar.DateTime;

/**
 * Represents a record in the record book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Record {

    private final DateTime dateTime;

    public Record(DateTime dateTime) {
        requireNonNull(dateTime);
        this.dateTime = dateTime;
    }

    /**
     * Returns true if both records have the same identity.
     */
    public boolean isSameRecord(Record otherRecord) {
        if (otherRecord == this) {
            return true;
        }

        return otherRecord != null
            && otherRecord.dateTime.equals(dateTime)
            && otherRecord.getClass().equals(this.getClass());
    }

    /**
     * Returns true if both persons have the same identity and data fields. This defines a stronger notion of equality
     * between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Record)) {
            return false;
        }

        Record otherRecord = (Record) other;
        return otherRecord.dateTime.equals(dateTime)
            && otherRecord.getClass().equals(this.getClass());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(dateTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("at DateTime: ")
            .append(dateTime);
        return builder.toString();
    }
}
