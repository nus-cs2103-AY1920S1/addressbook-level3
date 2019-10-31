package organice.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TaskListTest {
    private TaskList taskList = new TaskList("tasklist");

    private Task one = new Task("one");
    private Task two = new Task("two");
    private Task three = new Task("three");
    private Task four = new Task("four");

    /**
     * Check if TaskList is adding Task correctly
     */
    public void add() {
        taskList.add(one);
        taskList.add(two);
        taskList.add(three);

        assertEquals(one, taskList.get(0));
        assertEquals(two, taskList.get(1));
        assertEquals(three, taskList.get(2));

        taskList.add(four);

        assertEquals(one, taskList.get(0));
        assertEquals(two, taskList.get(1));
        assertEquals(three, taskList.get(2));
        assertEquals(four, taskList.get(3));

        assertTrue(taskList.size() == 4);
    }
    @Test
    void size() {
        taskList.add(one);
        taskList.add(two);
        taskList.add(three);

        assertEquals(taskList.size(), 3);
    }

    @Test
    void defaultList() {

    }

    @Test
    void testToString() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }
}
