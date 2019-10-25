package seedu.jarvis.storage.planner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.planner.tasks.Event;
import seedu.jarvis.model.planner.tasks.Task;

/**
 * Tests the behaviour of {@code JsonAdaptedEvent}.
 */
public class JsonAdaptedEventTest {

    @Test
    public void toModelType_returnsEvent() throws Exception {
        Event event = new Event("description", LocalDate.parse("11/11/2019", Task.getDateFormat()),
                LocalDate.parse("29/11/2019", Task.getDateFormat()));
        JsonAdaptedEvent jsonAdaptedEvent = new JsonAdaptedEvent(event);
        assertEquals(event, jsonAdaptedEvent.toModelType());
    }
}
