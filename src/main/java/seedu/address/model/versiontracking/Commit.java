package seedu.address.model.versiontracking;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.studyplan.StudyPlan;

/**
 * Represents a commit of a given study plan.
 */
public class Commit {

    private String commitMessage;
    private StudyPlan studyPlan;

    /**
     * This constructor is also used for {@code JsonAdaptedCommit} to construct a {@code Commit} object based on
     * given details.
     */
    public Commit(StudyPlan studyPlan, String commitMessage) {
        requireAllNonNull(studyPlan, commitMessage);
        this.studyPlan = studyPlan;
        this.commitMessage = commitMessage;
    }

    public StudyPlan getStudyPlan() {
        return studyPlan;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    @Override
    public String toString() {
        return "Commit no. " + studyPlan.getIndex() + ".%1$d:"
                + " " + commitMessage;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Commit // instanceof handles nulls
                && commitMessage.equals(((Commit) other).commitMessage) // state check
                && studyPlan.getIndex() == ((Commit) other).getStudyPlan().getIndex());
    }
}
