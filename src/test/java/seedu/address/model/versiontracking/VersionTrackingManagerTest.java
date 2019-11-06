package seedu.address.model.versiontracking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalVersionTrackingManager.SP_1;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalVersionTrackingManager;

public class VersionTrackingManagerTest {
    @Test
    public void commitStudyPlan() {
        String commitMessage = "commit message";
        VersionTrackingManager manager = TypicalVersionTrackingManager.getTypicalVersionTrackingManager();
        StudyPlanCommitManager studyPlanCommitManager = manager.commitStudyPlan(SP_1, commitMessage);
        assertEquals(studyPlanCommitManager.getStudyPlanIndex(), SP_1.getIndex());
        assertEquals(studyPlanCommitManager.getCommitList().getCommitByIndex(3).getCommitMessage(), commitMessage);
    }
}
