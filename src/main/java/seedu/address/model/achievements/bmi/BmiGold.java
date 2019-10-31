package seedu.address.model.achievements.bmi;

import static seedu.address.model.achievements.AchievementLevel.GOLD;

import seedu.address.model.achievements.Achievement;
import seedu.address.model.achievements.DurationUnit;

/**
 * BMI Achievement of gold level.
 */
public class BmiGold extends Achievement implements Bmi {

    private static final String TITLE = "Mass Index Elder of Newtonian Physics";

    private static final int DURATION_VALUE = 30;
    private static final String DESCRIPTION = "Attain between " + MINIMUM + " and " + MAXIMUM + CONSTRAINT_UNITS + " "
            + "(inclusive) of daily average " + RECORD_TYPE + " for at least "
            + DURATION_VALUE + " consecutive "
            + DURATION_UNITS.toLowerCase()
            + (DURATION_VALUE != 1 ? "s." : ".");

    public BmiGold() {
        super(RECORD_TYPE, TITLE, DESCRIPTION, GOLD);
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
        Achievement newAchievement = new BmiGold();
        newAchievement.setAchievementState(this.getAchievementState());
        return newAchievement;
    }

}
