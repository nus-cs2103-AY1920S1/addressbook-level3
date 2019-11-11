package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedAssignment.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT_MATH;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.AssignmentDeadline;
import seedu.address.model.assignment.AssignmentName;

public class JsonAdaptedAssignmentTest {
    private static final String INVALID_ASSIGNMENT_NAME = "M@ath";
    private static final String INVALID_ASSIGNMENT_DEADLINE = "24/02/200000";

    private static final String VALID_ASSIGNMENT_NAME = ASSIGNMENT_MATH.getAssignmentName().toString();
    private static final String VALID_ASSIGNMENT_DEADLINE = ASSIGNMENT_MATH.getAssignmentDeadline().toString();

    @Test
    public void toModelType_validAssignmentDetails_returnsAssignment() throws Exception {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(ASSIGNMENT_MATH);
        assertEquals(ASSIGNMENT_MATH, assignment.toModelType());
    }

    @Test
    public void toModelType_invalidAssignmentName_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment =
                new JsonAdaptedAssignment(INVALID_ASSIGNMENT_NAME, VALID_ASSIGNMENT_DEADLINE, new ArrayList<String>(),
                        new ArrayList<String>(), false);
        String expectedMessage = AssignmentName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_nullAssignmentName_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(null, VALID_ASSIGNMENT_DEADLINE,
                new ArrayList<String>(), new ArrayList<String>(), false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, AssignmentName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_invalidAssignmentDeadline_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment =
                new JsonAdaptedAssignment(VALID_ASSIGNMENT_NAME, INVALID_ASSIGNMENT_DEADLINE, new ArrayList<String>(),
                        new ArrayList<String>(), false);
        String expectedMessage = AssignmentDeadline.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_nullAssignmentDeadline_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(VALID_ASSIGNMENT_NAME, null,
                new ArrayList<String>(), new ArrayList<String>(), false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, AssignmentDeadline.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }
}
