package seedu.pluswork.model;

import javafx.collections.ObservableList;
import seedu.pluswork.model.calendar.CalendarWrapper;
import seedu.pluswork.model.calendar.Meeting;
import seedu.pluswork.model.calendar.MeetingQuery;
import seedu.pluswork.model.inventory.Inventory;
import seedu.pluswork.model.mapping.InvMemMapping;
import seedu.pluswork.model.mapping.InvTasMapping;
import seedu.pluswork.model.mapping.Mapping;
import seedu.pluswork.model.mapping.TasMemMapping;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.task.Task;


/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyProjectDashboard {

    /**
     * Returns an unmodifiable view of the task list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<Task> getTaskList();

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Member> getMemberList();

    /**
     * Returns an unmodifiable view of the expenses list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Inventory> getInventoryList();

    ObservableList<InvMemMapping> getInvMemMappingList();

    ObservableList<InvTasMapping> getInvTasMappingList();

    ObservableList<TasMemMapping> getTasMemMappingList();

    ObservableList<Mapping> getMappingList();

    ObservableList<CalendarWrapper> getCalendarList();

    ObservableList<Meeting> getMeetingList();

    MeetingQuery getMeetingQuery();

    ObservableList<Task> getTasksNotStarted();

    ObservableList<Task> getTasksDoing();

    ObservableList<Task> getTasksDone();

}
