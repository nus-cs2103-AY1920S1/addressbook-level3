package seedu.address.model.versiontracking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.model.versiontracking.StudyPlanCommitManager.MESSAGE_REVERT_COMMIT;
import static seedu.address.testutil.TypicalVersionTrackingManager.SAMPLE_COMMIT_MESSAGE_1;
import static seedu.address.testutil.TypicalVersionTrackingManager.SP_1;
import static seedu.address.testutil.TypicalVersionTrackingManager.SP_3;

import org.junit.jupiter.api.Test;

import seedu.address.model.studyplan.StudyPlan;
import seedu.address.testutil.TypicalVersionTrackingManager;

/**
 * A test class for StudyPlanCommitManager.
 */
public class StudyPlanCommitManagerTest {
    private static final String TEST_COMMIT_MESSAGE = "plan for NOC Israel";

    @Test
    public void getStudyPlanIndex() {
        StudyPlanCommitManager manager = TypicalVersionTrackingManager.getTypicalStudyPlanCommitManager();
        assertEquals(manager.getStudyPlanIndex(), SP_1.getIndex());
    }

    @Test
    public void commit() {
        StudyPlanCommitManager manager = TypicalVersionTrackingManager.getTypicalStudyPlanCommitManager();
        StudyPlan studyPlan = SP_1;
        manager.commit(SP_1, TEST_COMMIT_MESSAGE);
        Commit added = manager.getCommitList().getCommitByIndex(2);
        assertEquals(added.getStudyPlan(), SP_1);
        assertEquals(added.getCommitMessage(), TEST_COMMIT_MESSAGE);
    }

    @Test
    public void deleteCommit() {
        StudyPlanCommitManager manager = TypicalVersionTrackingManager.getTypicalStudyPlanCommitManager();
        Commit commit1 = manager.getCommitList().getCommitByIndex(0);
        manager.deleteCommit(0);
        Commit commit2 = manager.getCommitList().getCommitByIndex(0);
        assertNotEquals(commit1, commit2);
    }

    @Test
    public void revertToCommit() {
        StudyPlanCommitManager manager = TypicalVersionTrackingManager.getTypicalStudyPlanCommitManager();
        // add a commit first
        manager.commit(SP_3, TEST_COMMIT_MESSAGE);
        Commit commit1 = manager.getCommitList().getCommitByIndex(2);
        // then revert to an old commit
        manager.revertToCommit(0);
        Commit commit2 = manager.getCommitList().getCommitByIndex(2); // should be equal to commit1
        Commit revertedCommit = manager.getCommitList().getCommitByIndex(3); // should have the same study plan as 0

        assertEquals(commit2, commit1);
        assertEquals(revertedCommit.getStudyPlan(), SP_1);
        assertEquals(revertedCommit.getCommitMessage(), String.format(MESSAGE_REVERT_COMMIT, SAMPLE_COMMIT_MESSAGE_1));
        assertNotEquals(revertedCommit, commit1);
    }

}
