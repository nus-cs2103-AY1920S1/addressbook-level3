package seedu.address.model.achievements.bmi;

import static seedu.address.model.achievements.AchievementLevel.PLATINUM;

import seedu.address.model.achievements.Achievement;
import seedu.address.model.achievements.DurationUnit;

/**
 * BMI Achievement of platinum level.
 */
public class BmiPlatinum extends Achievement implements Bmi {

    private static final String TITLE = "Chief Guardian of Mass and Space-Time Continuum";

    private static final int DURATION_VALUE = 90;
    private static final String DESCRIPTION = "Attain between " + MINIMUM + " and " + MAXIMUM + CONSTRAINT_UNITS + " "
            + "(inclusive) of daily average " + RECORD_TYPE + " for at least "
            + DURATION_VALUE + " consecutive "
            + DURATION_UNITS.toLowerCase()
            + (DURATION_VALUE != 1 ? "s." : ".");

    public BmiPlatinum() {
        super(RECORD_TYPE, TITLE, DESCRIPTION, PLATINUM);
    }

    @Override
    public double getMaximum() {
        return MAXIMUM;
    }

    @Override
    public double getMinimum() {
        return MINIMUM;
    }

    @Override
    public double getDurationValue() {
        return DURATION_VALUE;
    }

    @Override
    public DurationUnit getDurationUnits() {
        return DURATION_UNITS;
    }

}
