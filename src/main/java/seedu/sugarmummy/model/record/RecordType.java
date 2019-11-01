package seedu.sugarmummy.model.record;

/**
 * Represents different possible types of records.
 */
public enum RecordType {

    BLOODSUGAR, BMI;

    public static final String MESSAGE_CONSTRAINTS =
            "Record type not recognized";
}
