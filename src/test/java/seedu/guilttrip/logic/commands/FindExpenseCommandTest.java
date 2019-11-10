package seedu.guilttrip.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.guilttrip.testutil.TypicalEntries.getTypicalGuiltTrip;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.logic.commands.findcommands.FindExpenseCommand;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.ModelManager;
import seedu.guilttrip.model.UserPrefs;
import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.entry.predicates.EntryContainsAmountPredicate;
import seedu.guilttrip.model.entry.predicates.EntryContainsDescriptionPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindExpenseCommand}.
 */
public class FindExpenseCommandTest {
    private Model model = new ModelManager(getTypicalGuiltTrip(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalGuiltTrip(), new UserPrefs());

    @Test
    public void equals() {
        EntryContainsDescriptionPredicate firstDescPredicate =
                new EntryContainsDescriptionPredicate(Collections.singletonList("pgp mala"));
        EntryContainsDescriptionPredicate secondDescPredicate =
                new EntryContainsDescriptionPredicate(Collections.singletonList("buying food second"));

        EntryContainsAmountPredicate firstAmountPredicate =
                new EntryContainsAmountPredicate(5.00);
        EntryContainsAmountPredicate secondAmountPredicate =
                new EntryContainsAmountPredicate(15.00);

        List<Predicate<Entry>> firstPredicate = Arrays.asList(firstDescPredicate);
        List<Predicate<Entry>> secondPredicate = Arrays.asList(secondDescPredicate);
        List<Predicate<Entry>> firstMultiplePredicate = Arrays.asList(firstDescPredicate, firstAmountPredicate);
        List<Predicate<Entry>> secondMultiplePredicate = Arrays.asList(secondDescPredicate, secondAmountPredicate);

        //Comparisons of only one predicate
        FindExpenseCommand findFirstCommand = new FindExpenseCommand(firstPredicate);
        FindExpenseCommand findSecondCommand = new FindExpenseCommand(secondPredicate);

        //Comparisons of two predicates
        FindExpenseCommand findMultipleFirstCommand = new FindExpenseCommand(firstMultiplePredicate);
        FindExpenseCommand findMultipleSecondCommand = new FindExpenseCommand(secondMultiplePredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));
        assertTrue(findMultipleFirstCommand.equals(findMultipleFirstCommand));

        // same values -> returns true
        FindExpenseCommand findFirstCommandCopy = new FindExpenseCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));
        FindExpenseCommand findMultipleFirstCommandCopy = new FindExpenseCommand(firstMultiplePredicate);
        assertTrue(findMultipleFirstCommand.equals(findMultipleFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different entry -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    /*@Test
    public void execute_multipleKeywords_multipleExpensesFound() {
        CommandHistory chs = new CommandHistoryStub();
        String expectedMessage = String.format(MESSAGE_ENTRIES_LISTED_OVERVIEW, model.getFilteredExpenses().size() - 1);
        EntryContainsDescriptionPredicate predicate = preparePredicate("mala cotton");
        List<Predicate<Entry>> predicateToPassIn = Arrays.asList(predicate);
        FindExpenseCommand command = new FindExpenseCommand(predicateToPassIn);
        expectedModel.updateFilteredExpenses(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, chs);
        assertEquals(Arrays.asList(TypicalEntries.CLOTHING_EXPENSE, TypicalEntries.FOOD_EXPENSE),
                model.getFilteredExpenses());
    }*/

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private EntryContainsDescriptionPredicate preparePredicate(String userInput) {
        return new EntryContainsDescriptionPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
