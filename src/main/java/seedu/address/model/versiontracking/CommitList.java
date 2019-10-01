package seedu.address.model.versiontracking;

import java.util.ArrayList;

import seedu.address.model.studyplan.StudyPlan;


public class CommitList {
    private ArrayList<Commit> commits;

    public CommitList() {
        commits = new ArrayList<>();
    }

    public void commitStudyPlan(StudyPlan studyPlan) {
        Commit commit = new Commit(studyPlan);
        commits.add(commit);
    }

}
