package seedu.address.model;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.member.Member;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.task.Task;

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

}
