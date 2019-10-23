package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalExpenses.getTypicalExchangeData;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseList;

import java.util.Arrays;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.budget.BudgetList;
import seedu.address.model.expense.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {

    private Model model = new ModelManager(getTypicalExpenseList(), new BudgetList(),
        getTypicalExchangeData(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalExpenseList(), new BudgetList(),
        getTypicalExchangeData(), new UserPrefs());

    //    @Test
    //    public void equals() {
    //        NameContainsKeywordsPredicate firstPredicate =
    //            new NameContainsKeywordsPredicate(Collections.singletonList("first"));
    //        NameContainsKeywordsPredicate secondPredicate =
    //            new NameContainsKeywordsPredicate(Collections.singletonList("second"));
    //
    //        FindCommand findFirstCommand = new FindCommand(firstPredicate);
    //        FindCommand findSecondCommand = new FindCommand(secondPredicate);
    //
    //        // same object -> returns true
    //        assertTrue(findFirstCommand.equals(findFirstCommand));
    //
    //        // same values -> returns true
    //        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
    //        assertTrue(findFirstCommand.equals(findFirstCommandCopy));
    //
    //        // different types -> returns false
    //        assertFalse(findFirstCommand.equals(1));
    //
    //        // null -> returns false
    //         String expectedMessage = String.format(MESSAGE_EXPENSE_LISTED_OVERVIEW, 0);
    //        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
    //        FindCommand command = new FindCommand(predicate);
    //        expectedModel.updateFilteredExpenseList(predicate);
    //        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    //        assertEquals(Collections.emptyList(), model.getFilteredExpenseList());
    //    }

    //    @Test
    //    public void execute_multipleKeywords_multipleExpensesFound() {
    //        String expectedMessage = String.format(MESSAGE_EXPENSES_LISTED_OVERVIEW, 3);
    //        NameContainsKeywordsPredicate predicate = preparePredicate("shoe Chocolates Socks");
    //        FindCommand command = new FindCommand(predicate);
    //        expectedModel.updateFilteredExpenseList(predicate);
    //        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    //        assertEquals(Arrays.asList(SHOPPING, VALENTINES, CHRISTMAS), model.getFilteredExpenseList());
    //    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
