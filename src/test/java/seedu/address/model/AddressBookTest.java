package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PUBLICITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENCY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.ORDER_SHIRTS;
import static seedu.address.testutil.TypicalTasks.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.testutil.TaskBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getTaskList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsDuplicateTaskException() {
        // Two tasks with the same identity fields
        Task editedShirtOrderTask = new TaskBuilder(ORDER_SHIRTS).withTags(VALID_TAG_PUBLICITY)
                .build();
        List<Task> newTasks = Arrays.asList(ORDER_SHIRTS, editedShirtOrderTask);
        AddressBookStub newData = new AddressBookStub(newTasks);

        assertThrows(DuplicateTaskException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasTask_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasTask(null));
    }

    @Test
    public void hasTask_taskNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasTask(ORDER_SHIRTS));
    }

    @Test
    public void hasTask_taskInAddressBook_returnsTrue() {
        addressBook.addTask(ORDER_SHIRTS);
        assertTrue(addressBook.hasTask(ORDER_SHIRTS));
    }

    @Test
    public void hasTask_taskWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addTask(ORDER_SHIRTS);
        Task editedShirtOrderTask = new TaskBuilder(ORDER_SHIRTS).withTags(VALID_TAG_URGENCY)
                .build();
        assertTrue(addressBook.hasTask(editedShirtOrderTask));
    }

    @Test
    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getTaskList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose tasks list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        AddressBookStub(Collection<Task> tasks) {
            this.tasks.setAll(tasks);
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }
    }

}
