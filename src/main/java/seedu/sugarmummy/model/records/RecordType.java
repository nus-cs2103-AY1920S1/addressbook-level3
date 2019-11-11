package seedu.sugarmummy.model.records;

/**
 * Represents different possible types of records.
 */
public enum RecordType {

    BLOODSUGAR, BMI;

    public static final String MESSAGE_CONSTRAINTS =
            "Record type not recognized";
}
