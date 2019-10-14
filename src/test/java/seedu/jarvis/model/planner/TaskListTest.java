package seedu.jarvis.model.planner;

import seedu.jarvis.model.planner.tasks.Deadline;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

class TaskListTest {

    @Test
    void getTasks_success() {
        ArrayList<Task> validInput = new ArrayList<>();
        validInput.add(new Todo("borrow book"));
        validInput.add(new Todo("do homework"));
        TaskList testList = new TaskList(validInput);
        for (int i = 0; i < 2; i++) {
            assertEquals(testList.getTasks().get(i), validInput.get(i));
        }
    }

    @Test
    void add() {
        Task t = new Todo("borrow");
        TaskList testList = new TaskList();
        testList.add(t);
        assertNotNull(testList.getTasks());
    }

    @Test
    void hasTask_validInput_true() {
        Task tOne = new Todo("borrow book");
        Task tTwo = new Todo("borrow book");
        TaskList testList = new TaskList();
        testList.add(tOne);
        assertTrue(testList.hasTask(tTwo));
    }

    @Test
    void hasTask_validInput_false() {
        Task todo = new Todo("borrow book");
        Task deadline = new Deadline("borrow book", Calendar.getInstance());
        TaskList testList = new TaskList();
        testList.add(todo);
        assertFalse(testList.hasTask(deadline));
    }
}
