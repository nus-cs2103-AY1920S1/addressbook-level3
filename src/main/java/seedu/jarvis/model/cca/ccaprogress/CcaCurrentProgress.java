package seedu.jarvis.model.cca.ccaprogress;

import static java.util.Objects.requireNonNull;

import seedu.jarvis.model.cca.exceptions.CcaProgressAtZeroException;

/**
 * A wrapper class containing the progress of the current cca.
 */
public class CcaCurrentProgress {

    private int currentProgress = 0;

    /**
     * Default constructor used.
     */
    public CcaCurrentProgress() {

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

    /**
     * Sets the current progress.
     *
     * @param currentProgress to be updated.
     */
    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    /**
     * Returns true of the progress is at 0.
     */
    public boolean progressAtMin() {
        if (currentProgress == 0) {
            return true;
        }

        return false;
    }

    //// util methods

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
