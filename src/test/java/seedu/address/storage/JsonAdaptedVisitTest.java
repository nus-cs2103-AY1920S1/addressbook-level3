package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.visit.EndDateTime;
import seedu.address.model.visit.StartDateTime;
import seedu.address.model.visit.Visit;

public class JsonAdaptedVisitTest {
    private static final String INVALID_DATE_1 = "R@chel";
    private static final String INVALID_DATE_2 = "12/12/12 1400";

    private static final String VALID_REMARK = "Remark";
    private static final String VALID_START = "10-12-2019 2100";
    private static final String VALID_END = "11-12-2019 2100";
    private static final String VALID_VISIT_TASK = "10-12-2019 2100";
    private static final Visit VALID_VISIT = BENSON.getVisits().get(0);

    private static final JsonAdaptedVisit VALID_JSON_VISIT =
            new JsonAdaptedVisit(BENSON.getVisits().get(0));

    @Test
    public void toModelType_validVisitDetails_returnsVisitTask() throws Exception {
        for (Visit visit : BENSON.getVisits()) {
            JsonAdaptedVisit visitTask = new JsonAdaptedVisit(visit);
            assertEquals(visit, visitTask.toModelType());
        }
    }

    @Test
    public void toModelType_nullVisitRemarkOrEndOrTasks_doNotThrowIllegalValueException() {
        List<JsonAdaptedVisitTask> visitTasks = new ArrayList<>();
        visitTasks.addAll(VALID_VISIT.getVisitTasks().stream()
                .map(JsonAdaptedVisitTask::new)
                .collect(Collectors.toList()));

        JsonAdaptedVisit visit = new JsonAdaptedVisit(null,
                VALID_START,
                VALID_END,
                visitTasks);
        assertDoesNotThrow(visit::toModelType);

        visit = new JsonAdaptedVisit(VALID_REMARK,
                VALID_START,
                null,
                visitTasks);
        assertDoesNotThrow(visit::toModelType);

        visit = new JsonAdaptedVisit(VALID_REMARK,
                VALID_START,
                VALID_END,
                null);
        assertDoesNotThrow(visit::toModelType);
    }

    @Test
    public void toModelType_nullVisitStart_throwsIllegalValueException() {
        List<JsonAdaptedVisitTask> visitTasks = new ArrayList<>();
        visitTasks.addAll(VALID_VISIT.getVisitTasks().stream()
                .map(JsonAdaptedVisitTask::new)
                .collect(Collectors.toList()));

        JsonAdaptedVisit visit = new JsonAdaptedVisit(VALID_REMARK,
                null,
                VALID_END,
                visitTasks);

        String expectedMessage = String.format(JsonAdaptedVisit.MISSING_FIELD_MESSAGE_FORMAT,
                StartDateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_invalidDates_throwsIllegalValueException() {
        List<JsonAdaptedVisitTask> visitTasks = new ArrayList<>();
        visitTasks.addAll(VALID_VISIT.getVisitTasks().stream()
                .map(JsonAdaptedVisitTask::new)
                .collect(Collectors.toList()));

        JsonAdaptedVisit visit = new JsonAdaptedVisit(VALID_REMARK,
                INVALID_DATE_1,
                VALID_END,
                visitTasks);
        assertThrows(IllegalValueException.class, StartDateTime.MESSAGE_CONSTRAINTS, visit::toModelType);

        visit = new JsonAdaptedVisit(VALID_REMARK,
                INVALID_DATE_2,
                VALID_END,
                visitTasks);
        assertThrows(IllegalValueException.class, StartDateTime.MESSAGE_CONSTRAINTS, visit::toModelType);

        visit = new JsonAdaptedVisit(VALID_REMARK,
                VALID_START,
                INVALID_DATE_1,
                visitTasks);
        assertThrows(IllegalValueException.class, EndDateTime.MESSAGE_CONSTRAINTS, visit::toModelType);

        visit = new JsonAdaptedVisit(VALID_REMARK,
                VALID_START,
                INVALID_DATE_2,
                visitTasks);
        assertThrows(IllegalValueException.class, EndDateTime.MESSAGE_CONSTRAINTS, visit::toModelType);

        visit = new JsonAdaptedVisit(VALID_REMARK,
                VALID_END,
                VALID_START,
                visitTasks);
        assertThrows(IllegalValueException.class, JsonAdaptedVisit.END_DATE_EARLIER_THAN_START_DATE,
                visit::toModelType);
    }
}
