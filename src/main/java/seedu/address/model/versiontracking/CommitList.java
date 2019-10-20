package seedu.address.model.versiontracking;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.studyplan.StudyPlan;

/**
 * Represents the list of all the commits in a study plan.
 */
public class CommitList {
    private List<Commit> commits;

    public CommitList() {
        commits = new ArrayList<>();
    }

    public CommitList(List<Commit> commits) {
        this.commits = commits;
    }

    public List<Commit> getCommits() {
        return commits;
    }

    /**
     * Gets a commit by its index in the list.
     */
    public Commit getCommitByIndex(int index) {
        return commits.get(index);
    }

    /**
     * Gets the instance of study plan represented by the commit index.
     */
    public StudyPlan getStudyPlanByCommitNumber(int commitNumber) {
        return getCommitByIndex(commitNumber).getStudyPlan();
    }

    /**
     * Deletes all the commits after a given index.
     */
    public void deleteAllLaterCommits(int index) {
        for (int i = index + 1; i < commits.size(); i++) {
            commits.remove(i);
        }
    }

    /**
     * Adds a study plan to this commit list.
     *
     * @param studyPlan study plan to be committed.
     */
    public void commitStudyPlan(StudyPlan studyPlan, String commitMessage) {
        Commit commit = new Commit(studyPlan, commitMessage);
        commits.add(commit);
    }

    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder();
        if (commits.size() == 0) {
            toReturn.append("There are zero commits in this study plan!");
        } else {
            for (Commit commit : commits) {
                int index = commits.indexOf(commit);
                toReturn.append(String.format(commit.toString(), index) + "\n");
            }
        }

        return toReturn.toString();
    }
}
