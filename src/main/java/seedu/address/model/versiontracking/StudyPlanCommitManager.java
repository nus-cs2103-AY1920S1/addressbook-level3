package seedu.address.model.versiontracking;

import seedu.address.model.studyplan.StudyPlan;

/**
 * Represents a manager that manages the commits for one study plan with a particular index.
 */
public class StudyPlanCommitManager {
    private int studyPlanIndex;
    private CommitList commitList;

    public StudyPlanCommitManager(StudyPlan studyPlan) {
        this.studyPlanIndex = studyPlan.getIndex();
        this.commitList = new CommitList();
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
     * @param studyPlan study plan to commit.
     */
    public void commit(StudyPlan studyPlan) {
        StudyPlan planToCommit = null;
        try {
            planToCommit = studyPlan.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        commitList.commitStudyPlan(planToCommit);
    }
}
