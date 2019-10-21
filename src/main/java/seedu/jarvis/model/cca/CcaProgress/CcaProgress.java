package seedu.jarvis.model.cca.CcaProgress;

import java.util.List;

import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents the progress of a CCA.
 */
public class CcaProgress {

    private final CcaProgressList ccaProgressList = new CcaProgressList();
    private final CcaCurrentProgress ccaCurrentProgress = new CcaCurrentProgress();

    public CcaProgress() {

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
}
