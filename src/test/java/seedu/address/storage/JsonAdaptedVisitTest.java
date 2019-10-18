package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.datetime.EndDateTime;
import seedu.address.model.datetime.StartDateTime;
import seedu.address.model.visit.Visit;

public class JsonAdaptedVisitTest {
    private static final String INVALID_DATE_1 = "R@chel";
    private static final String INVALID_DATE_2 = "12/12/12 1400";

    private static final String VALID_REMARK = "Remark";
    private static final String VALID_START = "10-12-2019 2100";
    private static final String VALID_END = "11-12-2019 2100";
    private static final String VALID_VISIT_TASK = "10-12-2019 2100";
    private static final Visit VALID_VISIT = BENSON.getVisits().get(0);
    private static final Visit VALID_COMPLETED_VISIT = CARL.getVisits().get(0);

    private static final JsonAdaptedVisit VALID_JSON_VISIT =
            new JsonAdaptedVisit(BENSON.getVisits().get(0));

    @Test
    public void toModelType_validVisitDetailsAndNullEnd_returnsVisitTask() throws Exception {
        for (Visit visit : BENSON.getVisits()) {
            JsonAdaptedVisit visitTask = new JsonAdaptedVisit(visit);
            assertEquals(visit, visitTask.toModelType(BENSON));
        }
    }

    @Test
    public void toModelType_validVisitDetailsAndNonNullEnd_returnsVisitTask() throws Exception {
        for (Visit visit : CARL.getVisits()) {
            JsonAdaptedVisit visitTask = new JsonAdaptedVisit(visit);
            assertEquals(visit, visitTask.toModelType(CARL));
        }
    }

    /**
     * Returns a list of valid JsonAdaptedVisitTasks for testing purposes.
     */
    private List<JsonAdaptedVisitTask> generateValidJsonAdaptedVisitTasks() {
        List<JsonAdaptedVisitTask> visitTasks = new ArrayList<>();
        visitTasks.addAll(VALID_VISIT.getVisitTasks().stream()
                .map(JsonAdaptedVisitTask::new)
                .collect(Collectors.toList()));
        return visitTasks;
    }

    @Test
    public void toModelType_nullRemark_doNotThrowIllegalValueException() {
        List<JsonAdaptedVisitTask> visitTasks = generateValidJsonAdaptedVisitTasks();
        final JsonAdaptedVisit visit = new JsonAdaptedVisit(null,
                VALID_START,
                VALID_END,
                visitTasks);
        assertDoesNotThrow(() -> {
            visit.toModelType(BENSON);
        });
    }

    @Test
    public void toModelType_nullVisitStart_throwsIllegalValueException() {
        List<JsonAdaptedVisitTask> visitTasks = generateValidJsonAdaptedVisitTasks();

        JsonAdaptedVisit visit = new JsonAdaptedVisit(VALID_REMARK,
                null,
                VALID_END,
                visitTasks);

        String expectedMessage = String.format(JsonAdaptedVisit.MISSING_FIELD_MESSAGE_FORMAT,
                StartDateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> {
            visit.toModelType(BENSON);
        });
    }

    @Test
    public void toModelType_nullEndDateTime_doNotThrowIllegalValueException() {
        List<JsonAdaptedVisitTask> visitTasks = generateValidJsonAdaptedVisitTasks();
        final JsonAdaptedVisit visit = new JsonAdaptedVisit(VALID_REMARK,
                VALID_START,
                null,
                visitTasks);
        assertDoesNotThrow(() -> {
            visit.toModelType(BENSON);
        });
    }

    @Test
    public void toModelType_nullVisitTasks_doNotThrowIllegalValueException() {
        List<JsonAdaptedVisitTask> visitTasks = generateValidJsonAdaptedVisitTasks();
        final JsonAdaptedVisit visit = new JsonAdaptedVisit(VALID_REMARK,
                VALID_START,
                VALID_END,
                null);
        assertDoesNotThrow(() -> {
            visit.toModelType(BENSON);
        });
    }

    @Test
    public void toModelType_startDateNotDate_throwsIllegalValueException() {
        List<JsonAdaptedVisitTask> visitTasks = generateValidJsonAdaptedVisitTasks();

        final JsonAdaptedVisit visit = new JsonAdaptedVisit(VALID_REMARK,
                INVALID_DATE_1,
                VALID_END,
                visitTasks);
        assertThrows(IllegalValueException.class, StartDateTime.MESSAGE_CONSTRAINTS, () -> {
            visit.toModelType(BENSON);
        });
    }

    @Test
    public void toModelType_startDateInvalidFormat_throwsIllegalValueException() {
        List<JsonAdaptedVisitTask> visitTasks = generateValidJsonAdaptedVisitTasks();

        final JsonAdaptedVisit visit = new JsonAdaptedVisit(VALID_REMARK,
                INVALID_DATE_2,
                VALID_END,
                visitTasks);
        assertThrows(IllegalValueException.class, StartDateTime.MESSAGE_CONSTRAINTS, () -> {
            visit.toModelType(BENSON);
        });
    }

    @Test
    public void toModelType_endDateNotDate_throwsIllegalValueException() {
        List<JsonAdaptedVisitTask> visitTasks = generateValidJsonAdaptedVisitTasks();

        final JsonAdaptedVisit visit = new JsonAdaptedVisit(VALID_REMARK,
                VALID_START,
                INVALID_DATE_1,
                visitTasks);
        assertThrows(IllegalValueException.class, EndDateTime.MESSAGE_CONSTRAINTS, () -> {
            visit.toModelType(BENSON);
        });
    }

    @Test
    public void toModelType_endDateInvalidFormat_throwsIllegalValueException() {
        List<JsonAdaptedVisitTask> visitTasks = generateValidJsonAdaptedVisitTasks();

        final JsonAdaptedVisit visit = new JsonAdaptedVisit(VALID_REMARK,
                VALID_START,
                INVALID_DATE_2,
                visitTasks);
        assertThrows(IllegalValueException.class, EndDateTime.MESSAGE_CONSTRAINTS, () -> {
            visit.toModelType(BENSON);
        });
    }

    @Test
    public void toModelType_endDateEarlierThanStartDate_throwsIllegalValueException() {
        List<JsonAdaptedVisitTask> visitTasks = generateValidJsonAdaptedVisitTasks();

        final JsonAdaptedVisit visit = new JsonAdaptedVisit(VALID_REMARK,
                VALID_END,
                VALID_START,
                visitTasks);
        assertThrows(IllegalValueException.class, JsonAdaptedVisit.END_DATE_EARLIER_THAN_START_DATE, () -> {
            visit.toModelType(BENSON);
        });
    }
}
