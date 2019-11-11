package seedu.sugarmummy.model.achievements.bloodsugar;

import static seedu.sugarmummy.model.achievements.AchievementLevel.BRONZE;

import seedu.sugarmummy.model.achievements.Achievement;
import seedu.sugarmummy.model.achievements.DurationUnit;

/**
 * Bloodsugar Achievement of bronze level.
 */
public class BloodSugarBronze extends Achievement implements BloodSugar {

    private static final String TITLE = "Sugar Control Apprentice";

    private static final int DURATION_VALUE = 3;
    private static final String DESCRIPTION = "Attain between " + MINIMUM + " and " + MAXIMUM + CONSTRAINT_UNITS + " "
            + "(inclusive) of daily average " + RECORD_TYPE.toString().toLowerCase() + " levels for at least "
            + DURATION_VALUE + " consecutive "
            + DURATION_UNITS.toLowerCase()
            + (DURATION_VALUE != 1 ? "s." : ".");

    public BloodSugarBronze() {
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

    @Override
    public Achievement copy() {
        Achievement newAchievement = new BloodSugarBronze();
        newAchievement.setAchievementState(this.getAchievementState());
        return newAchievement;
    }

}
