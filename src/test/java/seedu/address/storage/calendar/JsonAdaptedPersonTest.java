package seedu.address.storage.calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.calendar.JsonAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.calendar.task.TaskDay;
import seedu.address.model.calendar.task.TaskDeadline;
import seedu.address.model.calendar.task.TaskDescription;
import seedu.address.model.calendar.task.TaskTime;
import seedu.address.model.calendar.task.TaskTitle;

public class JsonAdaptedPersonTest {
    private static final String INVALID_TASKTITLE = "R@chel";
    private static final String INVALID_TASKDAY = "+651234";
    private static final String INVALID_TASKTIME = " ";
    private static final String INVALID_TASKDEADLINE = "32-02-2019";
    private static final String INVALID_TASKTAG = "#friend";

    private static final String VALID_TASKTITLE = BENSON.getTaskTitle().toString();
    private static final String VALID_TASKDAY = BENSON.getTaskDay().toString();
    private static final String VALID_TASKDESCRIPTION = BENSON.getTaskDescription().toString();
    private static final String VALID_TASKTIME = BENSON.getTaskTime().toString();
    private static final String VALID_TASKDEADLINE = BENSON.getTaskDeadline().toString();
    private static final int VALID_WEEK = BENSON.getWeek();
    private static final List<JsonAdaptedTag> VALID_TASKTAGS = BENSON.getTaskTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validTaskDetails_returnsTask() throws Exception {
        JsonAdaptedTask task = new JsonAdaptedTask(BENSON);
        assertEquals(BENSON, task.toModelType());
    }

    @Test
    public void toModelType_invalidTaskTitle_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(INVALID_TASKTITLE, VALID_TASKDAY, VALID_TASKDESCRIPTION, VALID_TASKTIME,
                    VALID_TASKTAGS, VALID_TASKDEADLINE, VALID_WEEK, false);
        String expectedMessage = TaskTitle.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullTaskTitle_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(null, VALID_TASKDAY, VALID_TASKDESCRIPTION, VALID_TASKTIME,
            VALID_TASKTAGS, VALID_TASKDEADLINE, VALID_WEEK, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskTitle.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidTaskDay_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_TASKTITLE, INVALID_TASKDAY, VALID_TASKDESCRIPTION, VALID_TASKTIME,
                    VALID_TASKTAGS, VALID_TASKDEADLINE, VALID_WEEK, false);
        String expectedMessage = TaskDay.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullTaskDay_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_TASKTITLE, null, VALID_TASKDESCRIPTION, VALID_TASKTIME,
            VALID_TASKTAGS, VALID_TASKDEADLINE, VALID_WEEK, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskDay.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidTaskDeadline_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_TASKTITLE, VALID_TASKDAY, VALID_TASKDESCRIPTION, VALID_TASKTIME,
                    VALID_TASKTAGS, INVALID_TASKDEADLINE, VALID_WEEK, false);
        String expectedMessage = TaskDeadline.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullTaskDeadline_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_TASKTITLE, VALID_TASKDAY, VALID_TASKDESCRIPTION,
            VALID_TASKTIME, VALID_TASKTAGS, null, VALID_WEEK, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskDeadline.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullTaskDescription_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_TASKTITLE, VALID_TASKDAY, null, VALID_TASKTIME,
            VALID_TASKTAGS, VALID_TASKDEADLINE, VALID_WEEK, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskDescription.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidTaskTime_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_TASKTITLE, VALID_TASKDAY, VALID_TASKDESCRIPTION, INVALID_TASKTIME,
                    VALID_TASKTAGS, VALID_TASKDEADLINE, VALID_WEEK, false);
        String expectedMessage = TaskTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullTaskTime_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_TASKTITLE, VALID_TASKDAY, VALID_TASKDESCRIPTION, null,
            VALID_TASKTAGS, VALID_TASKDEADLINE, VALID_WEEK, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidTaskTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TASKTAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TASKTAG));
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_TASKTITLE, VALID_TASKDAY, VALID_TASKDESCRIPTION, VALID_TASKTIME,
                    invalidTags, VALID_TASKDEADLINE, VALID_WEEK, false);
        assertThrows(IllegalValueException.class, task::toModelType);
    }

}
