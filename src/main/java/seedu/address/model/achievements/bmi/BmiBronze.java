package seedu.address.model.achievements.bmi;

import static seedu.address.model.achievements.AchievementLevel.BRONZE;

import seedu.address.model.achievements.Achievement;
import seedu.address.model.achievements.DurationUnit;

/**
 * BMI Achievement of bronze level.
 */
public class BmiBronze extends Achievement implements Bmi {

    private static final String TITLE = "Mass Index Novice";

    private static final int DURATION_VALUE = 7;
    private static final String DESCRIPTION = "Attain between " + MINIMUM + " and " + MAXIMUM + CONSTRAINT_UNITS + " "
            + "(inclusive) of daily average " + RECORD_TYPE + " for at least "
            + DURATION_VALUE + " consecutive "
            + DURATION_UNITS.toLowerCase()
            + (DURATION_VALUE != 1 ? "s." : ".");

    public BmiBronze() {
        super(RECORD_TYPE, TITLE, DESCRIPTION, BRONZE);
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
