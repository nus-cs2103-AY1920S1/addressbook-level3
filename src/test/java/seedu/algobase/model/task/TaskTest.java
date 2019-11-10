package seedu.algobase.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.algobase.testutil.TypicalTasks.FACTORIAL_TASK;
import static seedu.algobase.testutil.TypicalTasks.TWO_SUM_TASK;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.algobase.testutil.TaskBuilder;

public class TaskTest {

    @Test
    public void equals() {
        // same values -> returns true
        Task twoSumCopy = new TaskBuilder(TWO_SUM_TASK).build();
        assertEquals(TWO_SUM_TASK, twoSumCopy);

        // same object -> returns true
        assertEquals(TWO_SUM_TASK, TWO_SUM_TASK);

        // null -> returns false
        assertNotEquals(null, TWO_SUM_TASK);

        // different type -> returns false
        assertNotEquals(10086, TWO_SUM_TASK);

        // different tasks -> returns false
        assertNotEquals(TWO_SUM_TASK, FACTORIAL_TASK);

        // different due date -> returns true
        Task editedTwoSumTask = new TaskBuilder(TWO_SUM_TASK).withDueDate(LocalDate.now()).build();
        assertEquals(TWO_SUM_TASK, editedTwoSumTask);

        // different isSolved -> returns true
        editedTwoSumTask = new TaskBuilder(TWO_SUM_TASK).withIsDone(true).build();
        assertEquals(TWO_SUM_TASK, editedTwoSumTask);
    }

}
