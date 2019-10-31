package seedu.address.model.statistics;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_RECORD_TYPE;

import java.util.function.Predicate;

import seedu.address.model.record.BloodSugar;
import seedu.address.model.record.Bmi;
import seedu.address.model.record.Record;
import seedu.address.model.record.RecordType;

/**
 * Tests that a {@code Record} is an instance of a given {@code RecordType}.
 */
public class RecordContainsRecordTypePredicate implements Predicate<Record> {

    private final RecordType recordType;

    public RecordContainsRecordTypePredicate(RecordType recordType) {
        this.recordType = recordType;
    }

    @Override
    public boolean test(Record record) {
        switch (recordType) {
        case BLOODSUGAR:
            return record instanceof BloodSugar;
        case BMI:
            return record instanceof Bmi;
        default:
            throw new IllegalArgumentException(MESSAGE_INVALID_RECORD_TYPE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecordContainsRecordTypePredicate // instanceof handles nulls
                && recordType.equals(((RecordContainsRecordTypePredicate) other).recordType)); // state check
    }
}
