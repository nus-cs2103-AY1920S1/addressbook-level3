package seedu.jarvis.model.cca.ccaprogress;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.jarvis.commons.core.index.Index;
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

    public CcaMilestoneList getCcaMilestoneList() {
        return ccaMilestoneList;
    }

    public CcaCurrentProgress getCcaCurrentProgress() {
        return ccaCurrentProgress;
    }

    /**
     * Replaces the contents of the progress list with {@code milestones}.
     * {@code milestones} must not contain duplicate milestones.
     */
    public void setMilestones(List<CcaMilestone> milestones) {
        requireAllNonNull(milestones);

        ccaMilestoneList.setMilestones(milestones);
    }

    /**
     * Replaces the contents of the progress list with {@code milestones}.
     * {@code milestones} must not contain duplicate milestones.
     */
    public void setMilestones(CcaMilestoneList ccaMilestoneList) {
        requireNonNull(ccaMilestoneList);

        this.ccaMilestoneList.setMilestones(ccaMilestoneList);
    }

    /**
     * Replaces the values of the current progress with the values from {@code ccaCurrentProgress}.
     */
    public void setCcaCurrentProgress(CcaCurrentProgress ccaCurrentProgress) {
        this.ccaCurrentProgress.setCurrentProgress(ccaCurrentProgress.getCurrentProgress());
    }

    /**
     * Gets the current progress percentage of the Cca.
     */
    public double getCcaProgressPercentage() {
        if (ccaMilestoneListIsEmpty()) {
            throw new MaxProgressNotSetException();
        }

        int ccaCurrentProgressInt = ccaCurrentProgress.getCurrentProgress();
        double ccaProgressPercentage = (double) ccaCurrentProgressInt / ccaMilestoneList.size();
        return ccaProgressPercentage;
    }

    /**
     * Gets the current {@code CcaMilestone}.
     */
    public CcaMilestone getCurrentCcaMilestone() {
        Index ccaMilestoneIndex = Index.fromOneBased(ccaCurrentProgress.getCurrentProgress());
        CcaMilestone ccaMilestone = ccaMilestoneList.getCcaMilestone(ccaMilestoneIndex);
        return ccaMilestone;
    }

    /**
     * Gets {@code CcaCurrentProgress} as an int.
     */
    public int getCcaCurrentProgressAsInt() {
        return ccaCurrentProgress.getCurrentProgress();
    }

    /**
     * Checks if the CcaMilestoneList is empty.
     *
     * @return true of the {@code CcaMilestoneList} is empty.
     */
    public boolean ccaMilestoneListIsEmpty() {
        return ccaMilestoneList.isEmpty();
    }

    /**
     * Increases the progress by 1 {@code Milestone}.
     */
    public void increaseProgress() {
        if (ccaMilestoneListIsEmpty()) {
            throw new CcaProgressNotSetException();
        }
        ccaCurrentProgress.increaseProgress();
    }

    /**
     * Returns true if the {@code CcaCurrentProgress} is at max.
     */
    public boolean progressAtMax() {
        return ccaCurrentProgress.getCurrentProgress() == ccaMilestoneList.size();
    }

    /**
     * Returns true if the progress is the minimum.
     */
    public boolean progressAtMin() {
        if (ccaCurrentProgress.progressAtMin()) {
            return true;
        }

        return false;
    }

    /**
     * Decreases the progress of the {@code CurrentProgress}.
     */
    public void decreaseProgress() {
        ccaCurrentProgress.decreaseProgress();
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
