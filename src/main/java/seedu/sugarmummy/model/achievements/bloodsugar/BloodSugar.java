package seedu.sugarmummy.model.achievements.bloodsugar;

import static seedu.sugarmummy.model.achievements.DurationUnit.DAY;
import static seedu.sugarmummy.model.record.RecordType.BLOODSUGAR;

import seedu.sugarmummy.model.achievements.DurationUnit;
import seedu.sugarmummy.model.record.RecordType;

/**
 * Achievement of type bloodsugar.
 */
public interface BloodSugar {

    RecordType RECORD_TYPE = BLOODSUGAR;
    double MINIMUM = 4.0;
    double MAXIMUM = 7.8;
    String CONSTRAINT_UNITS = "mmol/L";
    DurationUnit DURATION_UNITS = DAY;

    /**
     * Returns the type of record of this achievement;
     */
    RecordType getRecordType();
}
