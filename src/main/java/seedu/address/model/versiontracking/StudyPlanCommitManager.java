package seedu.address.model.versiontracking;

import seedu.address.model.studyplan.StudyPlan;

public class StudyPlanCommitManager {
    private int studyPlanIndex;
    private CommitList commitList;

    public StudyPlanCommitManager(StudyPlan studyPlan) {
        this.studyPlanIndex = studyPlan.getIndex();
        this.commitList = new CommitList();
    }

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
