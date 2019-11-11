package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.EditEventCommand.Builder.OPTION_DESCRIPTION;
import static seedu.address.logic.commands.EditEventCommand.Builder.OPTION_END_DATE_TIME;
import static seedu.address.logic.commands.EditEventCommand.Builder.OPTION_REMIND_DATE_TIME;
import static seedu.address.logic.commands.EditEventCommand.Builder.OPTION_START_DATE_TIME;
import static seedu.address.logic.commands.EditEventCommand.Builder.OPTION_TAGS;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DateTime;
import seedu.address.model.ModelData;
import seedu.address.model.ModelManager;
import seedu.address.model.events.EventSource;

class EditEventCommandTest {

    @Test
    void build_allParameters_success() {
        String[] indexes = new String[]{"1", "2", "3"};
        String description = "description";
        String start = "11/11/1111 11:00";
        String end = "11/11/1111 12:00";
        String remind = "11/11/1111 08:00";
        String[] tags = new String[]{"a", "b", "c"};
        assertDoesNotThrow(() -> EditEventCommand.newBuilder(null)
            .acceptPhrase(indexes[0])
            .acceptPhrase(indexes[1])
            .acceptPhrase(indexes[2])
            .acceptPhrase(OPTION_DESCRIPTION)
            .acceptPhrase(description)
            .acceptPhrase(OPTION_START_DATE_TIME)
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
    void execute_noIndexes_failure() {
        String description = "description";

        ModelManager model = new ModelManager();
        DateTime start = DateTime.now();
        List<EventSource> events = List.of(
            EventSource.newBuilder("a", start).build(),
            EventSource.newBuilder("b", start).build(),
            EventSource.newBuilder("c", start).build());

        model.setModelData(new ModelData(events, List.of()));
        assertEquals(3, model.getEvents().size());

        assertThrows(CommandException.class, () -> EditEventCommand.newBuilder(model)
            .acceptPhrase(OPTION_DESCRIPTION)
            .acceptPhrase(description)
            .build()
            .execute());

        List<EventSource> modelEvents = model.getEvents();
        assertEquals(3, modelEvents.size());
        assertEquals(events.get(0), modelEvents.get(0));
        assertEquals(events.get(1), modelEvents.get(1));
        assertEquals(events.get(2), modelEvents.get(2));
    }

    @Test
    void execute_noParameters_failure() {
        String[] indexes = new String[]{"0", "1", "2"};

        ModelManager model = new ModelManager();
        DateTime start = DateTime.now();
        List<EventSource> events = List.of(
            EventSource.newBuilder("a", start).build(),
            EventSource.newBuilder("b", start).build(),
            EventSource.newBuilder("c", start).build());

        model.setModelData(new ModelData(events, List.of()));
        assertEquals(3, model.getEvents().size());

        assertThrows(CommandException.class, () -> EditEventCommand.newBuilder(model)
            .acceptPhrase(indexes[0])
            .acceptPhrase(indexes[1])
            .acceptPhrase(indexes[2])
            .build()
            .execute());

        List<EventSource> modelEvents = model.getEvents();
        assertEquals(3, modelEvents.size());
        assertEquals(events.get(0), modelEvents.get(0));
        assertEquals(events.get(1), modelEvents.get(1));
        assertEquals(events.get(2), modelEvents.get(2));
    }

    /* Description */

    @Test
    void execute_editDescriptions_success() {
        String[] indexes = new String[]{"0", "1"};
        String description = "description";
        DateTime start = DateTime.now();
        List<EventSource> oldEvents = List.of(
            EventSource.newBuilder("a", start).setTags(List.of("1")).build(),
            EventSource.newBuilder("b", start).setTags(List.of("2")).build(),
            EventSource.newBuilder("c", start).build()
        );
        List<EventSource> newEvents = List.of(
            EventSource.newBuilder(description, start).setTags(List.of("1")).build(),
            EventSource.newBuilder(description, start).setTags(List.of("2")).build()
        );

        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(oldEvents, List.of()));
        assertEquals(3, model.getEvents().size());

        assertDoesNotThrow(() -> EditEventCommand.newBuilder(model)
            .acceptPhrase(indexes[0])
            .acceptPhrase(indexes[1])
            .acceptPhrase(OPTION_DESCRIPTION)
            .acceptPhrase(description)
            .build()
            .execute());

        List<EventSource> modelEvents = model.getEvents();
        assertEquals(3, modelEvents.size());
        assertEquals(newEvents.get(0), modelEvents.get(0));
        assertEquals(newEvents.get(1), modelEvents.get(1));
        assertEquals(oldEvents.get(2), modelEvents.get(2));
    }

    /* Start */

    @Test
    void execute_editStart_success() {
        String[] indexes = new String[]{"0", "1"};
        DateTime oldStart = DateTime.now();
        DateTime newStart = oldStart.plus(Duration.ofHours(1));
        List<EventSource> oldEvents = List.of(
            EventSource.newBuilder("a", oldStart).build(),
            EventSource.newBuilder("b", oldStart).build(),
            EventSource.newBuilder("c", oldStart).build()
        );
        List<EventSource> newEvents = List.of(
            EventSource.newBuilder("a", newStart).build(),
            EventSource.newBuilder("b", newStart).build()
        );

        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(oldEvents, List.of()));
        assertEquals(3, model.getEvents().size());

        assertDoesNotThrow(() -> EditEventCommand.newBuilder(model)
            .acceptPhrase(indexes[0])
            .acceptPhrase(indexes[1])
            .acceptPhrase(OPTION_START_DATE_TIME)
            .acceptPhrase(newStart.toUserString())
            .build()
            .execute());

        List<EventSource> modelEvents = model.getEvents();
        assertEquals(3, modelEvents.size());
        assertEquals(newEvents.get(0), modelEvents.get(0));
        assertEquals(newEvents.get(1), modelEvents.get(1));
        assertEquals(oldEvents.get(2), modelEvents.get(2));
    }

