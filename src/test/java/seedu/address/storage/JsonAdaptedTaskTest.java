package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.PRINT_POSTERS;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Name;
import seedu.address.model.task.TaskStatus;

public class JsonAdaptedTaskTest {
    private static final String INVALID_TASK_NAME = "R@eview Code";
    private static final String INVALID_TASK_STATUS = "pending";
    private static final String INVALID_TAG = "#urgent";

    private static final String VALID_TASK_NAME = PRINT_POSTERS.getName().toString();
    private static final TaskStatus VALID_TASK_STATUS = PRINT_POSTERS.getTaskStatus();
    private static final List<JsonAdaptedTag> VALID_TAGS = PRINT_POSTERS.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validTaskDetails_returnsTask() throws Exception {
        JsonAdaptedTask task = new JsonAdaptedTask(PRINT_POSTERS);
        assertEquals(PRINT_POSTERS, task.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(INVALID_TASK_NAME, VALID_TASK_STATUS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(null, VALID_TASK_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidTaskStatus_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_TASK_NAME, TaskStatus.valueOf(INVALID_TASK_STATUS), VALID_TAGS);
        assertThrows(IllegalValueException.class, task::toModelType);
    }

    @Test
    public void toModelType_nullTaskStatus_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_TASK_NAME, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_TASK_NAME, VALID_TASK_STATUS, invalidTags);
        assertThrows(IllegalValueException.class, task::toModelType);
    }

}
