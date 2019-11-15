package dukecooks.model.health.components;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a Person in Duke Cooks.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Record {

    // Data fields
    private final Set<Remark> remarks = new HashSet<>();
    private final Type type;
    private final Value value;
    private final Timestamp timestamp;

    /**
     * Every field must be present and not null.
     */
    public Record(Type type, Value value, Timestamp timestamp, Set<Remark> remarks) {
        this.type = type;
        this.value = value;
        this.timestamp = timestamp;
        this.remarks.addAll(remarks);
    }

    public Type getType() {
        return type;
    }

    public Value getValue() {
        return value;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * Returns an immutable remark set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Remark> getRemarks() {
        return Collections.unmodifiableSet(remarks);
    }

    /**
     * Returns true if both records of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two records.
     */
    public boolean isSameRecord(Record otherRecord) {
        if (otherRecord == this) {
            return true;
        }

        return otherRecord != null
                && otherRecord.equals(this);
    }

    /**
     * Returns true if both records have the same identity and data fields.
     * This defines a stronger notion of equality between two records.
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
        return otherRecord.getType().equals(getType())
                && otherRecord.getTimestamp().equals(getTimestamp());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(type, timestamp);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Type: " + getType())
                .append(" Value: " + getValue())
                .append(" Timestamp: " + getTimestamp())
                .append(" Remarks: ");
        getRemarks().forEach(builder::append);
        return builder.toString();
    }

}
