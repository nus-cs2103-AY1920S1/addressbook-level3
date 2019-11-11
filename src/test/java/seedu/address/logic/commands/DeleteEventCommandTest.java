package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.DeleteEventCommand.Builder.OPTION_TAGS;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DateTime;
import seedu.address.model.ModelData;
import seedu.address.model.ModelManager;
import seedu.address.model.events.EventSource;

class DeleteEventCommandTest {

    @Test
    void build_allParameters_success() {
        String[] indexes = new String[]{"0", "1", "2"};
        String[] tags = new String[]{"a", "b", "c"};
        assertDoesNotThrow(() -> DeleteEventCommand.newBuilder(null)
            .acceptPhrase(indexes[0])
            .acceptPhrase(indexes[1])
            .acceptPhrase(indexes[2])
            .acceptPhrase(OPTION_TAGS)
            .acceptPhrase(tags[0])
            .acceptPhrase(tags[1])
            .acceptPhrase(tags[2])
            .build());
    }

    @Test
    void execute_noParameters_failure() {
        ModelManager model = new ModelManager();
        DateTime start = DateTime.now();
        model.setModelData(new ModelData(
            List.of(
                EventSource.newBuilder("a", start).build(),
                EventSource.newBuilder("b", start).build(),
                EventSource.newBuilder("c", start).build()
            ), List.of()));
        assertEquals(3, model.getEvents().size());

        assertThrows(CommandException.class, () -> DeleteEventCommand.newBuilder(model)
            .build()
            .execute());

        assertEquals(3, model.getEvents().size());
    }

    /* Delete by Index */

    @Test
    void execute_deleteIndexes_success() {
        String[] indexes = new String[]{"1", "2", "3"};
        DateTime start = DateTime.now();
        List<EventSource> events = List.of(
            EventSource.newBuilder("a", start).build(),
            EventSource.newBuilder("b", start).build(),
            EventSource.newBuilder("c", start).build(),
            EventSource.newBuilder("d", start).build(),
            EventSource.newBuilder("e", start).build());

        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(events, List.of()));
        assertEquals(5, model.getEvents().size());

        assertDoesNotThrow(() -> DeleteEventCommand.newBuilder(model)
            .acceptPhrase(indexes[0])
            .acceptPhrase(indexes[1])
            .acceptPhrase(indexes[2])
            .build()
            .execute());

        // Delete 'b', 'c', 'd'
        List<EventSource> modelEvents = model.getEvents();
        assertEquals(2, modelEvents.size());
        assertEquals(events.get(0), modelEvents.get(0));
        assertEquals(events.get(4), modelEvents.get(1));
    }

    @Test
    void execute_deleteInvalidIndexes_failed() {
        String[] indexes = new String[]{"0", "1", "2"};
        DateTime start = DateTime.now();
        List<EventSource> events = List.of(
            EventSource.newBuilder("a", start).build(),
            EventSource.newBuilder("b", start).build());

        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(events, List.of()));
        assertEquals(2, model.getEvents().size());

        assertThrows(CommandException.class, () -> DeleteEventCommand.newBuilder(model)
            .acceptPhrase(indexes[0])
            .acceptPhrase(indexes[1])
            .acceptPhrase(indexes[2])
            .build().execute());

        assertEquals(2, model.getEvents().size());
    }

    /* Delete by Tags */

    @Test
    void execute_deleteTags_success() {
        String[] tags = new String[]{"3", "4"};
        DateTime start = DateTime.now();
        List<EventSource> events = List.of(
            EventSource.newBuilder("a", start).setTags(List.of("1", "2", "3")).build(),
            EventSource.newBuilder("b", start).setTags(List.of("2", "3", "4")).build(),
            EventSource.newBuilder("c", start).setTags(List.of("3", "4", "5")).build(),
            EventSource.newBuilder("d", start).setTags(List.of("4", "5", "6")).build(),
            EventSource.newBuilder("e", start).setTags(List.of("5", "6", "7")).build());

        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(events, List.of()));
        assertEquals(5, model.getEvents().size());

        assertDoesNotThrow(() -> DeleteEventCommand.newBuilder(model)
            .acceptPhrase(OPTION_TAGS)
            .acceptPhrase(tags[0])
            .acceptPhrase(tags[1])
            .build()
            .execute());

        // Delete 'b' and 'c'
        List<EventSource> modelEvents = model.getEvents();
        assertEquals(3, modelEvents.size());
        assertEquals(events.get(0), modelEvents.get(0));
        assertEquals(events.get(3), modelEvents.get(1));
        assertEquals(events.get(4), modelEvents.get(2));
    }

    /* Delete by Index and Tags */

    @Test
    void execute_deleteIndexesAndTags_success() {
        String[] indexes = new String[]{"0", "1"};
        String[] tags = new String[]{"3", "4"};
        DateTime start = DateTime.now();
        List<EventSource> events = List.of(
            EventSource.newBuilder("a", start).setTags(List.of("1", "2", "3")).build(),
            EventSource.newBuilder("b", start).setTags(List.of("2", "3", "4")).build(),
            EventSource.newBuilder("c", start).setTags(List.of("3", "4", "5")).build(),
            EventSource.newBuilder("d", start).setTags(List.of("4", "5", "6")).build(),
            EventSource.newBuilder("e", start).setTags(List.of("5", "6", "7")).build());

        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(events, List.of()));
        assertEquals(5, model.getEvents().size());

        assertDoesNotThrow(() -> DeleteEventCommand.newBuilder(model)
            .acceptPhrase(indexes[0])
            .acceptPhrase(indexes[1])
            .acceptPhrase(OPTION_TAGS)
            .acceptPhrase(tags[0])
            .acceptPhrase(tags[1])
            .build()
            .execute());

        // Delete 'b'
        List<EventSource> modelEvents = model.getEvents();
        assertEquals(4, modelEvents.size());
        assertEquals(events.get(0), modelEvents.get(0));
        assertEquals(events.get(2), modelEvents.get(1));
        assertEquals(events.get(3), modelEvents.get(2));
        assertEquals(events.get(4), modelEvents.get(3));
    }

    @Test
    void execute_deleteNoMatches_failure() {
        String[] indexes = new String[]{"0", "1"};
        String[] tags = new String[]{"5", "6"};
        DateTime start = DateTime.now();
        List<EventSource> events = List.of(
            EventSource.newBuilder("a", start).setTags(List.of("1", "2", "3")).build(),
            EventSource.newBuilder("b", start).setTags(List.of("2", "3", "4")).build(),
            EventSource.newBuilder("c", start).setTags(List.of("3", "4", "5")).build(),
            EventSource.newBuilder("d", start).setTags(List.of("4", "5", "6")).build(),
            EventSource.newBuilder("e", start).setTags(List.of("5", "6", "7")).build());

        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(events, List.of()));
        assertEquals(5, model.getEvents().size());

        assertThrows(CommandException.class, () -> DeleteEventCommand.newBuilder(model)
            .acceptPhrase(indexes[0])
            .acceptPhrase(indexes[1])
            .acceptPhrase(OPTION_TAGS)
            .acceptPhrase(tags[0])
            .acceptPhrase(tags[1])
            .build()
            .execute());

        // Check size
        assertEquals(5, model.getEvents().size());
    }
}
