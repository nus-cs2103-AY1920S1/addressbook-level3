package seedu.address.model.cap.person.Rank;

import seedu.address.model.cap.person.DegreeClassification;

public class SecondUpper extends Rank {

    private static final String TITLE = "SECOND CLASS HONOURS (UPPER)";
    private static final double MAXIMUM_CAP = 4.49;
    private static final double MINIMUM_CAP = 4.00;
    private static final String imageFilePath = "/images/second_upper.png";

    public SecondUpper() {
        super(DegreeClassification.SECONDUPPER, TITLE);
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
        if (cap >= MINIMUM_CAP && cap <= MAXIMUM_CAP) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return TITLE;
    }
}
