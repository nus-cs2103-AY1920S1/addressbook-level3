package seedu.address.model.cap.person.Rank;

import seedu.address.model.cap.person.DegreeClassification;

public class ThirdClass extends Rank {

    private static final String TITLE = "Third Class Honours";
    private static final double MAXIMUM_CAP = 3.49;
    private static final double MINIMUM_CAP = 3.00;
    private static final String imageFilePath = "/images/third_class.png";

    public ThirdClass() {
        super(DegreeClassification.THIRD, TITLE);
    }

    public static double getMaximumCap() {
        return MAXIMUM_CAP;
    }

    public static double getMinimumCap() {
        return MINIMUM_CAP;
    }

    @Override
    public String getRankImageFilePath() {
        return imageFilePath;
    }

    public static boolean isWithinRange(double cap) {
        if (cap > MAXIMUM_CAP && cap < MINIMUM_CAP) {
            return true;
        } else {
            return false;
        }
    }
}
