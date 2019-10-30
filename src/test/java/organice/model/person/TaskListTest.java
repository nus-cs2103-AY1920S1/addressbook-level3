package organice.model.person;

import static org.junit.jupiter.api.Assertions.*;
import static organice.testutil.Assert.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import organice.logic.commands.AddCommand;
import organice.logic.commands.AddCommandTest;
import organice.logic.commands.CommandResult;
import organice.testutil.PersonBuilder;

class TaskListTest {
    TaskList taskList = new TaskList("tasklist");
    Task one = new Task("one");
    Task two = new Task("two");
    Task three = new Task("three");
    Task four = new Task("four");

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

        assertTrue(taskList.size()==4);
    }
    @Test
    void size() {
        taskList.add(one);
        taskList.add(two);
        taskList.add(three);

        assertEquals(taskList.size(),3);
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
