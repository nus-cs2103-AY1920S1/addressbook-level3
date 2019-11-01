package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MARKING_N;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_TIME_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_TIME_2;
import static seedu.address.testutil.TypicalTasks.CS2100;
import static seedu.address.testutil.TypicalTasks.CS2103T;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.TaskBuilder;

public class TaskTest {
    @Test
    public void isSameTask() {
        // same object -> returns true
        assertTrue(CS2103T.isSameTask(CS2103T));

        // null -> returns false
        assertFalse(CS2103T.isSameTask(null));

        // different object -> returns false
        assertFalse(CS2103T.isSameTask(CS2100));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Task CS2103TCopy = new TaskBuilder(CS2103T).build();
        assertTrue(CS2103T.equals(CS2103TCopy));

        // same object -> returns true
        assertTrue(CS2103T.equals(CS2103T));

        // null -> returns false
        assertFalse(CS2103T.equals(null));

        // different type -> returns false
        assertFalse(CS2103T.equals(5));

        // different person -> returns false
        assertFalse(CS2103T.equals(CS2100));

        // different name -> returns false
        Task editedCS2103T = new TaskBuilder(CS2103T).withClassId(VALID_CLASSID_AMY).build();
        assertFalse(CS2103T.equals(editedCS2103T));

        // different marking -> returns false
        editedCS2103T = new TaskBuilder(CS2103T).withMarking(VALID_MARKING_N).build();
        assertFalse(CS2103T.equals(editedCS2103T));

        // different task time -> returns false
        editedCS2103T = new TaskBuilder(CS2103T).withTaskTimes(VALID_TASK_TIME_1, VALID_TASK_TIME_2).build();
        assertFalse(CS2103T.equals(editedCS2103T));
    }
}
