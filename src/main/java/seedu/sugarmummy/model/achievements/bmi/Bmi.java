package seedu.sugarmummy.model.achievements.bmi;

import static seedu.sugarmummy.model.achievements.DurationUnit.DAY;
import static seedu.sugarmummy.model.records.RecordType.BMI;

import seedu.sugarmummy.model.achievements.DurationUnit;
import seedu.sugarmummy.model.records.RecordType;

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
