package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPerformance.getTypicalPerformance;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAthletick;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TrainingManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.history.HistoryManager;
import seedu.address.model.person.TagMatchesPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FilterCommandTest {

    private Model model = new ModelManager(getTypicalAthletick(), getTypicalPerformance(), new TrainingManager(),
            new UserPrefs(), new HistoryManager());
    private Model expectedModel = new ModelManager(getTypicalAthletick(), getTypicalPerformance(),
            new TrainingManager(), new UserPrefs(), new HistoryManager());

    @Test
    public void equals() {
        TagMatchesPredicate firstPredicate =
                new TagMatchesPredicate(Collections.singletonList("first"));
        TagMatchesPredicate secondPredicate =
                new TagMatchesPredicate(Collections.singletonList("second"));

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand findFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand == null);

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        TagMatchesPredicate predicate = preparePredicate(" ");
        FilterCommand command = new FilterCommand(predicate);
        String expectedMessage = command.getNoMatchMessage();
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        TagMatchesPredicate predicate = preparePredicate("friends colleagues");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code TagMatchesPredicate}.
     */
    private TagMatchesPredicate preparePredicate(String userInput) {
        return new TagMatchesPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
