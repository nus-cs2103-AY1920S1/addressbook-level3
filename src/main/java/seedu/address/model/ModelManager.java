package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.events.EventSource;
import seedu.address.model.listeners.ModelDataListener;
import seedu.address.model.tasks.TaskSource;

//@@author bruceskellator

/**
 * Represents a class that manages the {@link ModelData} of Horo.
 * Horo's Storage, Ui and UndoRedoManager components implement the ModelDataListener interface
 * which listens for any changes to this ModelData so that they can be updated accordingly.
 */
public class ModelManager {

    private ModelData model;

    private final List<ModelDataListener> modelDataListeners;

    /**
     * Creates a ModelManager.
     */
    public ModelManager() {
        super();
        this.model = new ModelData();

        this.modelDataListeners = new ArrayList<>();
    }

    /**
     * Adds ModelDataListeners.
     */
    public void addModelDataListener(ModelDataListener listener) {
        this.modelDataListeners.add(listener);
    }

    /**
     * Replaces the current ModelData with a deep copy of a ModelData provided.
     * @param modelData a ModelData to replace this Model
     */
    public void setModelData(ModelData modelData) {
        // Deep copy and reassign model.
        this.model = new ModelData(modelData);

        // Notify all listeners whenever either the EventList or TaskList is changed.
        this.modelDataListeners
            .forEach(listener -> listener.onModelDataChange(this.getModelData()));
    }

    /**
     * Returns an immutable, deep copy of Horo's events and tasks.
     *
     * @return a ModelLists
     */
    public ModelData getModelData() {
        return new ModelData(this.model);
    }

    /**
     * Returns an immutable, deep copy of this Horo's events.
     *
     * @return a copy of the Horo's events
     */
    public List<EventSource> getEvents() {
        return this.model.getEvents();
    }

    /* Tasks */

    /**
     * Returns an immutable, deep copy of Horo's tasks.
     *
     * @return a copy of Horo's tasks
     */
    public List<TaskSource> getTasks() {
        return this.model.getTasks();
    }

    /**
     * Returns the original ModelData. This method is used for testing UndoRedoManager.
     *
     * @return the original ModelData
     */
    public ModelData getModel() {
        return model;
    }

}
