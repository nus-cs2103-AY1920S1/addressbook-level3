package seedu.jarvis.model.cca.ccaprogress;

import static java.util.Objects.requireNonNull;

import seedu.jarvis.model.cca.exceptions.CcaProgressAtMaxException;
import seedu.jarvis.model.cca.exceptions.CcaProgressAtZeroException;
import seedu.jarvis.model.cca.exceptions.MaxProgressNotSetException;

/**
 * A wrapper class containing the progress of the current cca.
 */
public class CcaCurrentProgress {

    private int maxProgress = 0;
    private int currentProgress = 0;

    public int getMaxProgress() {
        return maxProgress;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    /**
     * Increments the current progress by 1.
     */
    public void increaseProgress() {
        if (currentProgress >= maxProgress) {
            throw new CcaProgressAtMaxException();
        }
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
     * Sets the maximum progress.
     *
     * @param maxProgress needed.
     */
    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
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
     * Returns the current progress as a percentage of the max progress.
     *
     * @return the current progress as a percentage of the max progress.
     */
    public float getProgressPercentage() {
        if (isNotSet(maxProgress)) {
            throw new MaxProgressNotSetException();
        }
        return currentProgress / maxProgress;
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

    /**
     * Method to check if max progress is not yet set.
     *
     * @param maxProgress
     * @return true if max progress is 0.
     */
    private boolean isNotSet(int maxProgress) {
        if (maxProgress == 0) {
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
        return otherCcaCurrentProgress.maxProgress == this.maxProgress
                && otherCcaCurrentProgress.currentProgress == this.currentProgress;
    }

}
