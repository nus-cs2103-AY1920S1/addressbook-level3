package seedu.address.model.cap.module.rank;

import seedu.address.model.cap.module.DegreeClassification;

/**
 * Represents a SecondLower grade.
 */
public class SecondLower extends Rank {

    private static final String TITLE = "SECOND CLASS HONOURS (LOWER)";
    private static final double MAXIMUM_CAP = 3.99;
    private static final double MINIMUM_CAP = 3.50;
    private static final String imageFilePath = "/images/second_lower.png";

    public SecondLower() {
        super(DegreeClassification.SECONDLOWER, TITLE);
    }

    public static double getMaximumCap() {
        return MAXIMUM_CAP;
    }

    public static double getMinimumCap() {
        return MINIMUM_CAP;
    }

    public String getTitle() {
        return TITLE;
    }

    @Override
    public String getRankImageFilePath() {
        return imageFilePath;
    }

    /**
     * To check if the cap given is within the range of the catagorisation of this rank/ class.
     * @param cap CUMULATIVE AVERAGE POINT
     * @return
     */
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
