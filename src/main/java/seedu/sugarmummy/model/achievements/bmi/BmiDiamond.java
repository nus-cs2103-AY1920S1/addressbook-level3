package seedu.sugarmummy.model.achievements.bmi;

import static seedu.sugarmummy.model.achievements.AchievementLevel.DIAMOND;

import seedu.sugarmummy.model.achievements.Achievement;
import seedu.sugarmummy.model.achievements.DurationUnit;

/**
 * BMI Achievement of diamond level.
 */
public class BmiDiamond extends Achievement implements Bmi {

    private static final String TITLE = "Prime Governor of the Masses, Slayer of Cross-Dimensional Obesity";

    private static final int DURATION_VALUE = 180;
    private static final String DESCRIPTION = "Attain between " + MINIMUM + " and " + MAXIMUM + CONSTRAINT_UNITS + " "
            + "(inclusive) of daily average " + RECORD_TYPE + " for at least "
            + DURATION_VALUE + " consecutive "
            + DURATION_UNITS.toLowerCase()
            + (DURATION_VALUE != 1 ? "s." : ".");

    public BmiDiamond() {
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

    @Override
    public Achievement copy() {
        Achievement newAchievement = new BmiDiamond();
        newAchievement.setAchievementState(this.getAchievementState());
        return newAchievement;
    }

}
