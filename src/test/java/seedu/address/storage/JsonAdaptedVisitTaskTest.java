package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalVisitTasks.SAMPLE_VISIT_TASK_NO_DETAIL;
import static seedu.address.testutil.TypicalVisitTasks.SAMPLE_VISIT_TASK_SPECIAL_CHARS;
import static seedu.address.testutil.TypicalVisitTasks.SAMPLE_VISIT_TASK_WITH_DETAIL;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.visittask.Detail;
import seedu.address.model.visittodo.VisitTodo;

public class JsonAdaptedVisitTaskTest {
    private static final String INVALID_VISIT_TODO = " ";

    private static final List<JsonAdaptedVisitTodo> VALID_VISIT_TODOS = BENSON.getVisitTodos().stream()
            .map(JsonAdaptedVisitTodo::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validVisitTaskDetails_returnsVisitTask() throws Exception {
        JsonAdaptedVisitTask visitTask = new JsonAdaptedVisitTask(SAMPLE_VISIT_TASK_NO_DETAIL);
        assertEquals(SAMPLE_VISIT_TASK_NO_DETAIL, visitTask.toModelType());

        JsonAdaptedVisitTask visitTask2 = new JsonAdaptedVisitTask(SAMPLE_VISIT_TASK_WITH_DETAIL);
        assertEquals(SAMPLE_VISIT_TASK_WITH_DETAIL, visitTask2.toModelType());

        JsonAdaptedVisitTask visitTask3 = new JsonAdaptedVisitTask(SAMPLE_VISIT_TASK_SPECIAL_CHARS);
        assertEquals(SAMPLE_VISIT_TASK_SPECIAL_CHARS, visitTask3.toModelType());
    }

    @Test
    public void toModelType_nullVisitTodo_throwsIllegalValueException() {
        JsonAdaptedVisitTask person = new JsonAdaptedVisitTask(null, "", true);
        String expectedMessage = String.format(JsonAdaptedVisitTask.MISSING_FIELD_MESSAGE_FORMAT,
                VisitTodo.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDetail_throwsIllegalValueException() {
        JsonAdaptedVisitTask person = new JsonAdaptedVisitTask(new JsonAdaptedVisitTodo("Work"),
                null, true);
        String expectedMessage = String.format(JsonAdaptedVisitTask.MISSING_FIELD_MESSAGE_FORMAT,
                Detail.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDone_throwsIllegalValueException() {
        JsonAdaptedVisitTask person = new JsonAdaptedVisitTask(new JsonAdaptedVisitTodo("Work"),
                "", null);
        String expectedMessage = String.format(JsonAdaptedVisitTask.MISSING_FIELD_MESSAGE_FORMAT,
                "IsDone");
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
}
