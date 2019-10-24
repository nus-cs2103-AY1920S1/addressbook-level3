package seedu.jarvis.model.cca.ccaprogress;

import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

/**
 * Represents the progress of a CCA.
 */
public class CcaProgress {

    private final CcaProgressList ccaProgressList = new CcaProgressList();
    private final CcaCurrentProgress ccaCurrentProgress = new CcaCurrentProgress();

    public CcaProgress() {

    }

    public CcaProgressList getCcaProgressList() {
        return ccaProgressList;
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

        ccaProgressList.setMilestones(milestones);
        ccaCurrentProgress.setMaxProgress(milestones.size());
    }

    /**
     * Replaces the contents of the progress list with {@code milestones}.
     * {@code milestones} must not contain duplicate milestones.
     */
    public void setMilestones(CcaProgressList ccaProgressList) {
        requireAllNonNull(ccaProgressList);

        this.ccaProgressList.setMilestones(ccaProgressList);
        ccaCurrentProgress.setMaxProgress(ccaProgressList.size());
    }

    /**
     * Replaces the values of the current progress with the values from {@code ccaCurrentProgress}.
     */
    public void setCcaCurrentProgress(CcaCurrentProgress ccaCurrentProgress) {
        this.ccaCurrentProgress.setMaxProgress(ccaCurrentProgress.getMaxProgress());
        this.ccaCurrentProgress.setCurrentProgress(ccaCurrentProgress.getCurrentProgress());
    }

    /**
     * Checks if the CcaProgressList is empty.
     *
     * @return true of the {@code CcaProgressList} is empty.
     */
    public boolean ccaProgressListIsEmpty() {
        if (ccaProgressList.size() == 0) {
            return true;
        }

        return false;
    }

    /**
     * Increases the progress by 1 {@code Milestone}.
     */
    public void increaseProgress() {
        ccaCurrentProgress.increaseProgress();
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
        return otherCcaProgress.ccaProgressList.equals(this.ccaProgressList)
                && otherCcaProgress.ccaCurrentProgress.equals(this.ccaCurrentProgress);
    }

}
