package seedu.jarvis.model.planner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.planner.enums.Status;
import seedu.jarvis.model.planner.predicates.TaskDesContainsKeywordsPredicate;
import seedu.jarvis.model.planner.tasks.Deadline;
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

        assertTrue(expected.equals(planner.getTaskList()));

    }

    @Test
    void addTask_task() {
        Planner planner = new Planner();
        planner.addTask(new Todo("read book"));
        assertNotNull(planner.getTaskList().getTasks());
    }

    @Test
    void addTask_taskAndIndex() {
        Planner planner = new Planner();
        planner.addTask(0, new Todo("read book"));
        assertNotNull(planner.getTaskList().getTasks());
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

        assertTrue(expected.equals(testPlanner.getTask(ParserUtil.parseIndex("2"))));
    }

    @Test
    void deleteTask_index() throws ParseException {
        Planner testPlanner = new Planner();
        testPlanner.addTask(new Todo("borrow"));
        testPlanner.addTask(new Todo("read"));
        testPlanner.addTask(new Todo("study"));

        testPlanner.deleteTask(ParserUtil.parseIndex("2"));

        assertEquals(2, testPlanner.size());
    }

    @Test
    void deleteTask_task() {
        Planner testPlanner = new Planner();
        Task toDelete = new Todo("read");
        testPlanner.addTask(new Todo("borrow"));
        testPlanner.addTask(toDelete);
        testPlanner.addTask(new Todo("study"));

        testPlanner.deleteTask(toDelete);

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

    @Test
    void getFilteredTaskList() {
        Planner planner = new Planner();
        planner.addTask(new Todo("borrow book"));
        planner.addTask(new Todo("read book"));

        TaskDesContainsKeywordsPredicate predicate = new TaskDesContainsKeywordsPredicate(
                                                            Arrays.asList("borrow"));

        planner.updateFilteredTaskList(predicate);

        assertEquals(1, planner.getFilteredTaskList().size());
    }

    @Test
    void markTaskAsDone() throws ParseException {
        Planner planner = new Planner();
        planner.addTask(new Todo("borrow book"));
        planner.addTask(new Todo("read book"));

        planner.markTaskAsDone(ParserUtil.parseIndex("1"));
        Task check = planner.getTask(ParserUtil.parseIndex("1"));

        assertEquals("borrow book", check.getTaskDes());
        assertEquals(Status.DONE, check.getStatus());
    }

    @Test
    void getTasksToday() {
        Planner planner = new Planner();
        planner.addTask(new Deadline("d", LocalDate.now()));
        planner.addTask(new Deadline("dd", LocalDate.parse("10/10/2010", Task.getDateFormat())));

        planner.updateSchedule();

        assertEquals(1, planner.getTasksToday().size());
    }

    @Test
    void getTasksThisWeek() {
        Planner planner = new Planner();
        LocalDate date = LocalDate.now();
        planner.addTask(new Deadline("d", date));
        planner.addTask(new Deadline("dd", LocalDate.parse("10/10/2010", Task.getDateFormat())));
        planner.addTask(new Deadline("ddd", date.plusDays(6)));

        planner.updateSchedule();

        assertEquals(2, planner.getTasksThisWeek().size());
    }
}
