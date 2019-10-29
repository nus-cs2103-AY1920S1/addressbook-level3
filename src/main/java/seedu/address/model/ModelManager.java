package seedu.address.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.model.events.EventSource;
import seedu.address.model.listeners.EventListListener;
import seedu.address.model.listeners.TaskListListener;
import seedu.address.model.listeners.UndoRedoListener;
import seedu.address.model.tasks.TaskSource;
import seedu.address.model.undo.UndoRedoState;

/**
 * Represents the in-memory model of Horo.
 */
public class ModelManager implements UndoRedoListener {
    private final NotNullList<EventSource> eventList;
    private final List<EventListListener> eventListListeners;
    private final NotNullList<TaskSource> taskList;
    private final List<TaskListListener> taskListListeners;

    /**
     * Creates a ModelManager.
     */
    public ModelManager() {
        super();
        this.eventList = new NotNullList<>();
        this.eventListListeners = new ArrayList<>();
        this.taskList = new NotNullList<>();
        this.taskListListeners = new ArrayList<>();
    }

    public void addEventListListener(EventListListener listener) {
        this.eventListListeners.add(listener);
    }

    public void addTaskListListener(TaskListListener listener) {
        this.taskListListeners.add(listener);
    }

    /* Events */

    /**
     * Adds EventSource(s) to this model's eventList.
     * @param events the EventSource(s) to add
     */
    public void addEvents(EventSource... events) {
        for (EventSource e : events) {
            // Create a deep-copy of each addition.
            this.eventList.add(new EventSource(e));
        }
        notifyEventListListeners();
    }

    public boolean containsEvent(EventSource event) {
        return this.eventList.contains(event);
    }

    /**
     * Removes an EventSource from this model.
     * @param event the EventSource to remove.
     */
    public void removeEvent(EventSource event) {
        this.eventList.remove(event);
        notifyEventListListeners();
    }

    /**
     * Replaces an EventSource in this model with another EventSource.
     * @param event the EventSource to replace
     * @param replacement the replacement
     */
    public void replaceEvent(EventSource event, EventSource replacement) {
        // Create a deep-copy of the replacement.
        this.eventList.replace(event, new EventSource(replacement));
        notifyEventListListeners();
    }

    /**
     * Replaces the entire EventList in this model with a list of events.
     * @param events the events to replace the entire EventList.
     */
    public void setEventList(List<EventSource> events) {
        this.eventList.reset(events);
        notifyEventListListeners();
    }

    /**
     * Returns an unmodifiable, deep copy of this model's EventList.
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
     * @param tasks the TaskSource(s) to add
     */
    public void addTasks(TaskSource... tasks) {
        for (TaskSource t : tasks) {
            // Create a deep-copy of each addition.
            this.taskList.add(new TaskSource(t));
        }
        notifyTaskListListeners();
    }

    public boolean containsTask(TaskSource task) {
        return this.taskList.contains(task);
    }

    /**
     * Removes an TaskSource from this model.
     * @param task the TaskSource to remove.
     */
    public void removeTask(TaskSource task) {
        this.taskList.remove(task);
        notifyTaskListListeners();
    }

    /**
     * Replaces a TaskSource in this model with another TaskSource.
     * @param task the TaskSource to replace
     * @param replacement the replacement
     */
    public void replaceTask(TaskSource task, TaskSource replacement) {
        // Create a deep-copy of the replacement.
        this.taskList.replace(task, new TaskSource(replacement));
        notifyTaskListListeners();
    }

    /**
     * Replaces the entire TaskList in this model with a list of tasks.
     * @param tasks the tasks to replace the entire TaskList.
     */
    public void setTaskList(List<TaskSource> tasks) {
        this.taskList.reset(tasks);
        notifyTaskListListeners();
    }

    /**
     * Returns an unmodifiable, deep copy of this model's TaskList.
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
     * Notify all listeners whenever the EventList is changed.
     */
    private void notifyEventListListeners() {
        this.eventListListeners.forEach(listener ->
                listener.onEventListChange(this.getEventList()));
    }

    /**
     * Notify all listeners whenever the TaskList is changed.
     */
    private void notifyTaskListListeners() {
        this.taskListListeners.forEach(listener ->
                listener.onTaskListChange(this.getTaskList()));
    }

    @Override
    public void onUndoRedo(UndoRedoState state) {
        this.setEventList(state.getEvents());
        this.setTaskList(state.getTasks());
    }

}
