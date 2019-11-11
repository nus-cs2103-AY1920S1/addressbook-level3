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
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;

public class CalendarPanelTest {

    @Test
    void combineList() {
        List<EventSource> events = new ArrayList<>();
        List<TaskSource> tasks = new ArrayList<>();

        LocalDate date = LocalDate.parse("2019-11-07");
        Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        LocalDate date1 = LocalDate.parse("2019-11-05");
        Instant instant1 = date1.atStartOfDay(ZoneId.systemDefault()).toInstant();
        LocalDate date2 = LocalDate.parse("2019-11-06");
        Instant instant2 = date2.atStartOfDay(ZoneId.systemDefault()).toInstant();
        LocalDate date3 = LocalDate.parse("2019-11-04");
        Instant instant3 = date3.atStartOfDay(ZoneId.systemDefault()).toInstant();


        EventSource event = EventSource.newBuilder(
                "description",
                DateTime.newBuilder(instant).build())
                .build();
        EventSource event1 = EventSource.newBuilder(
                "description1",
                DateTime.newBuilder(instant1).build())
                .build();

        TaskSource task = TaskSource.newBuilder(
                "description2")
                .setDueDate(DateTime.newBuilder(instant2).build())
                .build();
        TaskSource task1 = TaskSource.newBuilder(
                "description3")
                .setDueDate(DateTime.newBuilder(instant3).build())
                .build();

        events.add(event1);
        events.add(event);
        tasks.add(task1);
        tasks.add(task);

        CalendarPanelStub calendarPanel = new CalendarPanelStub();

        assertDoesNotThrow(() -> {
            List<Object> lists = calendarPanel.combineList(events, tasks);
            assertEquals(lists.get(0), task1);
            assertEquals(lists.get(1), event1);
            assertEquals(lists.get(2), task);
            assertEquals(lists.get(3), event);
        });

    }
}
