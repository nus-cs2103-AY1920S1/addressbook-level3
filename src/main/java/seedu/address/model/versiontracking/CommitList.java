package seedu.address.model.versiontracking;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.versiontracking.exception.CommitNotFoundException;

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
     * Gets the commit message represented by the commit index.
     */
    public String getCommitMessageByCommitNumber(int commitNumber) {
        return getCommitByIndex(commitNumber).getCommitMessage();
    }

    /**
     * Deletes one commit of the specified index.
     */
    public void deleteCommitByIndex(int index) throws CommitNotFoundException {
        try {
            commits.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new CommitNotFoundException();
        }
    }

    /**
     * Deletes all the commits after a given index.
     */
    public void deleteAllLaterCommits(int index) {
        for (int i = commits.size() - 1; i > index; i--) {
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
            for (int i = 0; i < commits.size(); i++) { // do not change to for commit : commits due to Commit#equals()
                Commit commit = commits.get(i);
                toReturn.append(String.format(commit.toString(), i) + "\n");
            }
        }

        return toReturn.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // short circuit if identical objects
        }

        if (!(obj instanceof CommitList)) {
            return false; // handle null pointers
        }

        // check all commits in the list
        try {
            for (int i = 0; i < commits.size(); i++) {
                Commit commit1 = commits.get(i);
                Commit commit2 = ((CommitList) obj).commits.get(i);
                if (!commit1.equals(commit2)) {
                    return false;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        return true;
    }
}
