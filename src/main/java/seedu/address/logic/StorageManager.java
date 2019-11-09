package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import seedu.address.logic.storage.exceptions.StorageIoException;
import seedu.address.model.ModelData;
import seedu.address.model.ModelManager;
import seedu.address.model.events.EventSource;
import seedu.address.model.events.EventSourceBuilder;
import seedu.address.model.listeners.ModelDataListener;
import seedu.address.model.tasks.TaskSource;
import seedu.address.model.tasks.TaskSourceBuilder;

/**
 * Manages saving and loading (to local storage) of the Model in Horo.
 */
public class StorageManager implements ModelDataListener {

    private final ObjectMapper mapper;
    private final ModelManager model;

    private Path eventsFile;
    private Path tasksFile;

    private boolean start;

    public StorageManager(ModelManager model) {
        this.mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);
        this.model = model;
        this.start = true;
    }

    public void setEventsFile(Path eventsFile) {
        this.eventsFile = eventsFile;
    }

    public void setTasksFile(Path tasksFile) {
        this.tasksFile = tasksFile;
    }

    /**
     * Load Model from local storage.
     */
    public void load() {
        try {
            List<EventSourceBuilder> eventBuilders;
            if (this.eventsFile == null || Files.notExists(this.eventsFile)) {
                eventBuilders = List.of();
            } else {
                eventBuilders = this.mapper.readValue(this.eventsFile.toFile(),
                    new TypeReference<List<EventSourceBuilder>>() {});
            }

            List<TaskSourceBuilder> taskBuilders;
            if (this.tasksFile == null || Files.notExists(this.tasksFile)) {
                taskBuilders = List.of();
            } else {
                taskBuilders = this.mapper.readValue(this.tasksFile.toFile(),
                    new TypeReference<List<TaskSourceBuilder>>() {});
            }

            // Create events and tasks from builders.
            List<EventSource> events = eventBuilders.stream()
                .map(EventSourceBuilder::build)
                .collect(Collectors.toList());

            List<TaskSource> tasks = taskBuilders.stream()
                .map(TaskSourceBuilder::build)
                .collect(Collectors.toList());

            if (this.start) {
                // Ignore changes to model made by itself.
                this.start = false;
                this.model.setModelData(new ModelData(events, tasks));
                this.start = true;
            }
        } catch (IOException e) {
            throw new StorageIoException();
        }
    }

    @Override
    public void onModelDataChange(ModelData modelData) {
        if (!start) {
            return;
        }

        try {
            Files.createDirectories(this.eventsFile.getParent());
            Files.createDirectories(this.tasksFile.getParent());

            if (this.eventsFile != null) {
                this.mapper.writeValue(this.eventsFile.toFile(), modelData.getEvents());
            }

            if (this.tasksFile != null) {
                this.mapper.writeValue(this.tasksFile.toFile(), modelData.getTasks());
            }
        } catch (IOException e) {
            throw new StorageIoException();
        }
    }
}
