package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;

//@@author bruceskellator

public class ModelDataTest {

    @Test
    void equals_equalModelDataObjects_success() {
        // Test if "empty" modelDatas are equal
        ModelData modelData = new ModelData();
        assertEquals(modelData, modelData);
        assertEquals(modelData, new ModelData());

        // Test if modelDatas with value-equal (but not reference-equal) eventsources with
        // description and start fields and tasksources with description are considered as equal
        ArrayList<EventSource> events1 = new ArrayList<>();
        ArrayList<EventSource> events2 = new ArrayList<>();
        String description = "event ";
        DateTime start;
        for (int i = 1; i <= 5; i++) {
            start = DateTime.now();
            events1.add(EventSource.newBuilder(description + i, start).build());
            events2.add(EventSource.newBuilder(description + i, start).build());
        }

        ArrayList<TaskSource> tasks1 = new ArrayList<>();
        ArrayList<TaskSource> tasks2 = new ArrayList<>();
        description = "task ";
        for (int i = 1; i <= 5; i++) {
            tasks1.add(TaskSource.newBuilder(description + i).build());
            tasks2.add(TaskSource.newBuilder(description + i).build());
        }

        modelData = new ModelData(events1, tasks1);
        assertEquals(modelData, modelData);
        assertEquals(modelData, new ModelData(events2, tasks2));

        // Test if modelDatas with value-equal (but not reference-equal) eventsources
        // with description, start, end, tags and remind fields and tasksources
        // with description, due, tags and isDone fields are considered as equal
        start = DateTime.now();
        DateTime remind = DateTime.newBuilder(1, 1, 2019, 0,
                0, ZoneId.systemDefault()).build();
        events1.clear();
        events2.clear();
        tasks1.clear();
        tasks2.clear();
        DateTime end = DateTime.newBuilder(1, 11, 2020, 0,
                0, ZoneId.systemDefault()).build();
        events1.add(EventSource.newBuilder("event", start)
                .setEnd(end)
                .setRemind(remind)
                .setTags(Arrays.asList("Horo", "test"))
                .build());
        events2.add(EventSource.newBuilder("event", start)
                .setEnd(end)
                .setRemind(remind)
                .setTags(Arrays.asList("test", "Horo"))
                .build());
        tasks1.add(TaskSource.newBuilder("task")
                .setDueDate(end)
                .setDone(true)
                .setTags((Arrays.asList("task", "test")))
                .build());
        tasks2.add(TaskSource.newBuilder("task")
                .setDueDate(end)
                .setDone(true)
                .setTags((Arrays.asList("task", "test")))
                .build());

        modelData = new ModelData(events1, tasks1);
        assertEquals(modelData, modelData);
        assertEquals(modelData, new ModelData(events2, tasks2));
    }

