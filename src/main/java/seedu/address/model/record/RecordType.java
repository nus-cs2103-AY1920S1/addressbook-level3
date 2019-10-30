package seedu.address.model.record;

/**
 * Represents different possible types of records.
 */
public enum RecordType {

    BLOODSUGAR, BMI, DIET, EXERCISE, HEIGHTANDWEIGHT, MEDICALEXPENSES;

    public static final String MESSAGE_CONSTRAINTS =
            "Record type not recognized";
}
