package seedu.billboard.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.billboard.commons.core.Messages.MESSAGE_EXPENSES_LISTED_OVERVIEW;
import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.billboard.testutil.TypicalExpenses.FOOD;
import static seedu.billboard.testutil.TypicalExpenses.GROCERIES;
import static seedu.billboard.testutil.TypicalExpenses.MOVIE;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboard;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.billboard.model.Model;
import seedu.billboard.model.ModelManager;
import seedu.billboard.model.UserPrefs;
import seedu.billboard.model.expense.MultiArgPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalBillboard(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalBillboard(), new UserPrefs());
    }

    @Test
    public void equals() {
        MultiArgPredicate firstPredicate = new MultiArgPredicate();
        firstPredicate.setKeywords(Collections.singletonList("first"));
        MultiArgPredicate secondPredicate = new MultiArgPredicate();
        secondPredicate.setKeywords(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different expense -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void execute_zeroKeywords_noExpenseFound() {
        String expectedMessage = String.format(MESSAGE_EXPENSES_LISTED_OVERVIEW, 0);
        MultiArgPredicate predicate = new MultiArgPredicate();
        predicate.setKeywords(Arrays.asList("  ".split("\\s+")));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredExpenses(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredExpenses());
    }

    @Test
    public void execute_multipleKeywords_multipleExpensesFound() {
        String expectedMessage = String.format(MESSAGE_EXPENSES_LISTED_OVERVIEW, 3);
        MultiArgPredicate predicate = new MultiArgPredicate();
        predicate.setKeywords(Arrays.asList("food movie groceries".split("\\s+")));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredExpenses(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FOOD, GROCERIES, MOVIE), model.getFilteredExpenses());
    }
}
