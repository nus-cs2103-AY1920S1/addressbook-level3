package seedu.jarvis.model.cca.CcaProgress;

import seedu.jarvis.model.cca.exceptions.CcaProgressAtMaxException;
import seedu.jarvis.model.cca.exceptions.CcaProgressAtZeroException;
import seedu.jarvis.model.cca.exceptions.MaxProgressNotSetException;

import static java.util.Objects.requireNonNull;

/**
 * A wrapper class containing the progress of the current cca.
 */
public class CcaCurrentProgress {

    private int maxProgress = 0;
    private int currentProgress = 0;

    public void increaseProgress() {
        if (currentProgress >= maxProgress ) {
            throw new CcaProgressAtMaxException();
        }
        currentProgress ++;
    }

    public void decreaseProgress() {
        if (lessThanOne(currentProgress)) {
            throw new CcaProgressAtZeroException();
        }
        currentProgress --;
    }

    public void setMaxProgress(int maxProgress) {
        requireNonNull(maxProgress);
        this.maxProgress = maxProgress;
    }

    public float getProgressPercentage() {
        if (isNotSet(maxProgress)) {
            throw new MaxProgressNotSetException();
        }
        return currentProgress/maxProgress;
    }


    //// util methods
    public boolean lessThanOne(int num) {
        requireNonNull(num);
        if (num < 1) {
            return true;
        }
        return false;
    }

    private boolean isNotSet(int maxProgress) {
        if (maxProgress == 0) {
            return true;
        }
        return false;
    }
}
