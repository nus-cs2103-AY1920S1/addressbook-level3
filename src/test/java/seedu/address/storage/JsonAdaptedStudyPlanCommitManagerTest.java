package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.versiontracking.StudyPlanCommitManager;
import seedu.address.testutil.TypicalVersionTrackingManager;

/**
 * A test class for JsonAdaptedStudyPlanCommitManager.
 */
public class JsonAdaptedStudyPlanCommitManagerTest {
    @Test
    public void toModelType_validStudyPlanCommitManagerDetails_returnsStudyPlanCommitManager() throws Exception {
        StudyPlanCommitManager typicalStudyPlanCommitManager =
                TypicalVersionTrackingManager.getTypicalStudyPlanCommitManager();
        JsonAdaptedStudyPlanCommitManager adaptedStudyPlanCommitManager =
                new JsonAdaptedStudyPlanCommitManager(typicalStudyPlanCommitManager);
        StudyPlanCommitManager loadedStudyPlanCommitManager = adaptedStudyPlanCommitManager.toModelType();
        assertEquals(typicalStudyPlanCommitManager, loadedStudyPlanCommitManager);
    }

}
