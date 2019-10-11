package seedu.address.storage;

import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.deadline.DueDate;
import seedu.address.model.deadline.Task;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.Question;
import seedu.address.model.flashcard.Rating;

import javax.xml.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedDeadline.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

public class JsonAdaptedDeadlineTest {
    private static final String INVALID_TASK_STR = " ";
    private static final String INVALID_DUEDATE_STR = " ";

    private static final String VALID_TASK_STR = "Complete Deadline Scheduler.";
    private static final String VALID_DUEDATE_STR = "01/10/2019";
    private static final Task VALID_TASK = new Task(VALID_TASK_STR);
    private static final DueDate VALID_DUEDATE = new DueDate(VALID_DUEDATE_STR);
    public static final Deadline DEADLINE = new Deadline(VALID_TASK, VALID_DUEDATE);

    @Test
    public void toModelType_validDeadlineDetails_returnsDeadline() throws Exception {
        JsonAdaptedDeadline deadline = new JsonAdaptedDeadline(DEADLINE);
        assertEquals(DEADLINE.toString(), deadline.toModelType().toString());
    }

    @Test
    public void toModelType_invalidTask_throwsIllegalValueException() {
        JsonAdaptedDeadline deadline = new JsonAdaptedDeadline(INVALID_TASK_STR, VALID_DUEDATE_STR);
        String expectedMessage = Task.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, deadline::toModelType);
    }

    @Test
    public void toModelType_nullTask_throwsIllegalValueException() {
        JsonAdaptedDeadline deadline = new JsonAdaptedDeadline(null, VALID_DUEDATE_STR);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Task.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, deadline::toModelType);
    }

    @Test
    public void toModelType_invalidDueDate_throwsIllegalValueException() {
        JsonAdaptedDeadline deadline = new JsonAdaptedDeadline(VALID_TASK_STR, INVALID_DUEDATE_STR);
        String expectedMessage = DueDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, deadline::toModelType);
    }

    @Test
    public void toModelType_nullDueDate_throwsIllegalValueException() {
        JsonAdaptedDeadline deadline = new JsonAdaptedDeadline(VALID_TASK_STR, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DueDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, deadline::toModelType);
    }

}
