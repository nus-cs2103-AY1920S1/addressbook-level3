package seedu.address.model.achievements.bmi;

import static seedu.address.model.achievements.DurationUnit.DAY;
import static seedu.address.model.record.RecordType.BMI;

import seedu.address.model.achievements.DurationUnit;
import seedu.address.model.record.RecordType;

/**
 * Achievement of type BMI.
 */
public interface Bmi {

    RecordType RECORD_TYPE = BMI;
    double MINIMUM = 18.5;
    double MAXIMUM = 25;
    String CONSTRAINT_UNITS = "";
    DurationUnit DURATION_UNITS = DAY;

    /**
     * Returns the type of record of this achievement;
     */
    RecordType getRecordType();

}
