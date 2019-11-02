package seedu.address.model.cap.person.Rank;

import seedu.address.model.cap.person.DegreeClassification;

public class FirstClass extends Rank {

    private static final String TITLE = "FIRST CLASS HONOURS";
    private static final double MAXIMUM_CAP = 5.0;
    private static final double MINIMUM_CAP = 4.5;
    private static final String imageFilePath = "/images/first_class.png";

    public FirstClass() {
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
