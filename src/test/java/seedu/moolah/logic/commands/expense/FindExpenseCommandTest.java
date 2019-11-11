package seedu.moolah.logic.commands.expense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.moolah.commons.core.Messages.MESSAGE_EXPENSES_LISTED_OVERVIEW;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.testutil.TypicalMooLah.CHICKEN_RICE;
import static seedu.moolah.testutil.TypicalMooLah.ENTERTAINMENT;
import static seedu.moolah.testutil.TypicalMooLah.FASHION;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.expense.DescriptionContainsKeywordsPredicate;
import seedu.moolah.model.modelhistory.ModelChanges;
import seedu.moolah.model.modelhistory.ModelHistory;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindExpenseCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
    }

    @Test
    public void equals() {
        DescriptionContainsKeywordsPredicate firstPredicate =
                new DescriptionContainsKeywordsPredicate(Collections.singletonList("first"));
        DescriptionContainsKeywordsPredicate secondPredicate =
                new DescriptionContainsKeywordsPredicate(Collections.singletonList("second"));

        FindExpenseCommand findFirstCommand = new FindExpenseCommand(firstPredicate);
        FindExpenseCommand findSecondCommand = new FindExpenseCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindExpenseCommand findFirstCommandCopy = new FindExpenseCommand(firstPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(new Object(), findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different expense -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void run_zeroKeywords_noExpenseFound() {
        String expectedMessage = String.format(MESSAGE_EXPENSES_LISTED_OVERVIEW, 0);
        DescriptionContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindExpenseCommand command = new FindExpenseCommand(predicate);

        expectedModel.updateFilteredExpenseList(predicate);
        expectedModel.addToPastChanges(new ModelChanges(command.getDescription())
                .setExpensePredicate(model.getFilteredExpensePredicate()));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredExpenseList());
    }

    @Test
    public void run_multipleKeywords_multipleExpensesFound() {
        String expectedMessage = String.format(MESSAGE_EXPENSES_LISTED_OVERVIEW, 3);
        DescriptionContainsKeywordsPredicate predicate = preparePredicate("the Chicken Movie");
        FindExpenseCommand command = new FindExpenseCommand(predicate);

        expectedModel.updateFilteredExpenseList(predicate);
        expectedModel.addToPastChanges(new ModelChanges(command.getDescription())
                .setExpensePredicate(model.getFilteredExpensePredicate()));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CHICKEN_RICE, ENTERTAINMENT, FASHION), model.getFilteredExpenseList());
    }

    /**
     * Parses {@code userInput} into a {@code DescriptionContainsKeywordsPredicate}.
     */
    private DescriptionContainsKeywordsPredicate preparePredicate(String userInput) {
        return new DescriptionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
