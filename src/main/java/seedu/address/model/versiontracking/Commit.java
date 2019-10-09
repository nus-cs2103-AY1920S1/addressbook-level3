package seedu.address.model.versiontracking;

import seedu.address.model.studyplan.StudyPlan;

/**
 * Represents a commit of a given study plan.
 */
public class Commit {
    private static int numberOfCommits = 0;

    private int index;
    private StudyPlan studyPlan;

    public Commit(StudyPlan studyPlan) {
        this.studyPlan = studyPlan;

        numberOfCommits++;
        index = numberOfCommits;
    }

    /**
     * This constructor is used for {@code JsonAdaptedCommit} to construct a {@code Commit} object based on
     * given details.
     */
    public Commit(int index, StudyPlan studyPlan) {
        this.index = index;
        this.studyPlan = studyPlan;
    }

    public int getIndex() {
        return index;
    }

    public StudyPlan getStudyPlan() {
        return studyPlan;
    }
}
