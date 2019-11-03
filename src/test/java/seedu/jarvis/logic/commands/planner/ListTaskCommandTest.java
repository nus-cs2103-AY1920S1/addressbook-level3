package seedu.jarvis.logic.commands.planner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.planner.TaskDesContainsKeywordsPredicate;
import seedu.jarvis.model.planner.tasks.Todo;
import seedu.jarvis.model.userprefs.UserPrefs;


class ListTaskCommandTest {

    @Test
    void getCommandWord() {
        String expected = "list-tasks";
        ListTaskCommand command = new ListTaskCommand();

        assertEquals(expected, command.getCommandWord());
    }

    @Test
    void hasInverseExecution() {
        ListTaskCommand command = new ListTaskCommand();
        assertFalse(command.hasInverseExecution());
    }

    @Test
    void execute_listIsNotFiltered_showsSameList() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        ListTaskCommand command = new ListTaskCommand();

        assertCommandSuccess(command, model,
                ListTaskCommand.MESSAGE_SUCCESS, expectedModel);

    }

    @Test
    void execute_listIsFiltered_showsEverything() {
        Model model = new ModelManager();
        model.addTask(new Todo("borrow book"));
        model.addTask(new Todo("b"));
        model.addTask(new Todo("book"));

        Model expected = new ModelManager(model.getCcaTracker(), model.getHistoryManager(),
                model.getFinanceTracker(), new UserPrefs(), model.getPlanner(), model.getCoursePlanner());

        model.updateFilteredTaskList(new TaskDesContainsKeywordsPredicate(Arrays.asList("borrow")));


        ListTaskCommand command = new ListTaskCommand();

        assertCommandSuccess(command, model, ListTaskCommand.MESSAGE_SUCCESS, expected);
    }

    @Test
    void executeInverse_throwsCommandException() {
        ListTaskCommand command = new ListTaskCommand();
        ModelManager planner = new ModelManager();
        assertThrows(CommandException.class, () -> command.executeInverse(planner));
    }
}
