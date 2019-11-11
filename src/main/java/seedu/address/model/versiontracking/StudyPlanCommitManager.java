package seedu.address.model.versiontracking;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.versiontracking.exception.CommitNotFoundException;

/**
 * Represents a manager that manages the commits for one study plan with a particular index.
 */
public class StudyPlanCommitManager {

    public static final String MESSAGE_REVERT_COMMIT = "Revert to: %1$s";
    private static final Logger logger = LogsCenter.getLogger(StudyPlanCommitManager.class);

    private int studyPlanIndex;
    private CommitList commitList;

    public StudyPlanCommitManager(StudyPlan studyPlan, String commitMessage) {
        this.studyPlanIndex = studyPlan.getIndex();
        this.commitList = new CommitList();
        commit(studyPlan, commitMessage);
    }

    /**
     * This constructor is used by {@code JsonAdaptedStudyPlanCommitManager} to construct a StudyPlanCommitManager
     * using the given details.
     */
    public StudyPlanCommitManager(int studyPlanIndex, CommitList commitList) {
        this.studyPlanIndex = studyPlanIndex;
        this.commitList = commitList;
    }

    public int getStudyPlanIndex() {
        return studyPlanIndex;
    }

    public CommitList getCommitList() {
        return commitList;
    }

    /**
     * Commits a study plan.
     *
     * @param studyPlan study plan to commit.
     */
    public void commit(StudyPlan studyPlan, String commitMessage) {
        requireAllNonNull(studyPlan, commitMessage);
        StudyPlan planToCommit = null;
        try {
            planToCommit = studyPlan.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        commitList.commitStudyPlan(planToCommit, commitMessage);
        logger.info("Active study plan committed successfully with a message: " + commitMessage);
    }

    /**
     * Deletes a commit specified by the index.
     */
    public void deleteCommit(int index) throws CommitNotFoundException {
        commitList.deleteCommitByIndex(index);
    }

    /**
     * Reverts to the commit specified by the given commit number. This creates a new revert commit.
     */
    public StudyPlan revertToCommit(int commitNumber) {
        StudyPlan newActiveStudyPlan = commitList.getStudyPlanByCommitNumber(commitNumber);
        String commitMessage = commitList.getCommitMessageByCommitNumber(commitNumber);
        commit(newActiveStudyPlan, String.format(MESSAGE_REVERT_COMMIT, commitMessage));
        return newActiveStudyPlan;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || ((obj instanceof StudyPlanCommitManager)
                && this.studyPlanIndex == ((StudyPlanCommitManager) obj).studyPlanIndex)
                && this.commitList.equals(((StudyPlanCommitManager) obj).commitList);
    }
}
