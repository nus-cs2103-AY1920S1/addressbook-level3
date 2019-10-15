package seedu.address.model;

import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.HashSet;

import seedu.address.model.task.Task;
import seedu.address.model.member.Member;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyProjectDashboard {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Task> getTaskList();

    ObservableList<Member> getMemberList();

    //Make observable HashMap/ HashSet
    HashMap<Member, HashSet<Task>> getMemberTaskMapping();

    HashMap<Task, HashSet<Member>> getTaskMemberMapping();

}
