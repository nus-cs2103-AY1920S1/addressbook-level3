package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import seedu.address.logic.storage.exceptions.StorageIoException;
import seedu.address.model.ModelLists;
import seedu.address.model.events.EventSource;
import seedu.address.model.events.EventSourceBuilder;
import seedu.address.model.listeners.ModelListListener;
import seedu.address.model.listeners.ModelResetListener;
import seedu.address.model.tasks.TaskSource;
import seedu.address.model.tasks.TaskSourceBuilder;

/**
 * Manages saving and loading (to local storage) of the Model in Horo.
 */
public class StorageManager implements ModelListListener {

    private final ObjectMapper mapper;
    private final List<ModelResetListener> modelResetListeners;

    private Path eventsFile;
    private Path tasksFile;

    public StorageManager() {
        this.mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);
        this.modelResetListeners = new ArrayList<>();
    }

    public void addModelResetListener(ModelResetListener listener) {
        this.modelResetListeners.add(listener);
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

            this.modelResetListeners.forEach(listener -> {
                // Create events and tasks from builders.
                List<EventSource> events = eventBuilders.stream()
                    .map(EventSourceBuilder::build)
                    .collect(Collectors.toList());

                List<TaskSource> tasks = taskBuilders.stream()
                    .map(TaskSourceBuilder::build)
                    .collect(Collectors.toList());

                listener.onModelReset(new ModelLists(events, tasks), this);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onModelListChange(ModelLists lists) {
        try {
            if (this.eventsFile != null) {
                this.mapper.writeValue(this.eventsFile.toFile(), lists.getEvents());
            }

            if (this.tasksFile != null) {
                this.mapper.writeValue(this.tasksFile.toFile(), lists.getTasks());
            }
        } catch (IOException e) {
            throw new StorageIoException();
        }
    }
}
