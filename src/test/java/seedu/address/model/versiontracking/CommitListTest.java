package seedu.address.model.versiontracking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalVersionTrackingManager.SAMPLE_COMMIT_MESSAGE_3;
import static seedu.address.testutil.TypicalVersionTrackingManager.SP_1;

import org.junit.jupiter.api.Test;

import seedu.address.model.versiontracking.exception.CommitNotFoundException;
import seedu.address.testutil.TypicalVersionTrackingManager;

public class CommitListTest {

    @Test
    public void getCommitByIndex_validIndex_returnsExpectedCommit() throws CloneNotSupportedException {
        CommitList testCommitList = TypicalVersionTrackingManager.getTypicalCommitList();
        assertEquals(testCommitList.getCommitByIndex(0), TypicalVersionTrackingManager.getTypicalCommit(1));
    }

    @Test
    public void getCommitByIndex_invalidIndex_throwsIndexOutOfBoundsException() throws CloneNotSupportedException {
        CommitList testCommitList = TypicalVersionTrackingManager.getTypicalCommitList();

        // negative index
        assertThrows(IndexOutOfBoundsException.class, () -> testCommitList.getCommitByIndex(-1));

        // positive index
        assertThrows(IndexOutOfBoundsException.class, () -> testCommitList.getCommitByIndex(2));
    }

    @Test
    public void deleteCommitByIndex() throws CloneNotSupportedException {
        CommitList testCommitList = TypicalVersionTrackingManager.getTypicalCommitList();
        Commit commit1 = testCommitList.getCommitByIndex(0);
        testCommitList.deleteCommitByIndex(0); // valid index
        Commit commit2 = testCommitList.getCommitByIndex(0);
        assertNotEquals(commit1, commit2);

        // invalid index
        assertThrows(CommitNotFoundException.class, () -> testCommitList.deleteCommitByIndex(2));
    }

    @Test
    public void commitStudyPlan() throws CloneNotSupportedException {
        CommitList testCommitList = TypicalVersionTrackingManager.getTypicalCommitList();
        testCommitList.commitStudyPlan(SP_1, SAMPLE_COMMIT_MESSAGE_3);
        Commit added = new Commit(SP_1, SAMPLE_COMMIT_MESSAGE_3);
        assertEquals(testCommitList.getCommitByIndex(2), added);
    }
}
