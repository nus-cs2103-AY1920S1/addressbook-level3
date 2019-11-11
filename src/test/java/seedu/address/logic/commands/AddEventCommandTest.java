package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.AddEventCommand.Builder.OPTION_END_DATE_TIME;
import static seedu.address.logic.commands.AddEventCommand.Builder.OPTION_REMIND_DATE_TIME;
import static seedu.address.logic.commands.AddEventCommand.Builder.OPTION_TAGS;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DateTime;
import seedu.address.model.ModelManager;
import seedu.address.model.events.EventSource;

class AddEventCommandTest {

    @Test
    void build_allParameters_success() {
        String description = "description";
        String start = "11/11/1111 11:00";
        String end = "11/11/1111 12:00";
        String remind = "11/11/1111 08:00";
        String[] tags = new String[] {"a", "b", "c"};
        assertDoesNotThrow(() -> AddEventCommand.newBuilder(null)
            .acceptPhrase(description)
            .acceptPhrase(start)
            .acceptPhrase(OPTION_END_DATE_TIME)
            .acceptPhrase(end)
            .acceptPhrase(OPTION_REMIND_DATE_TIME)
            .acceptPhrase(remind)
            .acceptPhrase(OPTION_TAGS)
            .acceptPhrase(tags[0])
            .acceptPhrase(tags[1])
            .acceptPhrase(tags[2])
            .build());
    }

    @Test
    void execute_addOne_success() {
        String description = "description";
        DateTime start = DateTime.now();
        ModelManager model = new ModelManager();
        assertEquals(0, model.getEvents().size());

        assertDoesNotThrow(() -> AddEventCommand.newBuilder(model)
            .acceptPhrase(description)
            .acceptPhrase(start.toUserString())
            .build()
            .execute());

        List<EventSource> modelEvents = model.getEvents();
        assertEquals(1, modelEvents.size());
        assertEquals(EventSource.newBuilder(description, start).build(), modelEvents.get(0));
    }

    @Test
    void execute_addDuplicate_failure() {
        String description = "description";
        DateTime start = DateTime.now();
        ModelManager model = new ModelManager();
        assertEquals(0, model.getEvents().size());

        assertDoesNotThrow(() -> AddEventCommand.newBuilder(model)
            .acceptPhrase(description)
            .acceptPhrase(start.toUserString())
            .build()
            .execute());

        // Check size
        assertEquals(1, model.getEvents().size());

        assertThrows(CommandException.class, () -> AddEventCommand.newBuilder(model)
            .acceptPhrase(description)
            .acceptPhrase(start.toUserString())
            .build()
            .execute());

        // Check size
        assertEquals(1, model.getEvents().size());
    }
}
