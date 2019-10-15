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
     * Adds a study plan to this commit list.
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
                toReturn.append(commit.toString() + "\n");
            }
        }

        return toReturn.toString();
    }
}
