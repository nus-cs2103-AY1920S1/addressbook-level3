package seedu.address.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.events.EventSource;
import seedu.address.model.listeners.EventListListener;
import seedu.address.model.listeners.ModelListListener;
import seedu.address.model.listeners.ModelResetListener;
import seedu.address.model.listeners.TaskListListener;
import seedu.address.model.tasks.TaskSource;

/**
 * Represents the in-memory model of Horo.
 */
public class ModelManager implements ModelResetListener {
    private final NotNullList<EventSource> eventList;
    private final NotNullList<TaskSource> taskList;

    private final List<EventListListener> eventListListeners;
    private final List<TaskListListener> taskListListeners;
    private final List<ModelListListener> modelListListeners;

    /**
     * Creates a ModelManager.
     */
    public ModelManager() {
        super();
        this.eventList = new NotNullList<>();
        this.taskList = new NotNullList<>();

        this.eventListListeners = new ArrayList<>();
        this.taskListListeners = new ArrayList<>();
        this.modelListListeners = new ArrayList<>();
    }

    public void addEventListListener(EventListListener listener) {
        this.eventListListeners.add(listener);
    }

    public void addTaskListListener(TaskListListener listener) {
        this.taskListListeners.add(listener);
    }

    public void addModelListListener(ModelListListener listener) {
        this.modelListListeners.add(listener);
    }

    /* Events */

    /**
     * Adds EventSource(s) to this model's eventList.
     *
     * @param events the EventSource(s) to add
     */
    public void addEvents(EventSource... events) {
        for (EventSource e : events) {
            // Create a deep-copy of each addition.
            this.eventList.add(new EventSource(e));
        }
        notifyEventListListeners();
        notifyModelListListeners();
    }

    public boolean containsEvent(EventSource event) {
        return this.eventList.contains(event);
    }

    /**
     * Removes an EventSource from this model.
     *
     * @param event the EventSource to remove.
     */
    public void removeEvent(EventSource event) {
        this.eventList.remove(event);
        notifyEventListListeners();
        notifyModelListListeners();
    }

    /**
     * Replaces an EventSource in this model with another EventSource.
     *
     * @param event       the EventSource to replace
     * @param replacement the replacement
     */
    public void replaceEvent(EventSource event, EventSource replacement) {
        // Create a deep-copy of the replacement.
        this.eventList.replace(event, new EventSource(replacement));
        notifyEventListListeners();
        notifyModelListListeners();
    }

    /**
     * Returns an unmodifiable, deep copy of this model's EventList.
     *
     * @return a copy of the EventList
     */
    public List<EventSource> getEventList() {
        List<EventSource> result = new ArrayList<>();
        for (EventSource event : this.eventList) {
            // Create a deep-copy of each EventSource.
            result.add(new EventSource(event));
        }
        // Return an unmodifiable list.
        return Collections.unmodifiableList(result);
    }

    /* Tasks */

    /**
     * Adds TaskSource(s) to this model's taskList.
     *
     * @param tasks the TaskSource(s) to add
     */
    public void addTasks(TaskSource... tasks) {
        for (TaskSource t : tasks) {
            // Create a deep-copy of each addition.
            this.taskList.add(new TaskSource(t));
        }
        notifyTaskListListeners();
        notifyModelListListeners();
    }

    public boolean containsTask(TaskSource task) {
        return this.taskList.contains(task);
    }

    /**
     * Removes an TaskSource from this model.
     *
     * @param task the TaskSource to remove.
     */
    public void removeTask(TaskSource task) {
        this.taskList.remove(task);
        notifyTaskListListeners();
        notifyModelListListeners();
    }

    /**
     * Replaces a TaskSource in this model with another TaskSource.
     *
     * @param task        the TaskSource to replace
     * @param replacement the replacement
     */
    public void replaceTask(TaskSource task, TaskSource replacement) {
        // Create a deep-copy of the replacement.
        this.taskList.replace(task, new TaskSource(replacement));
        notifyTaskListListeners();
        notifyModelListListeners();
    }

    /**
     * Returns an unmodifiable, deep copy of this model's TaskList.
     *
     * @return a copy of the TaskList
     */
    public List<TaskSource> getTaskList() {
        List<TaskSource> result = new ArrayList<>();
        for (TaskSource task : this.taskList) {
            // Create a deep-copy of each TaskSource.
            result.add(new TaskSource(task));
        }
        // Return an unmodifiable list.
        return Collections.unmodifiableList(result);
    }


    /**
     * Returns an unmodifiable, deep copy of this model's EventList and TaskList.
     *
     * @return a ModelLists
     */
    public ModelLists getModelList() {
        return new ModelLists(this.getEventList(), this.getTaskList());
    }

    /**
     * Notify all listeners whenever the EventList is changed.
     */
    private void notifyEventListListeners(Predicate<EventListListener> predicate) {
        this.eventListListeners.stream()
            .filter(predicate)
            .forEach(listener -> listener.onEventListChange(this.getEventList()));
    }

    private void notifyEventListListeners() {
        notifyEventListListeners(listener -> true);
    }

    /**
     * Notify all listeners whenever the TaskList is changed.
     */
    private void notifyTaskListListeners(Predicate<TaskListListener> predicate) {
        this.taskListListeners.stream()
            .filter(predicate)
            .forEach(listener -> listener.onTaskListChange(this.getTaskList()));
    }

    private void notifyTaskListListeners() {
        notifyTaskListListeners(listener -> true);
    }

    /**
     * Notify all listeners whenever either the EventList or TaskList is changed.
     */
    private void notifyModelListListeners(Predicate<ModelListListener> predicate) {
        this.modelListListeners.stream()
            .filter(predicate)
            .forEach(listener -> listener.onModelListChange(this.getModelList()));
    }

    private void notifyModelListListeners() {
        notifyModelListListeners(listener -> true);
    }

    @Override
    public void onModelReset(ModelLists state, Object caller) {
        this.eventList.reset(state.getEvents());
        this.taskList.reset(state.getTasks());

        /*
        Reset the Models and notify all ModelListeners, except the caller.
        Example: UndoRedoManager resets the model, so no need to notify UndoRedoManager
        that the model changed.
         */
        notifyEventListListeners(listener -> listener != caller);
        notifyTaskListListeners(listener -> listener != caller);
        notifyModelListListeners(listener -> listener != caller);
    }

}
