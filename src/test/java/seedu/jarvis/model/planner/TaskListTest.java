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
    void add_task() {
        Task t = new Todo("borrow");
        TaskList testList = new TaskList();
        testList.add(t);
        assertNotNull(testList.getTasks());
    }

    @Test
    void add_taskAndIndex() throws ParseException {
        Task t = new Todo("borrow");
        Task t2 = new Todo("help");
        TaskList testList = new TaskList();
        testList.add(t);
        testList.add(t2);

        Task toAdd = new Todo("middle");
        testList.add(1, toAdd);

        assertEquals(testList.getTask(ParserUtil.parseIndex("2")),
                    toAdd);
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
        LocalDate due = LocalDate.parse("10/10/2019", Task.getDateFormat());
        Task deadline = new Deadline("borrow book", due);
        TaskList testList = new TaskList();
        testList.add(todo);
        assertFalse(testList.hasTask(deadline));
    }

    @Test
    void equals_true() {
        ArrayList<Task> tasksOne = new ArrayList<>();
        tasksOne.add(new Todo("borrow book"));
        tasksOne.add(new Deadline("hello", LocalDate.parse("10/10/2019", Task.getDateFormat())));
        tasksOne.add(new Todo("help"));

        ArrayList<Task> tasksTwo = new ArrayList<>();
        tasksTwo.add(new Todo("borrow book"));
        tasksTwo.add(new Deadline("hello", LocalDate.parse("10/10/2019", Task.getDateFormat())));
        tasksTwo.add(new Todo("help"));

        TaskList one = new TaskList(tasksOne);
        TaskList two = new TaskList(tasksTwo);

        assertEquals(one, two);

    }

    @Test
    void equals_false() {
        ArrayList<Task> tasksOne = new ArrayList<>();
        tasksOne.add(new Todo("borrow book"));
        tasksOne.add(new Deadline("hello", LocalDate.parse("10/10/2019", Task.getDateFormat())));
        tasksOne.add(new Todo("help"));

        ArrayList<Task> tasksTwo = new ArrayList<>();
        tasksTwo.add(new Todo("borrow book"));
        tasksTwo.add(new Deadline("hello", LocalDate.parse("10/10/2019", Task.getDateFormat())));
        tasksTwo.add(new Todo("help me"));

        TaskList one = new TaskList(tasksOne);
        TaskList two = new TaskList(tasksTwo);

        assertFalse(one.equals(two));

    }

    @Test
    void getTask() throws ParseException {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("borrow book"));
        tasks.add(new Deadline("hello", LocalDate.parse("10/10/2019", Task.getDateFormat())));
        tasks.add(new Todo("help"));
        TaskList taskTest = new TaskList(tasks);

        Task expected = new Deadline("hello", LocalDate.parse("10/10/2019", Task.getDateFormat()));

        assertTrue(expected.equals(taskTest.getTask(ParserUtil.parseIndex("2"))));
    }

    @Test
    void size() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("borrow book"));
        tasks.add(new Deadline("hello", LocalDate.parse("10/10/2019", Task.getDateFormat())));
        tasks.add(new Todo("help"));
        TaskList taskTest = new TaskList(tasks);

        assertEquals(3, taskTest.size());
    }

    @Test
    void deleteTask_index() throws ParseException {
        ArrayList<Task> tasks = new ArrayList<>();

        tasks.add(new Todo("borrow book"));
        tasks.add(new Deadline("hello", LocalDate.parse("10/10/2019", Task.getDateFormat())));
        tasks.add(new Todo("help"));

        TaskList taskTest = new TaskList(tasks);

        taskTest.deleteTask(ParserUtil.parseIndex("2"));

        assertEquals(2, taskTest.size());
        assertTrue(new Todo("help").equals(taskTest.getTask(ParserUtil.parseIndex("2"))));
    }

    @Test
    void deleteTask_task() throws ParseException {
        ArrayList<Task> tasks = new ArrayList<>();
        Task toDelete = new Todo("borrow book");

        tasks.add(toDelete);
        tasks.add(new Deadline("hello", LocalDate.parse("10/10/2019", Task.getDateFormat())));
        tasks.add(new Todo("help"));

        TaskList taskTest = new TaskList(tasks);

        taskTest.deleteTask(toDelete);

        assertEquals(2, taskTest.size());
        assertTrue(new Todo("help").equals(taskTest.getTask(ParserUtil.parseIndex("2"))));
    }

    @Test
    void find() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("borrow book"));
        tasks.add(new Todo("read book"));
        TaskList tasklist = new TaskList(tasks);

        ArrayList<Task> filteredTasks = new ArrayList<>();
        filteredTasks.add(new Todo("borrow book"));
        TaskList filtered = new TaskList(filteredTasks);

        TaskList actual = tasklist.find(new TaskDesContainsKeywordsPredicate(Arrays.asList("borrow")));

        assertEquals(filtered, actual);
    }

    @Test
    void markTaskAsDone() throws ParseException {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("borrow book"));
        tasks.add(new Todo("read book"));
        TaskList tasklist = new TaskList(tasks);

        tasklist.markTaskAsDone(ParserUtil.parseIndex("1"));
        Task check = tasklist.getTask(ParserUtil.parseIndex("1"));

        assertEquals("borrow book", check.getTaskDes());
        assertEquals(Status.DONE, check.getStatus());
    }
}
