package seedu.address.model.versiontracking;

import seedu.address.model.studyplan.StudyPlan;

/**
 * Represents a manager that manages the commits for one study plan with a particular index.
 */
public class StudyPlanCommitManager {
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
        StudyPlan planToCommit = null;
        try {
            planToCommit = studyPlan.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        commitList.commitStudyPlan(planToCommit, commitMessage);
    }

    /**
     * Reverts to the commit specified by the given commit number. Discards all later commits.
     */
    public StudyPlan revertToCommit(int commitNumber) {
        StudyPlan newActiveStudyPlan = commitList.getStudyPlanByCommitNumber(commitNumber);
        commitList.deleteAllLaterCommits(commitNumber);
        return newActiveStudyPlan;
    }
}
