package seedu.address.profile.records;

import java.util.Objects;

import seedu.address.logic.parser.DateParser;

/**
 * Represents a Person in Duke Cooks.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Record {

    // Identity fields
    private final int recordId;

    // Data fields
    private final Type type;
    private final Value value;
    private final Timestamp timestamp;

    /**
     * Every field must be present and not null.
     */
    public Record(Type type, Value value, Timestamp timestamp) {
        this.recordId = Objects.hash(timestamp.timestamp, DateParser.getCurrentTimestamp());
        this.type = type;
        this.value = value;
        this.timestamp = timestamp;
    }

    /**
     * Preassumptions: valid recordId generated and parse from Json.
     */
    public Record(String recordId, Type type, Value value, Timestamp timestamp) {
        this.recordId = Integer.parseInt(recordId);
        this.type = type;
        this.value = value;
        this.timestamp = timestamp;
    }

    public int getRecordId() {
        return recordId;
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
     * Returns true if both records of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two records.
     */
    public boolean isSameRecord(Record otherRecord) {
        if (otherRecord == this) {
            return true;
        }

        return otherRecord != null
                && otherRecord.getRecordId() == getRecordId();
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
        return otherRecord.getRecordId() == getRecordId();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(recordId, type);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getRecordId())
                .append(" Type: " + getType())
                .append(" Value: " + getType() + getValue())
                .append(" Timestamp: " + getTimestamp());
        return builder.toString();
    }

}
