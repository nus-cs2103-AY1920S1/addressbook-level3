package seedu.address.model.achievements.bloodsugar;

import static seedu.address.model.achievements.AchievementLevel.DIAMOND;

import seedu.address.model.achievements.Achievement;
import seedu.address.model.achievements.DurationUnit;

/**
 * Bloodsugar Achievement of diamond level.
 */
public class BloodSugarDiamond extends Achievement implements BloodSugar {

    private static final String TITLE = "Grand Sorcerer Supreme of the Sugar Arts";

    private static final int DURATION_VALUE = 180;
    private static final String DESCRIPTION = "Attain between " + MINIMUM + " and " + MAXIMUM + CONSTRAINT_UNITS + " "
            + "(inclusive) of daily average " + RECORD_TYPE.toString().toLowerCase() + " levels for at least "
            + DURATION_VALUE + " consecutive "
            + DURATION_UNITS.toLowerCase()
            + (DURATION_VALUE != 1 ? "s." : ".");

    public BloodSugarDiamond() {
        super(RECORD_TYPE, TITLE, DESCRIPTION, DIAMOND);
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
