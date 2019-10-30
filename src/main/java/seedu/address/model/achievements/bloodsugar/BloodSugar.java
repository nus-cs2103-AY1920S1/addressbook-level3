package seedu.address.model.achievements.bloodsugar;

import static seedu.address.model.achievements.DurationUnit.DAY;
import static seedu.address.model.record.RecordType.BLOODSUGAR;

import seedu.address.model.achievements.DurationUnit;
import seedu.address.model.record.RecordType;

public interface BloodSugar {

    static final RecordType RECORD_TYPE = BLOODSUGAR;
    static final double MINIMUM = 4.0;
    static final double MAXIMUM = 7.8;
    static final String CONSTRAINT_UNITS = "mmol/L";
    static final DurationUnit DURATION_UNITS = DAY;

    public RecordType getRecordType();
}
