package seedu.address.ui.stub;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.DateTime;
import seedu.address.model.ModelData;
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;

class UiManagerTest {

    @Test
    void onModelListChangeTest() {
        UiManagerStub uiManager = new UiManagerStub();
        List<EventSource> events = new ArrayList<>();
        List<TaskSource> tasks = new ArrayList<>();

        LocalDate date = LocalDate.parse("2019-11-05");
        Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        LocalDate date1 = LocalDate.parse("2019-11-04");
        Instant instant1 = date1.atStartOfDay(ZoneId.systemDefault()).toInstant();

        EventSource event = EventSource.newBuilder(
                "description",
                DateTime.newBuilder(instant).build())
                .build();
        EventSource event1 = EventSource.newBuilder(
                "description1",
                DateTime.newBuilder(instant1).build())
                .build();

        TaskSource task = TaskSource.newBuilder(
                "description")
                .build();
        TaskSource task1 = TaskSource.newBuilder(
                "description1")
                .build();

        events.add(event);
        events.add(event1);
        tasks.add(task);
        tasks.add(task1);

        ModelData modelLists = new ModelData(events, tasks);
        assertDoesNotThrow(() -> {
            ModelData lists = uiManager.overrideOnModelListChange(modelLists);
            assertEquals(lists.getEvents().get(0), event1);
            assertEquals(lists.getEvents().get(1), event);
            assertEquals(lists.getTasks().get(0), task);
            assertEquals(lists.getTasks().get(1), task1);
        });
    }
}
