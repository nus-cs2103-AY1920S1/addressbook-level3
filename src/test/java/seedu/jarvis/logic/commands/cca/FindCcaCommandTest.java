package seedu.jarvis.logic.commands.cca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.jarvis.commons.core.Messages.MESSAGE_CCAS_LISTED_OVERVIEW;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.address.TypicalPersons.getTypicalAddressBook;
import static seedu.jarvis.testutil.cca.TypicalCcas.CANOEING;
import static seedu.jarvis.testutil.cca.TypicalCcas.GUITAR_ENSEMBLE;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.cca.CcaNameContainsKeywordsPredicate;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.financetracker.FinanceTracker;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCcaCommand}.
 */
public class FindCcaCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new CcaTracker(), new HistoryManager(), new FinanceTracker(), getTypicalAddressBook(),
                new UserPrefs(), new Planner(), new CoursePlanner());
        expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(),
                model.getFinanceTracker(), model.getAddressBook(), new UserPrefs(),
                model.getPlanner(), model.getCoursePlanner());
    }

    /**
     * Verifies that checking {@code FindCcaCommand} for the availability of inverse execution returns false.
     */
    @Test
    public void hasInverseExecution() {
        CcaNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCcaCommand findCcaCommand = new FindCcaCommand(predicate);
        assertFalse(findCcaCommand.hasInverseExecution());
    }

    @Test
    public void equals() {
        CcaNameContainsKeywordsPredicate firstPredicate =
                new CcaNameContainsKeywordsPredicate(Collections.singletonList("first"));
        CcaNameContainsKeywordsPredicate secondPredicate =
                new CcaNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCcaCommand findFirstCommand = new FindCcaCommand(firstPredicate);
        FindCcaCommand findSecondCommand = new FindCcaCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCcaCommand findFirstCommandCopy = new FindCcaCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different cca -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noCcaFound() {
        String expectedMessage = String.format(MESSAGE_CCAS_LISTED_OVERVIEW, 0);
        CcaNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCcaCommand command = new FindCcaCommand(predicate);
        expectedModel.updateFilteredCcaList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCcaList());
    }

    @Test
    public void execute_multipleKeywords_multipleCcasFound() {
        String expectedMessage = String.format(MESSAGE_CCAS_LISTED_OVERVIEW, 2);
        CcaNameContainsKeywordsPredicate predicate = preparePredicate("Canoeing Guitar");
        FindCcaCommand command = new FindCcaCommand(predicate);
        expectedModel.updateFilteredCcaList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CANOEING, GUITAR_ENSEMBLE), model.getFilteredCcaList());
    }

    /**
     * Verifies that calling inverse execution of {@code FindCcaCommand} will always throw a
     * {@code CommandException} with the correct message.
     */
    @Test
    public void executeInverse_throwsCommandException() {
        CcaNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCcaCommand findCcaCommand = new FindCcaCommand(predicate);
        assertThrows(CommandException.class,
                FindCcaCommand.MESSAGE_NO_INVERSE, () -> findCcaCommand.executeInverse(model));
    }

    /**
     * Parses {@code userInput} into a {@code CcaNameContainsKeywordsPredicate}.
     */
    private CcaNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new CcaNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
