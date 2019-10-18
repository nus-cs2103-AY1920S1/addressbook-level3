package seedu.jarvis.model.planner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;

class PlannerTest {

    @Test
    void getTasks() {
        Planner planner = new Planner();
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("borrow book"));
        tasks.add(new Todo("read book"));
        TaskList expected = new TaskList(tasks);

        planner.addTask(new Todo("borrow book"));
        planner.addTask(new Todo("read book"));

        assertTrue(expected.equals(planner.getTasks()));

    }

    @Test
    void addTask() {
        Planner planner = new Planner();
        planner.addTask(new Todo("read book"));
        assertNotNull(planner.getTasks().getTasks());
    }

    @Test
    void hasTask_true() {
        Planner planner = new Planner();
        planner.addTask(new Todo("borrow book"));
        assertTrue(planner.hasTask(new Todo("borrow book")));
    }

    @Test
    void hasTask_false() {
        Planner planner = new Planner();
        planner.addTask(new Todo("borrow book"));
        assertFalse(planner.hasTask(new Todo("borrow")));
    }

    @Test
    void equals_true() {
        Planner pOne = new Planner();
        pOne.addTask(new Todo("borrow book"));

        Planner pTwo = new Planner();
        pTwo.addTask(new Todo("borrow book"));

        assertTrue(pOne.equals(pTwo));
    }

    @Test
    void equals_false() {
        Planner pOne = new Planner();
        pOne.addTask(new Todo("borrow book"));

        Planner pTwo = new Planner();

        assertFalse(pOne.equals(pTwo));
    }

    @Test
    void getTask() throws ParseException {
        Task expected = new Todo("borrow book");

        Planner testPlanner = new Planner();
        testPlanner.addTask(new Todo("read book"));
        testPlanner.addTask(new Todo("borrow book"));

        assertTrue(expected.isEqual(testPlanner.getTask(ParserUtil.parseIndex("2"))));
    }

    @Test
    void deleteTask() throws ParseException {
        Planner testPlanner = new Planner();
        testPlanner.addTask(new Todo("borrow"));
        testPlanner.addTask(new Todo("read"));
        testPlanner.addTask(new Todo("study"));

        testPlanner.deleteTask(ParserUtil.parseIndex("2"));

        assertEquals(2, testPlanner.size());
    }

    @Test
    void size() {
        Planner testPlanner = new Planner();
        testPlanner.addTask(new Todo("borrow"));
        testPlanner.addTask(new Todo("read"));
        testPlanner.addTask(new Todo("study"));

        assertEquals(3, testPlanner.size());
    }
}
