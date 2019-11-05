package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import seedu.address.commons.stub.UiManagerStub;
import seedu.address.model.DateTime;
import seedu.address.model.ModelLists;
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

class UiManagerTest {

    @Test
    void onModelListChangeTest() {
        UiManagerStub uiManager = new UiManagerStub();
        List<EventSource> events = new ArrayList<>();
        List<TaskSource> tasks = new ArrayList<>();

        LocalDate date = LocalDate.parse("2019-11-04");
        Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        LocalDate date1 = LocalDate.parse("2019-11-05");
        Instant instant1 = date1.atStartOfDay(ZoneId.systemDefault()).toInstant();

        events.add(EventSource.newBuilder(
                "description",
                DateTime.newBuilder(instant).build())
                .build());
        events.add(EventSource.newBuilder(
                "description1",
                DateTime.newBuilder(instant1).build())
                .build());

        tasks.add(TaskSource.newBuilder(
                "description")
                .build());
        tasks.add(TaskSource.newBuilder(
                "description1")
                .build());

        ModelLists modelLists = new ModelLists(events, tasks);
        assertDoesNotThrow(() -> uiManager.onModelListChange(modelLists));
    }

}