    /* End */

    @Test
    void execute_editEnd_success() {
        String[] indexes = new String[]{"0", "1"};
        DateTime start = DateTime.now();
        DateTime end = start.plus(Duration.ofHours(1));
        List<EventSource> oldEvents = List.of(
            EventSource.newBuilder("a", start).build(),
            EventSource.newBuilder("b", start).build(),
            EventSource.newBuilder("c", start).build()
        );
        List<EventSource> newEvents = List.of(
            EventSource.newBuilder("a", start).setEnd(end).build(),
            EventSource.newBuilder("b", start).setEnd(end).build()
        );

        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(oldEvents, List.of()));
        assertEquals(3, model.getEvents().size());

        assertDoesNotThrow(() -> EditEventCommand.newBuilder(model)
            .acceptPhrase(indexes[0])
            .acceptPhrase(indexes[1])
            .acceptPhrase(OPTION_END_DATE_TIME)
            .acceptPhrase(end.toUserString())
            .build()
            .execute());

        List<EventSource> modelEvents = model.getEvents();
        assertEquals(3, modelEvents.size());
        assertEquals(newEvents.get(0), modelEvents.get(0));
        assertEquals(newEvents.get(1), modelEvents.get(1));
        assertEquals(oldEvents.get(2), modelEvents.get(2));
    }

    /* Remind */

    @Test
    void execute_editRemind_success() {
        String[] indexes = new String[]{"0", "1"};
        DateTime start = DateTime.now();
        DateTime remind = start.plus(Duration.ofHours(-1));
        List<EventSource> oldEvents = List.of(
            EventSource.newBuilder("a", start).build(),
            EventSource.newBuilder("b", start).build(),
            EventSource.newBuilder("c", start).build()
        );
        List<EventSource> newEvents = List.of(
            EventSource.newBuilder("a", start).setRemind(remind).build(),
            EventSource.newBuilder("b", start).setRemind(remind).build()
        );

        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(oldEvents, List.of()));
        assertEquals(3, model.getEvents().size());

        assertDoesNotThrow(() -> EditEventCommand.newBuilder(model)
            .acceptPhrase(indexes[0])
            .acceptPhrase(indexes[1])
            .acceptPhrase(OPTION_REMIND_DATE_TIME)
            .acceptPhrase(remind.toUserString())
            .build()
            .execute());

        List<EventSource> modelEvents = model.getEvents();
        assertEquals(3, modelEvents.size());
        assertEquals(newEvents.get(0), modelEvents.get(0));
        assertEquals(newEvents.get(1), modelEvents.get(1));
        assertEquals(oldEvents.get(2), modelEvents.get(2));
    }

    /* Tags */

    @Test
    void execute_editTags_success() {
        String[] indexes = new String[]{"0", "1"};
        DateTime start = DateTime.now();
        List<String> tags = List.of("0", "1", "2");
        List<EventSource> oldEvents = List.of(
            EventSource.newBuilder("a", start).build(),
            EventSource.newBuilder("b", start).build(),
            EventSource.newBuilder("c", start).build()
        );
        List<EventSource> newEvents = List.of(
            EventSource.newBuilder("a", start).setTags(tags).build(),
            EventSource.newBuilder("b", start).setTags(tags).build()
        );

        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(oldEvents, List.of()));
        assertEquals(3, model.getEvents().size());

        assertDoesNotThrow(() -> EditEventCommand.newBuilder(model)
            .acceptPhrase(indexes[0])
            .acceptPhrase(indexes[1])
            .acceptPhrase(OPTION_TAGS)
            .acceptPhrase(tags.get(0))
            .acceptPhrase(tags.get(1))
            .acceptPhrase(tags.get(2))
            .build()
            .execute());

        List<EventSource> modelEvents = model.getEvents();
        assertEquals(3, modelEvents.size());
        assertEquals(newEvents.get(0), modelEvents.get(0));
        assertEquals(newEvents.get(1), modelEvents.get(1));
        assertEquals(oldEvents.get(2), modelEvents.get(2));
    }

    @Test
    void execute_editMultipleInvalidIndexes_failed() {
        String[] indexes = new String[]{"0", "1", "2"};
        String description = "description";

        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(
            List.of(
                EventSource.newBuilder("a", DateTime.now()).build(),
                EventSource.newBuilder("b", DateTime.now()).build()
            ), List.of()));
        assertEquals(2, model.getEvents().size());

        assertThrows(CommandException.class, () -> EditEventCommand.newBuilder(model)
            .acceptPhrase(indexes[0])
            .acceptPhrase(indexes[1])
            .acceptPhrase(indexes[2])
            .acceptPhrase(OPTION_DESCRIPTION)
            .acceptPhrase(description)
            .build()
            .execute());

        assertEquals(2, model.getEvents().size());
    }

    /* Duplicates */

    @Test
    void execute_editDuplicate_failure() {
        String[] indexes = new String[]{"0", "1", "2"};
        String description = "description";
        DateTime start = DateTime.now();
        List<EventSource> events = List.of(
            EventSource.newBuilder("a", start).build(),
            EventSource.newBuilder("b", start).build(),
            EventSource.newBuilder("c", start).build()
        );

        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(events, List.of()));
        assertEquals(3, model.getEvents().size());

        assertThrows(CommandException.class, () -> EditEventCommand.newBuilder(model)
            .acceptPhrase(indexes[0])
            .acceptPhrase(indexes[1])
            .acceptPhrase(OPTION_DESCRIPTION)
            .acceptPhrase(description)
            .build()
            .execute());

        List<EventSource> modelEvents = model.getEvents();
        assertEquals(3, modelEvents.size());
        assertEquals(events.get(0), modelEvents.get(0));
        assertEquals(events.get(1), modelEvents.get(1));
        assertEquals(events.get(2), modelEvents.get(2));
    }
}
