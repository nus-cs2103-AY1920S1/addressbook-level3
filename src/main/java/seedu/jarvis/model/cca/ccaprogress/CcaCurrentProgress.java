package seedu.jarvis.model.cca.ccaprogress;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.AppUtil.checkArgument;

import seedu.jarvis.model.cca.exceptions.CcaProgressAtZeroException;

/**
 * A wrapper class containing the progress of the current cca.
 */
public class CcaCurrentProgress {

    private static final String MESSAGE_CONSTRAINTS = "Current progress cannot be smaller than 1.";

    private int currentProgress = 1;

    /**
     * Default constructor used.
     */
    public CcaCurrentProgress() {

    }

    public void setCurrentProgress(int currentProgress) {
        checkArgument(numberIsLargerThanZero(currentProgress), MESSAGE_CONSTRAINTS);
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
        if (progressAtMin()) {
            throw new CcaProgressAtZeroException();
        }
        currentProgress--;
    }


    //// util methods

    /**
     * Returns true of the progress is at 1.
     */
    public boolean progressAtMin() {
        return currentProgress == 1;
    }

    /**
     * Returns true if a number is larger than 0.
     */
    public boolean numberIsLargerThanZero(int num) {
        requireNonNull(num);
        return num > 0;
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
