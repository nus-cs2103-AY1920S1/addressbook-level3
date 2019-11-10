package seedu.address.model.events;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import seedu.address.model.DateTime;
import seedu.address.model.exceptions.InvalidEventSourceException;

class EventSourceTest {

    @Test
    void construct_startAfterEqualsEnd_failure() {
        String description = "description";
        DateTime end = DateTime.now();
        DateTime start = end.plus(Duration.ofHours(1));

        // Start equals end
        assertThrows(InvalidEventSourceException.class, () -> EventSource.newBuilder(description, start)
            .setEnd(start)
            .build());

        // Start after end
        assertThrows(InvalidEventSourceException.class, () -> EventSource.newBuilder(description, start)
            .setEnd(end)
            .build());
    }

    @Test
    void construct_remindAfterStart_failure() {
        String description = "description";
        DateTime start = DateTime.now();
        DateTime remind = start.plus(Duration.ofHours(1));

        // Start after end
        assertThrows(InvalidEventSourceException.class, () -> EventSource.newBuilder(description, start)
            .setRemind(remind)
            .build());
    }

    @Test
    void equals_differentObject_failure() {
        String description = "description";
        DateTime start = DateTime.now();
        assertNotEquals(EventSource.newBuilder(description, start).build(), "");
    }
}
