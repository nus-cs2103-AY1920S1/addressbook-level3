package dukecooks.logic.commands.dashboard;

import static dukecooks.testutil.dashboard.TypicalDashboard.getTypicalDashboardRecords;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.dashboard.components.DashboardNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindTaskCommand}.
 */
public class FindTaskCommandTest {
    private Model model = new ModelManager(getTypicalDashboardRecords(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalDashboardRecords(), new UserPrefs());

    @Test
    public void equals() {
        DashboardNameContainsKeywordsPredicate firstPredicate =
                new DashboardNameContainsKeywordsPredicate(Collections.singletonList("first"));
        DashboardNameContainsKeywordsPredicate secondPredicate =
                new DashboardNameContainsKeywordsPredicate(Collections.singletonList("second"));

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

        // different dashboard -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    /**
     * Parses {@code userInput} into a {@code DashboardNameContainsKeywordsPredicate}.
     */
    private DashboardNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new DashboardNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
