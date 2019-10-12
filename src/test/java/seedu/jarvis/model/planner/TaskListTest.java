package seedu.jarvis.model.planner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

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
    void hasTask() {
        Task t = new Todo("borrow book");
        TaskList testList = new TaskList();
        testList.add(t);
        assertTrue(testList.hasTask(t));
    }
}
