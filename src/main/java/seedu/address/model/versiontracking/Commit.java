package seedu.address.model.versiontracking;

import seedu.address.model.studyplan.StudyPlan;

/**
 * Represents a commit of a given study plan.
 */
public class Commit {
    private static int numberOfCommits = 0;

    private int index;
    private String commitMessage;
    private StudyPlan studyPlan;

    public Commit(StudyPlan studyPlan, String commitMessage) {
        this.studyPlan = studyPlan;
        this.commitMessage = commitMessage;

        numberOfCommits++;
        index = numberOfCommits;
    }

    /**
     * This constructor is used for {@code JsonAdaptedCommit} to construct a {@code Commit} object based on
     * given details.
     */
    public Commit(int index, StudyPlan studyPlan, String commitMessage) {
        this.index = index;
        this.studyPlan = studyPlan;
        this.commitMessage = commitMessage;
    }

    public int getIndex() {
        return index;
    }

    public StudyPlan getStudyPlan() {
        return studyPlan;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    @Override
    public String toString() {
        return "Commit no. " + studyPlan.getIndex() + "." + index
                + " " + commitMessage;
    }
}
