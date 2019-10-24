package seedu.address.model.versiontracking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.StudyPlanBuilder;

public class CommitTest {
    private Commit testCommit = new Commit(new StudyPlanBuilder().build(), "MESSAGE");

    @Test
    public void getCommitMessage() {
        assertEquals(testCommit.getCommitMessage(), "MESSAGE");
    }
}
