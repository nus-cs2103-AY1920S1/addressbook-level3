package seedu.billboard.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.billboard.commons.core.Messages.MESSAGE_EXPENSES_LISTED_OVERVIEW;
import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.billboard.testutil.TypicalExpenses.BILLS;
import static seedu.billboard.testutil.TypicalExpenses.FOOD;
import static seedu.billboard.testutil.TypicalExpenses.MOVIE;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboard;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.billboard.model.Model;
import seedu.billboard.model.ModelManager;
import seedu.billboard.model.UserPrefs;
import seedu.billboard.model.tag.ContainsTagPredicate;
import seedu.billboard.model.tag.Tag;

public class FilterTagCommandTest {
    private Model model = new ModelManager(getTypicalBillboard(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalBillboard(), new UserPrefs());

    @Test
    public void execute_zeroTag_noExpenseFound() {
        String expectedMessage = String.format(MESSAGE_EXPENSES_LISTED_OVERVIEW, 0);
        ContainsTagPredicate predicate = preparePredicate(" ");
        FilterTagCommand command = new FilterTagCommand(predicate);
        expectedModel.updateFilteredExpenses(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredExpenses());
    }

    @Test
    public void execute_multipleTags_multipleExpensesFound() {
        String expectedMessage = String.format(MESSAGE_EXPENSES_LISTED_OVERVIEW, 3);
        ContainsTagPredicate predicate = preparePredicate("bills leisure monday");
        FilterTagCommand command = new FilterTagCommand(predicate);
        expectedModel.updateFilteredExpenses(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BILLS, FOOD, MOVIE), model.getFilteredExpenses());
    }

    @Test
    public void equals() {
        ContainsTagPredicate firstPredicate = preparePredicate("first");
        ContainsTagPredicate secondPredicate = preparePredicate("second");

        FilterTagCommand filterFirstCommand = new FilterTagCommand(firstPredicate);
        FilterTagCommand filterSecondCommand = new FilterTagCommand(secondPredicate);

        // same object -> returns true
        assertEquals(filterFirstCommand, filterFirstCommand);

        // same values -> returns true
        FilterTagCommand filterFirstCommandCopy = new FilterTagCommand(firstPredicate);
        assertEquals(filterFirstCommand, filterFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, filterFirstCommand);

        // null -> returns false
        assertNotEquals(null, filterFirstCommand);

        // different expense -> returns false
        assertNotEquals(filterFirstCommand, filterSecondCommand);
    }

    /**
     * Parses {@code userInput} into a {@code ContainsTagPredicate}.
     */
    private ContainsTagPredicate preparePredicate(String userInput) {
        Set<Tag> set = new HashSet<>();
        String[] tags = userInput.split("\\s+");
        for (String tag : tags) {
            set.add(new Tag(tag));
        }
        return new ContainsTagPredicate(set);
    }
}
