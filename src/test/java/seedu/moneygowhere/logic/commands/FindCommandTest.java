package seedu.moneygowhere.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.commons.core.Messages.MESSAGE_SPENDINGS_LISTED_OVERVIEW;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moneygowhere.testutil.TypicalSpendings.CATFOOD;
import static seedu.moneygowhere.testutil.TypicalSpendings.ENCYCLOPEDIA;
import static seedu.moneygowhere.testutil.TypicalSpendings.FLIGHTTICKET;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.UserPrefs;
import seedu.moneygowhere.model.spending.NameContainsKeywordsPredicate;
import seedu.moneygowhere.model.spending.Spending;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalSpendingBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        List<Predicate<Spending>> predicates = new ArrayList<>();
        predicates.add(firstPredicate);

        FindCommand findFirstCommand = new FindCommand(predicates);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(predicates);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicate list -> returns false
        List<Predicate<Spending>> secondPredicateList = new ArrayList<>();
        secondPredicateList.add(secondPredicate);

        FindCommand findSecondCommand = new FindCommand(secondPredicateList);
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_emptyPredicate_noSpendingFound() {
        String expectedMessage = String.format(MESSAGE_SPENDINGS_LISTED_OVERVIEW, 0);

        List<Predicate<Spending>> predicates = new ArrayList<>();

        FindCommand command = new FindCommand(predicates);
        expectedModel.updateFilteredSpendingList(failed -> false);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredSpendingList());
    }

    @Test
    public void execute_zeroKeywords_noSpendingFound() {
        String expectedMessage = String.format(MESSAGE_SPENDINGS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");

        List<Predicate<Spending>> predicates = new ArrayList<>();
        predicates.add(predicate);

        FindCommand command = new FindCommand(predicates);
        expectedModel.updateFilteredSpendingList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredSpendingList());
    }

    @Test
    public void execute_multipleKeywords_multipleSpendingsFound() {
        String expectedMessage = String.format(MESSAGE_SPENDINGS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("flight encyclopedia cat");
        List<Predicate<Spending>> predicates = new ArrayList<>();
        predicates.add(predicate);

        FindCommand command = new FindCommand(predicates);
        expectedModel.updateFilteredSpendingList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CATFOOD, ENCYCLOPEDIA, FLIGHTTICKET), model.getFilteredSpendingList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
