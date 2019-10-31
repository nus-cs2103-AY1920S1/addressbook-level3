package cs.f10.t1.nursetraverse.storage;

import static cs.f10.t1.nursetraverse.testutil.Assert.assertThrows;
import static cs.f10.t1.nursetraverse.testutil.TypicalPatients.BENSON;
import static cs.f10.t1.nursetraverse.testutil.TypicalVisitTasks.SAMPLE_VISIT_TASK_NO_DETAIL;
import static cs.f10.t1.nursetraverse.testutil.TypicalVisitTasks.SAMPLE_VISIT_TASK_SPECIAL_CHARS;
import static cs.f10.t1.nursetraverse.testutil.TypicalVisitTasks.SAMPLE_VISIT_TASK_WITH_DETAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.commons.exceptions.IllegalValueException;
import cs.f10.t1.nursetraverse.model.visittask.Detail;
import cs.f10.t1.nursetraverse.model.visittodo.VisitTodo;

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
        JsonAdaptedVisitTask patient = new JsonAdaptedVisitTask(null, "", true);
        String expectedMessage = String.format(JsonAdaptedVisitTask.MISSING_FIELD_MESSAGE_FORMAT,
                VisitTodo.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullDetail_throwsIllegalValueException() {
        JsonAdaptedVisitTask patient = new JsonAdaptedVisitTask(new JsonAdaptedVisitTodo("Work"),
                null, true);
        String expectedMessage = String.format(JsonAdaptedVisitTask.MISSING_FIELD_MESSAGE_FORMAT,
                Detail.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullDone_throwsIllegalValueException() {
        JsonAdaptedVisitTask patient = new JsonAdaptedVisitTask(new JsonAdaptedVisitTodo("Work"),
                "", null);
        String expectedMessage = String.format(JsonAdaptedVisitTask.MISSING_FIELD_MESSAGE_FORMAT,
                "IsDone");
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }
}
