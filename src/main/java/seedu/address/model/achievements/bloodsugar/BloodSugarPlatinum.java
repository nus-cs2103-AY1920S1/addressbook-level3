package seedu.address.model.achievements.bloodsugar;

import static seedu.address.model.achievements.AchievementLevel.PLATINUM;

import seedu.address.model.achievements.Achievement;
import seedu.address.model.achievements.DurationUnit;

/**
 * Bloodsugar Achievement of platinum level.
 */
public class BloodSugarPlatinum extends Achievement implements BloodSugar {

    private static final String TITLE = "Sugar Elemental Mage";

    private static final int DURATION_VALUE = 90;
    private static final String DESCRIPTION = "Attain between " + MINIMUM + " and " + MAXIMUM + CONSTRAINT_UNITS + " "
            + "(inclusive) of daily average " + RECORD_TYPE.toString().toLowerCase() + " levels for at least "
            + DURATION_VALUE + " consecutive "
            + DURATION_UNITS.toLowerCase()
            + (DURATION_VALUE != 1 ? "s." : ".");

    public BloodSugarPlatinum() {
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

    @Override
    public Achievement copy() {
        Achievement newAchievement = new BloodSugarPlatinum();
        newAchievement.setAchievementState(this.getAchievementState());
        return newAchievement;
    }

}
