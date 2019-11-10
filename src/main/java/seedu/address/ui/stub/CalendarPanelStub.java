package seedu.address.ui.stub;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.panel.calendar.CalendarPanel;

//@@author Kyzure
/**
 * Represents a stub for CalendarPanel that is mainly used for testing purpose only.
 *
 * @see CalendarPanel
 */
class CalendarPanelStub {

    /**
     * Returns a combined copy of list for event list and task list into an Object list to be used.
     * This is a version modified mainly for testing. The logic behind it is the exact same.
     *
     * @param events Represents the event list.
     * @param tasks Represents the task list.
     * @return A combined copy of list for event list and task list into an Object list to be used.
     */
    public List<Object> combineList(List<EventSource> events, List<TaskSource> tasks) {
        List<Object> eventTaskList = new ArrayList<>();
        Queue<EventSource> tempEvents = new LinkedList<>();
        tempEvents.addAll(events);
        Queue<TaskSource> tempTasks = new LinkedList<>();
        tempTasks.addAll(tasks);

        // Events and Tasks are already sorted, so we need to zip them.
        while (!tempEvents.isEmpty() || !tempTasks.isEmpty()) {
            if (tempEvents.isEmpty()) {
                eventTaskList.addAll(tempTasks);
                break;
            }

            if (tempTasks.isEmpty()) {
                eventTaskList.addAll(tempEvents);
                break;
            }

            EventSource event = tempEvents.peek();
            TaskSource task = tempTasks.peek();
            if (task.isDone() || task.getDueDate() == null) {
                tempTasks.poll();
                continue;
            }

            int dateCompare = event.getStartDateTime().compareTo(task.getDueDate());
            if (dateCompare == 0) {
                if (event.getDescription().compareTo(task.getDescription()) <= 0) {
                    eventTaskList.add(tempEvents.poll());
                } else {
                    eventTaskList.add(tempTasks.poll());
                }
            } else if (dateCompare < 0) {
                eventTaskList.add(tempEvents.poll());
            } else {
                eventTaskList.add(tempTasks.poll());
            }
        }

        return eventTaskList;
    }
}
