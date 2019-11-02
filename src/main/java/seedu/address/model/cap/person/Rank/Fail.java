package seedu.address.model.cap.person.Rank;

import seedu.address.model.cap.person.DegreeClassification;

public class Fail extends Rank {

    private static final String TITLE = "FAIL";
    private static final double MAXIMUM_CAP = 1.99;
    private static final double MINIMUM_CAP = 0.0;
    private static final String imageFilePath = "/images/failcourse.png";

    public Fail() {
        super(DegreeClassification.FIRST, TITLE);
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
