package seedu.sugarmummy.model.statistics;

import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_RECORD_TYPE;

import java.util.function.Predicate;

import seedu.sugarmummy.model.record.BloodSugar;
import seedu.sugarmummy.model.record.Bmi;
import seedu.sugarmummy.model.record.Record;
import seedu.sugarmummy.model.record.RecordType;

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
