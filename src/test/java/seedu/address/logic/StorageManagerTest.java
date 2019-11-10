package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneOffset;
import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import seedu.address.model.DateTime;
import seedu.address.model.ModelData;
import seedu.address.model.ModelManager;
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;

class StorageManagerTest {

    private static final Path TEST_DATA_PATH = Paths.get("data/test");
    private static final Path EVENTS_PATH = TEST_DATA_PATH.resolve("events.json");
    private static final Path TASKS_PATH = TEST_DATA_PATH.resolve("tasks.json");

    private static final Path CORRECT_EVENTS_PATH = Paths.get("src/test/data", "events.json");
    private static final Path CORRECT_TASKS_PATH = Paths.get("src/test/data", "tasks.json");

    private static final ModelData CORRECT_MODEL_DATA;

    static {
        DateTime start = DateTime.newBuilder(11, 11, 1111, 11, 11, ZoneOffset.UTC).build();
        CORRECT_MODEL_DATA = new ModelData(
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
    }

    @BeforeEach
    void beforeEach() throws IOException {
        Files.deleteIfExists(EVENTS_PATH);
        Files.deleteIfExists(TASKS_PATH);
        Files.deleteIfExists(TEST_DATA_PATH);
    }

    @AfterAll
    static void afterAll() throws IOException {
        Files.deleteIfExists(EVENTS_PATH);
        Files.deleteIfExists(TASKS_PATH);
        Files.deleteIfExists(TEST_DATA_PATH);
    }

    @Test
    void load_noPaths_success() {
        ModelManager model = new ModelManager();
        StorageManager manager = new StorageManager(model);
        manager.load();

        // Loaded nothing.
        assertEquals(new ModelData(), model.getModelData());
    }

    @Test
    void load_notExists_success() {
        ModelManager model = new ModelManager();
        StorageManager manager = new StorageManager(model);

        manager.setEventsFile(EVENTS_PATH);
        manager.setTasksFile(TASKS_PATH);
        manager.load();

        // Loaded nothing.
        assertEquals(new ModelData(), model.getModelData());
    }

    @Test
    void save_notExists_success() throws IOException, JSONException {
        ModelManager model = new ModelManager();
        StorageManager manager = new StorageManager(model);

        model.addModelDataListener(manager);
        manager.setEventsFile(EVENTS_PATH);
        manager.setTasksFile(TASKS_PATH);

        model.setModelData(CORRECT_MODEL_DATA);

        assertTrue(Files.exists(EVENTS_PATH));
        assertTrue(Files.exists(TASKS_PATH));

        // Check that json data is the same.
        String events1 = Files.readString(CORRECT_EVENTS_PATH);
        String events2 = Files.readString(EVENTS_PATH);
        JSONAssert.assertEquals(events1, events2, false);

        String tasks1 = Files.readString(CORRECT_TASKS_PATH);
        String tasks2 = Files.readString(TASKS_PATH);
        JSONAssert.assertEquals(tasks1, tasks2, false);
    }

    @Test
    void load_exists_success() {
        ModelManager model = new ModelManager();
        StorageManager manager = new StorageManager(model);

        model.addModelDataListener(manager);
        manager.setEventsFile(CORRECT_EVENTS_PATH);
        manager.setTasksFile(CORRECT_TASKS_PATH);
        manager.load();

        assertEquals(CORRECT_MODEL_DATA, model.getModelData());
    }
}
