package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;

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
        events1.clear();
        events2.clear();
        tasks1.clear();
        tasks2.clear();
        DateTime end = DateTime.newBuilder(1, 11, 2020, 0,
                0, ZoneId.systemDefault()).build();
        events1.add(EventSource.newBuilder("event", start)
                .setEnd(end)
                .setRemind(end)
                .setTags(new HashSet<>(Arrays.asList("test", "Horo")))
                .build());
        events2.add(EventSource.newBuilder("event", start)
                .setEnd(end)
                .setRemind(end)
                .setTags(new HashSet<>(Arrays.asList("test", "Horo")))
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
        events1.add(EventSource.newBuilder("event", start).build());
        events2.add(EventSource.newBuilder("Event", start).build());
        tasks1.add(TaskSource.newBuilder("tasks").build());
        tasks2.add(TaskSource.newBuilder("tasks").build());
        assertNotEquals(new ModelData(events1, tasks1), new ModelData(events2, tasks2));

        events1.clear();
        events2.clear();
        tasks1.clear();
        tasks2.clear();
        events1.add(EventSource.newBuilder("event", start).build());
        events2.add(EventSource.newBuilder("event", start).build());
        tasks1.add(TaskSource.newBuilder("Tasks").build());
        tasks2.add(TaskSource.newBuilder("tasks").build());
        assertNotEquals(new ModelData(events1, tasks1), new ModelData(events2, tasks2));

        events1.clear();
        events2.clear();
        tasks1.clear();
        tasks2.clear();
        events1.add(EventSource.newBuilder("event", DateTime.now()).build());
        events2.add(EventSource.newBuilder("event", DateTime.now()).build());
        tasks1.add(TaskSource.newBuilder("tasks").build());
        tasks2.add(TaskSource.newBuilder("tasks").build());
        assertNotEquals(new ModelData(events1, tasks1), new ModelData(events2, tasks2));
    }

}
