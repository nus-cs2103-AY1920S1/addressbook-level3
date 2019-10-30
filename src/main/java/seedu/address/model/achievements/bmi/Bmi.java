package seedu.address.model.achievements.bmi;

import static seedu.address.model.achievements.DurationUnit.DAY;
import static seedu.address.model.record.RecordType.BMI;

import seedu.address.model.achievements.DurationUnit;
import seedu.address.model.record.RecordType;

public interface Bmi {

    static final RecordType RECORD_TYPE = BMI;
    static final double MINIMUM = 18.5;
    static final double MAXIMUM = 25;
    static final String CONSTRAINT_UNITS = "";
    static final DurationUnit DURATION_UNITS = DAY;

    public RecordType getRecordType();

}
