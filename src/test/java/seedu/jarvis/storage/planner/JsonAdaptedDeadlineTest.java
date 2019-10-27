package seedu.jarvis.storage.planner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.planner.TypicalTasks.DEADLINE;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.planner.tasks.Deadline;
import seedu.jarvis.testutil.planner.TaskBuilder;

/**
 * Tests the behaviour of {@code JsonAdaptedDeadline}.
 */
public class JsonAdaptedDeadlineTest {

    @Test
    public void toModelType_nullPriorityAndFrequencyNoTags_returnsDeadline() throws Exception {
        JsonAdaptedDeadline jsonAdaptedDeadline = new JsonAdaptedDeadline(DEADLINE);
        assertEquals(DEADLINE, jsonAdaptedDeadline.toModelType());
    }

    @Test
    public void toModelType_nonNullPriorityAndFrequencyWithTags_returnsDeadline() throws Exception {
        Deadline deadline = new TaskBuilder(DEADLINE).withPriority("HIGH").withFrequency("DAILY").withTags("Todo")
                .buildDeadline();
        JsonAdaptedDeadline jsonAdaptedDeadline = new JsonAdaptedDeadline(deadline);
        assertEquals(deadline, jsonAdaptedDeadline.toModelType());
    }
}
