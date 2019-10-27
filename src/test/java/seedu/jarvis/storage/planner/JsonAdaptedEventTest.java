package seedu.jarvis.storage.planner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.planner.TypicalTasks.EVENT;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.planner.tasks.Event;
import seedu.jarvis.testutil.planner.TaskBuilder;

/**
 * Tests the behaviour of {@code JsonAdaptedEvent}.
 */
public class JsonAdaptedEventTest {

    @Test
    public void toModelType_returnsEvent() throws Exception {
        JsonAdaptedEvent jsonAdaptedEvent = new JsonAdaptedEvent(EVENT);
        assertEquals(EVENT, jsonAdaptedEvent.toModelType());
    }

    @Test
    public void toModelType_nonNullPriorityAndFrequencyWithTags_returnsEvent() throws Exception {
        Event event = new TaskBuilder(EVENT).withPriority("HIGH").withFrequency("DAILY").withTags("Todo")
                .buildEvent();
        JsonAdaptedEvent jsonAdaptedEvent = new JsonAdaptedEvent(event);
        assertEquals(event, jsonAdaptedEvent.toModelType());
    }
}
