package seedu.jarvis.model.cca.ccaprogress;

import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.jarvis.model.cca.exceptions.CcaProgressNotSetException;
import seedu.jarvis.model.cca.exceptions.MaxProgressNotSetException;

/**
 * Represents the progress of a CCA.
 */
public class CcaProgress {

    private final CcaMilestoneList ccaMilestoneList = new CcaMilestoneList();
    private final CcaCurrentProgress ccaCurrentProgress = new CcaCurrentProgress();

    public CcaProgress() {

    }

    public CcaMilestoneList getCcaProgressList() {
        return ccaMilestoneList;
    }

    public CcaCurrentProgress getCcaCurrentProgress() throws MaxProgressNotSetException {
        return ccaCurrentProgress;
    }

    /**
     * Gets the current progress percentage of the Cca.
     */
    public double getCcaProgressPercentage() {
        return ccaCurrentProgress.getProgressPercentage();
    }

    /**
     * Replaces the contents of the progress list with {@code milestones}.
     * {@code milestones} must not contain duplicate milestones.
     */
    public void setMilestones(List<CcaMilestone> milestones) {
        requireAllNonNull(milestones);

        ccaMilestoneList.setMilestones(milestones);
        ccaCurrentProgress.setMaxProgress(milestones.size());
    }

    /**
     * Replaces the contents of the progress list with {@code milestones}.
     * {@code milestones} must not contain duplicate milestones.
     */
    public void setMilestones(CcaMilestoneList ccaMilestoneList) {
        requireAllNonNull(ccaMilestoneList);

        this.ccaMilestoneList.setMilestones(ccaMilestoneList);
        ccaCurrentProgress.setMaxProgress(ccaMilestoneList.size());
    }

    /**
     * Replaces the values of the current progress with the values from {@code ccaCurrentProgress}.
     */
    public void setCcaCurrentProgress(CcaCurrentProgress ccaCurrentProgress) {
        this.ccaCurrentProgress.setMaxProgress(ccaCurrentProgress.getMaxProgress());
        this.ccaCurrentProgress.setCurrentProgress(ccaCurrentProgress.getCurrentProgress());
    }

    /**
     * Checks if the CcaMilestoneList is empty.
     *
     * @return true of the {@code CcaMilestoneList} is empty.
     */
    public boolean ccaProgressListIsEmpty() {
        if (ccaMilestoneList.size() == 0) {
            return true;
        }

        return false;
    }

    /**
     * Increases the progress by 1 {@code Milestone}.
     */
    public void increaseProgress() {
        if (ccaProgressListIsEmpty()) {
            throw new CcaProgressNotSetException();
        }
        ccaCurrentProgress.increaseProgress();
    }

    /**
     * Checks if the Cca progress is at max.
     */
    public boolean progressAtMax() {
        return ccaCurrentProgress.progressAtMax();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CcaProgress)) {
            return false;
        }

        CcaProgress otherCcaProgress = (CcaProgress) other;
        return otherCcaProgress.ccaMilestoneList.equals(this.ccaMilestoneList)
                && otherCcaProgress.ccaCurrentProgress.equals(this.ccaCurrentProgress);
    }
}