    @Test
    void equals_unequalModelDataObjects_failure() {
        DateTime start = DateTime.now();
        ArrayList<EventSource> events1 = new ArrayList<>();
        ArrayList<EventSource> events2 = new ArrayList<>();
        ArrayList<TaskSource> tasks1 = new ArrayList<>();
        ArrayList<TaskSource> tasks2 = new ArrayList<>();

        // Test if EventSources with different description result in unequal ModelData objects
        events1.add(EventSource.newBuilder("event", start).build());
        events2.add(EventSource.newBuilder("Event", start).build());
        tasks1.add(TaskSource.newBuilder("tasks").build());
        tasks2.add(TaskSource.newBuilder("tasks").build());
        assertNotEquals(new ModelData(events1, tasks1), new ModelData(events2, tasks2));

        // Test if TaskSources with different description result in unequal ModelData objects
        events1.clear();
        events2.clear();
        tasks1.clear();
        tasks2.clear();
        events1.add(EventSource.newBuilder("event", start).build());
        events2.add(EventSource.newBuilder("event", start).build());
        tasks1.add(TaskSource.newBuilder("task 1").build());
        tasks2.add(TaskSource.newBuilder("task 2").build());
        assertNotEquals(new ModelData(events1, tasks1), new ModelData(events2, tasks2));

        // Test if TaskSources with different due dates result in unequal ModelData objects
        events1.clear();
        events2.clear();
        tasks1.clear();
        tasks2.clear();
        events1.add(EventSource.newBuilder("event", start).build());
        events2.add(EventSource.newBuilder("event", start).build());
        tasks1.add(TaskSource.newBuilder("task")
                .setDueDate(DateTime.newBuilder(1, 11, 2020, 0,
                        0, ZoneId.systemDefault()).build())
                .setTags(Arrays.asList("Horo"))
                .setDone(false)
                .build());
        tasks2.add(TaskSource.newBuilder("task")
                .setDueDate(DateTime.newBuilder(2, 1, 2020, 0,
                        0, ZoneId.systemDefault()).build())
                .setTags(Arrays.asList("Horo"))
                .setDone(false)
                .build());
        assertNotEquals(new ModelData(events1, tasks1), new ModelData(events2, tasks2));

        // Test if TaskSources with different tags result in unequal ModelData objects
        events1.clear();
        events2.clear();
        tasks1.clear();
        tasks2.clear();
        events1.add(EventSource.newBuilder("event", start).build());
        events2.add(EventSource.newBuilder("event", start).build());
        tasks1.add(TaskSource.newBuilder("task")
                .setDueDate(DateTime.newBuilder(1, 11, 2020, 0,
                        0, ZoneId.systemDefault()).build())
                .setTags(Arrays.asList("Horo"))
                .setDone(false)
                .build());
        tasks2.add(TaskSource.newBuilder("task")
                .setDueDate(DateTime.newBuilder(1, 11, 2020, 0,
                        0, ZoneId.systemDefault()).build())
                .setTags(Arrays.asList("Duke"))
                .setDone(false)
                .build());
        assertNotEquals(new ModelData(events1, tasks1), new ModelData(events2, tasks2));

        // Test if TaskSources with different done states result in unequal ModelData objects
        events1.clear();
        events2.clear();
        tasks1.clear();
        tasks2.clear();
        events1.add(EventSource.newBuilder("event", start).build());
        events2.add(EventSource.newBuilder("event", start).build());
        tasks1.add(TaskSource.newBuilder("task")
                .setDueDate(DateTime.newBuilder(1, 11, 2020, 0,
                        0, ZoneId.systemDefault()).build())
                .setTags(Arrays.asList("Horo"))
                .setDone(false)
                .build());
        tasks2.add(TaskSource.newBuilder("task")
                .setDueDate(DateTime.newBuilder(1, 11, 2020, 0,
                        0, ZoneId.systemDefault()).build())
                .setTags(Arrays.asList("Horo"))
                .setDone(true)
                .build());
        assertNotEquals(new ModelData(events1, tasks1), new ModelData(events2, tasks2));

        // Test if EventSources with different start times result in unequal ModelData objects
        events1.clear();
        events2.clear();
        tasks1.clear();
        tasks2.clear();
        events1.add(EventSource.newBuilder("event", DateTime.newBuilder(1, 11, 2020, 0,
                0, ZoneId.systemDefault()).build()).build());
        events2.add(EventSource.newBuilder("event", DateTime.newBuilder(2, 11, 2020, 0,
                0, ZoneId.systemDefault()).build()).build());
        tasks1.add(TaskSource.newBuilder("tasks").build());
        tasks2.add(TaskSource.newBuilder("tasks").build());
        assertNotEquals(new ModelData(events1, tasks1), new ModelData(events2, tasks2));

        // Test if EventSources with different end times result in unequal ModelData objects
        events1.clear();
        events2.clear();
        tasks1.clear();
        tasks2.clear();
        events1.add(EventSource.newBuilder("event", DateTime.now())
                .setEnd(DateTime.newBuilder(1, 12, 2020, 0,
                        0, ZoneId.systemDefault()).build())
                .setTags(Arrays.asList("test", "Horo"))
                .setRemind(DateTime.newBuilder(1, 12, 2018, 0,
                        0, ZoneId.systemDefault()).build())
                .build());
        events2.add(EventSource.newBuilder("event", DateTime.now())
                .setEnd(DateTime.newBuilder(2, 12, 2020, 0,
                        0, ZoneId.systemDefault()).build())
                .setTags(Arrays.asList("test", "Horo"))
                .setRemind(DateTime.newBuilder(1, 12, 2018, 0,
                        0, ZoneId.systemDefault()).build())
                .build());
        tasks1.add(TaskSource.newBuilder("tasks").build());
        tasks2.add(TaskSource.newBuilder("tasks").build());
        assertNotEquals(new ModelData(events1, tasks1), new ModelData(events2, tasks2));

        // Test if EventSources with different remind time result in unequal ModelData objects
        events1.clear();
        events2.clear();
        tasks1.clear();
        tasks2.clear();
        events1.add(EventSource.newBuilder("event", DateTime.now())
                .setEnd(DateTime.newBuilder(1, 12, 2020, 0,
                        0, ZoneId.systemDefault()).build())
                .setTags(Arrays.asList("test", "Horo"))
                .setRemind(DateTime.newBuilder(1, 12, 2018, 0,
                        0, ZoneId.systemDefault()).build())
                .build());
        events2.add(EventSource.newBuilder("event", DateTime.now())
                .setEnd(DateTime.newBuilder(1, 12, 2020, 0,
                        0, ZoneId.systemDefault()).build())
                .setTags(Arrays.asList("test", "Horo"))
                .setRemind(DateTime.newBuilder(2, 12, 2017, 0,
                        0, ZoneId.systemDefault()).build())
                .build());
        tasks1.add(TaskSource.newBuilder("tasks").build());
        tasks2.add(TaskSource.newBuilder("tasks").build());
        assertNotEquals(new ModelData(events1, tasks1), new ModelData(events2, tasks2));

        // Test if EventSources with different tags result in unequal ModelData objects
        events1.clear();
        events2.clear();
        tasks1.clear();
        tasks2.clear();
        events1.add(EventSource.newBuilder("event", DateTime.now())
                .setEnd(DateTime.newBuilder(1, 12, 2020, 0,
                        0, ZoneId.systemDefault()).build())
                .setTags(Arrays.asList("test", "Duke"))
                .setRemind(DateTime.newBuilder(1, 12, 2018, 0,
                        0, ZoneId.systemDefault()).build())
                .build());
        events2.add(EventSource.newBuilder("event", DateTime.now())
                .setEnd(DateTime.newBuilder(1, 12, 2020, 0,
                        0, ZoneId.systemDefault()).build())
                .setTags(Arrays.asList("Horo", "test"))
                .setRemind(DateTime.newBuilder(1, 12, 2018, 0,
                        0, ZoneId.systemDefault()).build())
                .build());
        tasks1.add(TaskSource.newBuilder("tasks").build());
        tasks2.add(TaskSource.newBuilder("tasks").build());
        assertNotEquals(new ModelData(events1, tasks1), new ModelData(events2, tasks2));
    }

}
