package seedu.jarvis.storage.planner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.planner.tasks.Deadline;
import seedu.jarvis.model.planner.tasks.Task;

/**
 * Tests the behaviour of {@code JsonAdaptedDeadline}.
 */
public class JsonAdaptedDeadlineTest {

    @Test
    public void toModelType_returnsDeadline() throws Exception {
        Deadline deadline = new Deadline("description", LocalDate.parse("29/11/2019", Task.getDateFormat()));
        JsonAdaptedDeadline jsonAdaptedDeadline = new JsonAdaptedDeadline(deadline);
        assertEquals(deadline, jsonAdaptedDeadline.toModelType());

    }
}
