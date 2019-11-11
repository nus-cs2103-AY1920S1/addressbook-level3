package seedu.address.ui.stub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.address.logic.UiManager;
import seedu.address.model.CalendarDate;
import seedu.address.model.ModelData;
import seedu.address.model.events.EventDateComparator;
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskDateComparator;
import seedu.address.model.tasks.TaskSource;

//@@author Kyzure
/**
 * Represents a logic class that holds all of the methods that uses logic only that
 * is mainly used for testing.
 */
public class UiManagerStub extends UiManager {

    public void viewDay(CalendarDate calendarDate) {
        return;
    }

    public void viewWeek(CalendarDate calendarDate) {
        return;
    }

    public void viewMonth(CalendarDate calendarDate) {
        return;
    }

    public void viewList() {
        return;
    }

    public void viewLog() {
        return;
    }

    public void viewCalendar(CalendarDate calendarDate) {
        return;
    }

    /**
     * Returns a ModelLists mainly for testing.
     *
     * @param lists The given model lists.
     * @return The sorted model lists.
     */
    public ModelData overrideOnModelListChange(ModelData lists) {
        List<EventSource> sortedDateEventList = sortDateEventList(lists.getEvents());
        List<TaskSource> sortedDateTaskList = sortDateTaskList(lists.getTasks());
        HashMap<EventSource, Integer> eventHash = addEventIndex(lists.getEvents());
        HashMap<TaskSource, Integer> taskHash = addTaskIndex(lists.getTasks());

        return new ModelData(sortedDateEventList, sortedDateTaskList);
    }

    /**
     * Returns a copy of the event list sorted by start date.
     *
     * @param events The given event list.
     * @return A copy of the event list sorted by date.
     */
    private List<EventSource> sortDateEventList(List<EventSource> events) {
        List<EventSource> sortedDateEventList = new ArrayList<>(events);
        sortedDateEventList.sort(new EventDateComparator());
        return sortedDateEventList;
    }

    /**
     * Returns a HashMap based on the indexing of the original event list.
     *
     * @param events The unsorted event list.
     * @return Returns a HashMap based on the indexing of the original event list
     */
    private HashMap<EventSource, Integer> addEventIndex(List<EventSource> events) {
        int backIndex = 0;
        HashMap<EventSource, Integer> eventHash = new HashMap<>();
        for (EventSource event : events) {
            eventHash.put(event, backIndex);
            backIndex++;
        }
        return eventHash;
    }

    /**
     * Returns a copy of the task list sorted by due date.
     *
     * @param tasks The given task list.
     * @return A copy of the task list sorted by due date.
     */
    private List<TaskSource> sortDateTaskList(List<TaskSource> tasks) {
        List<TaskSource> sortedDateTaskList = new ArrayList<>(tasks);
        sortedDateTaskList.sort(new TaskDateComparator());
        return sortedDateTaskList;
    }

    /**
     * Returns a HashMap based on the indexing of the original event list.
     *
     * @param tasks The unsorted task list.
     * @return Returns a HashMap based on the indexing of the original task list
     */
    private HashMap<TaskSource, Integer> addTaskIndex(List<TaskSource> tasks) {
        int backIndex = 0;
        HashMap<TaskSource, Integer> taskHash = new HashMap<>();
        for (TaskSource task : tasks) {
            taskHash.put(task, backIndex);
            backIndex++;
        }
        return taskHash;
    }
}
