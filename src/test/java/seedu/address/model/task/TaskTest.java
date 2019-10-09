package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PUBLICITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_FINANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_PUBLICITY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.ORDER_SHIRTS;
import static seedu.address.testutil.TypicalTasks.BUILD_WEBSITE;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TaskBuilder;

public class TaskTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Task task = new TaskBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> task.getTags().remove(0));
    }

    @Test
    public void isSameTask() {
        // same object -> returns true
        assertTrue(ORDER_SHIRTS.isSameTask(ORDER_SHIRTS));

        // null -> returns false
        assertFalse(ORDER_SHIRTS.isSameTask(null));

        Task editedShirtOrderTask = new TaskBuilder(ORDER_SHIRTS).build();

        // different name -> returns false
        editedShirtOrderTask = new TaskBuilder(ORDER_SHIRTS)
                .withName(VALID_TASK_NAME_FINANCE).build();
        assertFalse(ORDER_SHIRTS.isSameTask(editedShirtOrderTask));

        // same name, different attributes -> returns true
        editedShirtOrderTask = new TaskBuilder(ORDER_SHIRTS)
                .withStatus(TaskStatus.DOING)
                .build();
        assertTrue(ORDER_SHIRTS.isSameTask(editedShirtOrderTask));

        // same name, different attributes -> returns true
        editedShirtOrderTask = new TaskBuilder(ORDER_SHIRTS)
                .withTags(VALID_TAG_PUBLICITY)
                .build();
        assertTrue(ORDER_SHIRTS.isSameTask(editedShirtOrderTask));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Task orderShirtsCopy = new TaskBuilder(ORDER_SHIRTS).build();
        assertTrue(ORDER_SHIRTS.equals(orderShirtsCopy));

        // same object -> returns true
        assertTrue(ORDER_SHIRTS.equals(ORDER_SHIRTS));

        // null -> returns false
        assertFalse(ORDER_SHIRTS.equals(null));

        // different type -> returns false
        assertFalse(ORDER_SHIRTS.equals(5));

        // different task -> returns false
        assertFalse(ORDER_SHIRTS.equals(BUILD_WEBSITE));

        // different name -> returns false
        Task editedShirtOrderTask = new TaskBuilder(ORDER_SHIRTS).withName(VALID_TASK_NAME_PUBLICITY).build();
        assertFalse(ORDER_SHIRTS.equals(editedShirtOrderTask));

        // different tags -> returns false
        editedShirtOrderTask = new TaskBuilder(ORDER_SHIRTS).withTags(VALID_TAG_PUBLICITY).build();
        assertFalse(ORDER_SHIRTS.equals(editedShirtOrderTask));

        // different task status return false
        editedShirtOrderTask = new TaskBuilder(ORDER_SHIRTS).withStatus(TaskStatus.DOING).build();
        assertFalse(ORDER_SHIRTS.equals(editedShirtOrderTask));
    }
}
