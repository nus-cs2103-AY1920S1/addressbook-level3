package seedu.address.model.versiontracking;

import seedu.address.model.studyplan.StudyPlan;

public class Commit {
    private int index;
    private StudyPlan studyPlan;

    private static int numberOfCommits = 0;

    public Commit(StudyPlan studyPlan) {
        this.studyPlan = studyPlan;

        numberOfCommits++;
        index = numberOfCommits;
    }
}
