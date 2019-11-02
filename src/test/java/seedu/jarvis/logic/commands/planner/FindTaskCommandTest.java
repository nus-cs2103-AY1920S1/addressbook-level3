package seedu.jarvis.logic.commands.planner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.finance.FinanceTracker;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.planner.TaskDesContainsKeywordsPredicate;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;
import seedu.jarvis.model.userprefs.UserPrefs;

class FindTaskCommandTest {

    @Test
    void getCommandWord_success() {
        String expected = "find-task";
        String actual = FindTaskCommand.COMMAND_WORD;

        assertEquals(expected, actual);
    }

    /**
     * Verifies that checking {@code FindTaskCommand} for the availability of the inverse
     * execution returns false.
     */
    @Test
    void hasInverseExecution() {
        TaskDesContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindTaskCommand command = new FindTaskCommand(predicate);

        assertFalse(command.hasInverseExecution());
    }

    @Test
    void execute_zeroKeywords_noTaskFound() {
        Model model = new ModelManager(new CcaTracker(), new HistoryManager(), new FinanceTracker(),
                new UserPrefs(), new Planner(), new CoursePlanner());
        Model expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(),
                model.getFinanceTracker(), new UserPrefs(), model.getPlanner(), model.getCoursePlanner());

        String expected = String.format(FindTaskCommand.MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        TaskDesContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindTaskCommand command = new FindTaskCommand(predicate);
        //expectedModel.updateFilteredTaskList(predicate);
        //assertCommandSuccess(command, model, expected, expectedModel);
        //assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    void execute_multipleKeywords_multiplePersonsFound() {
        Model model = new ModelManager(new CcaTracker(), new HistoryManager(), new FinanceTracker(),
                new UserPrefs(), new Planner(), new CoursePlanner());
        Model expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(),
                model.getFinanceTracker(), new UserPrefs(), model.getPlanner(), model.getCoursePlanner());

        String expectedMessage = String.format(FindTaskCommand.MESSAGE_TASKS_LISTED_OVERVIEW, 1);
        TaskDesContainsKeywordsPredicate predicate = preparePredicate("book");

        Task t = new Todo("borrow book");
        model.addTask(t);
        expectedModel.addTask(t);

        FindTaskCommand command = new FindTaskCommand(predicate);
        //expectedModel.updateFilteredTaskList(predicate);
        //assertCommandSuccess(command, model, expectedMessage, expectedModel);
        //assertEquals(Arrays.asList(t), model.getFilteredTaskList());
    }

    /**
     * Verifies that calling inverse execution of {@code FindTaskCommand} will always throw a
     * {@code CommandException} with the correct message.
     */
    @Test
    void executeInverse_throwsCommandException() {
        Model model = new ModelManager();
        TaskDesContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindTaskCommand command = new FindTaskCommand(predicate);

        assertThrows(CommandException.class, () -> command.executeInverse(model));
    }

    @Test
    void testEquals() {
        TaskDesContainsKeywordsPredicate firstPredicate =
                new TaskDesContainsKeywordsPredicate(Collections.singletonList("first"));
        TaskDesContainsKeywordsPredicate secondPredicate =
                new TaskDesContainsKeywordsPredicate(Collections.singletonList("second"));

        FindTaskCommand findFirstCommand = new FindTaskCommand(firstPredicate);
        FindTaskCommand findSecondCommand = new FindTaskCommand(secondPredicate);

        //same object
        assertEquals(findFirstCommand, findFirstCommand);

        //same values
        FindTaskCommand copy = new FindTaskCommand(firstPredicate);
        assertEquals(findFirstCommand, copy);

        //different types
        assertNotEquals(1, findFirstCommand);

        //null
        assertNotNull(findFirstCommand);

        //different task description
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    /**
     * Parses {@code userInput} into a {@code TaskDesContainsKeywordsPredicate}
     * @param userInput the keywords the user wants to match the tasks with
     * @return {@code TaskDesContainsKeywordsPredicate} containing the keywords by the user
     */
    private TaskDesContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TaskDesContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
