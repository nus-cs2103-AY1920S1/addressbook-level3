package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.storage.JsonAdaptedCommit.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVersionTrackingManager.SP_1;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.versiontracking.Commit;
import seedu.address.testutil.TypicalVersionTrackingManager;

/**
 * A test class for JsonAdaptedCommit.
 */
public class JsonAdaptedCommitTest {

    private static final String VALID_COMMIT_MESSAGE =
            TypicalVersionTrackingManager.getTypicalCommit(1).getCommitMessage();
    private static final StudyPlan VALID_STUDY_PLAN = SP_1;

    @Test
    public void toModelType_validCommitDetails_returnsCommit() throws Exception {
        Commit typicalCommit = TypicalVersionTrackingManager.getTypicalCommit(1);
        JsonAdaptedCommit adaptedCommit = new JsonAdaptedCommit(typicalCommit);

        // check commit message
        assertEquals(adaptedCommit.getCommitMessage(), typicalCommit.getCommitMessage());

        // test equality between the study plan stored
        JsonAdaptedStudyPlan adaptedStudyPlan = adaptedCommit.getStudyPlan();
        StudyPlan skeletalStudyPlan = adaptedStudyPlan.toModelType();
        assertTrue(JsonAdaptedStudyPlanTest.studyPlanLoadedCorrectly(typicalCommit.getStudyPlan(), skeletalStudyPlan));
    }

    @Test
    public void toModelType_nullCommitMessage_throwsIllegalValueException() {
        JsonAdaptedCommit commit = new JsonAdaptedCommit(new JsonAdaptedStudyPlan(VALID_STUDY_PLAN), null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "commit message");
        assertThrows(IllegalValueException.class, expectedMessage, commit::toModelType);
    }

    @Test
    public void toModelType_nullStudyPlan_throwsIllegalValueException() {
        JsonAdaptedCommit commit = new JsonAdaptedCommit(null, VALID_COMMIT_MESSAGE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StudyPlan.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, commit::toModelType);
    }

}
