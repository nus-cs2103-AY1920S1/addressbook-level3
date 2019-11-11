package seedu.address.model.versiontracking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.studyplan.StudyPlan;
import seedu.address.testutil.StudyPlanBuilder;

public class CommitTest {

    public static final String COMMIT_MESSAGE_1 = "test commit message";
    public static final String COMMIT_MESSAGE_2 = "test commit message 2";

    private static final StudyPlan testStudyPlan = new StudyPlanBuilder().build();

    private Commit testCommit = new Commit(testStudyPlan, COMMIT_MESSAGE_1);

    @Test
    public void getCommitMessage() {
        assertEquals(testCommit.getCommitMessage(), COMMIT_MESSAGE_1);
    }

    @Test
    public void getStudyPlan() {
        assertEquals(testCommit.getStudyPlan(), testStudyPlan);
    }

    @Test
    public void equals() {
        Commit testCommitEqual = new Commit(testStudyPlan, COMMIT_MESSAGE_1);
        Commit testCommitNotEqual = new Commit(testStudyPlan, COMMIT_MESSAGE_2);
        assertEquals(testCommit, testCommitEqual);
        assertNotEquals(testCommit, testCommitNotEqual);
    }
}
