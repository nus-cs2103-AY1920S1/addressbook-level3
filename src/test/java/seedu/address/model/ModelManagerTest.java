package seedu.address.model;

//@@author bruceskellator

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;

class ModelManagerTest {

    @Test
    void setModel_getModel_equal() {
        ModelManager model = new ModelManager();

        List<EventSource> events = List.of(
            EventSource.newBuilder("a", DateTime.now()).build(),
            EventSource.newBuilder("b", DateTime.now()).build(),
            EventSource.newBuilder("c", DateTime.now()).build()
        );
        List<TaskSource> tasks = List.of(
            TaskSource.newBuilder("a").build(),
            TaskSource.newBuilder("b").build(),
            TaskSource.newBuilder("c").build()
        );
        ModelData modelData = new ModelData(events, tasks);

        // Ensure empty
        assertEquals(new ModelData(), model.getModelData());
        assertEquals(List.of(), model.getEvents());
        assertEquals(List.of(), model.getTasks());

        model.setModelData(modelData);
        assertEquals(modelData, model.getModelData());
        assertEquals(events, model.getEvents());
        assertEquals(tasks, model.getTasks());
    }

    @Test
    void setModel_updateModelListeners_success() {
        ModelManager model = new ModelManager();
        ModelDataListenerStub listener1 = new ModelDataListenerStub();
        ModelDataListenerStub listener2 = new ModelDataListenerStub();
        ModelDataListenerStub listener3 = new ModelDataListenerStub();

        model.addModelDataListener(listener1);
        model.addModelDataListener(listener2);
        model.addModelDataListener(listener3);

        DateTime start = DateTime.now();
        ModelData modelData = new ModelData(
            List.of(
                EventSource.newBuilder("a", start).build(),
                EventSource.newBuilder("b", start).build(),
                EventSource.newBuilder("c", start).build()
            ),
            List.of(
                TaskSource.newBuilder("a").build(),
                TaskSource.newBuilder("b").build(),
                TaskSource.newBuilder("c").build()
            )
        );

        model.setModelData(modelData);
        assertEquals(modelData, listener1.getModelData());
        assertEquals(modelData, listener2.getModelData());
        assertEquals(modelData, listener3.getModelData());
    }
}
