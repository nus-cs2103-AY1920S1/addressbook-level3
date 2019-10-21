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
