package seedu.pluswork.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pluswork.commons.core.Messages.MESSAGE_TASKS_LISTED_OVERVIEW;
import static seedu.pluswork.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pluswork.testutil.TypicalTasksMembers.BRIEFING_PREPARATION;
import static seedu.pluswork.testutil.TypicalTasksMembers.GET_SPONSORS;
import static seedu.pluswork.testutil.TypicalTasksMembers.ORGANISE_STORE;
import static seedu.pluswork.testutil.TypicalTasksMembers.getTypicalProjectDashboard;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.pluswork.logic.commands.task.FindTaskCommand;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.ModelManager;
import seedu.pluswork.model.UserPrefs;
import seedu.pluswork.model.UserSettings;
import seedu.pluswork.model.task.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindTaskCommand}.
 */
public class FindTaskCommandTest {
    private Model model = new ModelManager(getTypicalProjectDashboard(), new UserPrefs(), new UserSettings());
    private Model expectedModel = new ModelManager(getTypicalProjectDashboard(), new UserPrefs(), new UserSettings());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindTaskCommand findFirstCommand = new FindTaskCommand(firstPredicate);
        FindTaskCommand findSecondCommand = new FindTaskCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindTaskCommand findFirstCommandCopy = new FindTaskCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noTaskFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindTaskCommand command = new FindTaskCommand(predicate);
        expectedModel.updateFilteredTasksList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTasksList());
    }

    @Test
    public void execute_multipleKeywords_multipleTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("sponsors presentation store");
        FindTaskCommand command = new FindTaskCommand(predicate);
        expectedModel.updateFilteredTasksList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GET_SPONSORS, BRIEFING_PREPARATION, ORGANISE_STORE), model.getFilteredTasksList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
