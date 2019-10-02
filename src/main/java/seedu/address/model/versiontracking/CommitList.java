package seedu.address.model.versiontracking;

import java.util.ArrayList;

import seedu.address.model.studyplan.StudyPlan;

/**
 * Represents the list of all the commits in a study plan.
 */
public class CommitList {
    private ArrayList<Commit> commits;

    public CommitList() {
        commits = new ArrayList<>();
    }

    /**
     * Adds a study plan to this commit list.
     * @param studyPlan study plan to be committed.
     */
    public void commitStudyPlan(StudyPlan studyPlan) {
        Commit commit = new Commit(studyPlan);
        commits.add(commit);
    }

}
