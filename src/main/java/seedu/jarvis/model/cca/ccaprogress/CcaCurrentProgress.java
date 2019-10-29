package seedu.jarvis.model.cca.ccaprogress;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.AppUtil.checkArgument;

import seedu.jarvis.model.cca.exceptions.CcaProgressAtZeroException;

/**
 * A wrapper class containing the progress of the current cca.
 */
public class CcaCurrentProgress {

    private static final String MESSAGE_CONSTRAINTS = "Current progress cannot be negative.";

    private int currentProgress = 0;

    /**
     * Default constructor used.
     */
    public CcaCurrentProgress() {

    }

    public void setCurrentProgress(int currentProgress) {
        checkArgument(numberIsPositive(currentProgress), MESSAGE_CONSTRAINTS);
        this.currentProgress = currentProgress;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    /**
     * Increments the current progress by 1.
     */
    public void increaseProgress() {
        currentProgress++;
    }

    /**
     * Decreases the current progress by 1.
     */
    public void decreaseProgress() {
        if (lessThanOne(currentProgress)) {
            throw new CcaProgressAtZeroException();
        }
        currentProgress--;
    }

    //// util methods

    /**
     * Method to check whether a number is negative.
     *
     * @param num
     * @return a boolean.
     */
    public boolean numberIsPositive(int num) {
        requireNonNull(num);
        if (num >= 0) {
            return true;
        }
        return false;
    }

    /**
     * Method to check whether a number is less than one.
     *
     * @param num
     * @return a boolean.
     */
    public boolean lessThanOne(int num) {
        requireNonNull(num);
        if (num < 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CcaCurrentProgress)) {
            return false;
        }

        CcaCurrentProgress otherCcaCurrentProgress = (CcaCurrentProgress) other;
        return otherCcaCurrentProgress.currentProgress == this.currentProgress;
    }
}
