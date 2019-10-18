package seedu.jarvis.logic.commands.cca;

import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.showCcaAtIndex;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_CCA;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_SECOND_CCA;
import static seedu.jarvis.testutil.address.TypicalPersons.getTypicalAddressBook;
import static seedu.jarvis.testutil.cca.TypicalCcas.CANOEING;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.financetracker.FinanceTracker;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCcaCommand}.
 */
public class DeleteCcaCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        CcaTracker ccaTracker = new CcaTracker();
        ccaTracker.addCca(CANOEING);
        model = new ModelManager(ccaTracker, new HistoryManager(), new FinanceTracker(), getTypicalAddressBook(),
                new UserPrefs(), new Planner(), new CoursePlanner());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Cca ccaToDelete = model.getFilteredCcaList().get(INDEX_FIRST_CCA.getZeroBased());
        DeleteCcaCommand deleteCcaCommand = new DeleteCcaCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCcaCommand.MESSAGE_DELETE_CCA_SUCCESS, ccaToDelete);

        Model expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(),
                model.getFinanceTracker(), model.getAddressBook(), new UserPrefs(),
                model.getPlanner(), model.getCoursePlanner());
        expectedModel.removeCca(ccaToDelete);

        assertCommandSuccess(deleteCcaCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCcaCommand deleteAddressCommand = new DeleteCcaCommand(outOfBoundIndex);

        assertCommandFailure(deleteAddressCommand, model, Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCcaAtIndex(model, INDEX_FIRST_CCA);

        Cca ccaToDelete = model.getFilteredCcaList().get(INDEX_FIRST_CCA.getZeroBased());
        DeleteCcaCommand deleteCcaCommand = new DeleteCcaCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCcaCommand.MESSAGE_DELETE_CCA_SUCCESS, ccaToDelete);

        Model expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(),
                model.getFinanceTracker(), model.getAddressBook(), new UserPrefs(),
                model.getPlanner(), model.getCoursePlanner());
        expectedModel.removeCca(ccaToDelete);

        assertCommandSuccess(deleteCcaCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCcaAtIndex(model, INDEX_FIRST_CCA);

        Index outOfBoundIndex = INDEX_SECOND_CCA;

        DeleteCcaCommand deleteCcaCommand = new DeleteCcaCommand(outOfBoundIndex);

        assertCommandFailure(deleteCcaCommand, model, Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCcaCommand deleteFirstCommand = new DeleteCcaCommand(INDEX_FIRST_CCA);
        DeleteCcaCommand deleteSecondCommand = new DeleteCcaCommand(INDEX_SECOND_CCA);

        // same object -> returns true
        Assertions.assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCcaCommand deleteFirstCommandCopy = new DeleteCcaCommand(INDEX_FIRST_PERSON);
        Assertions.assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        Assertions.assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        Assertions.assertFalse(deleteFirstCommand.equals(null));

        // different cca -> returns false
        Assertions.assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no cca.
     */
    private void showNoCca(Model model) {
        model.updateFilteredPersonList(p -> false);

        Assertions.assertTrue(model.getFilteredCcaList().isEmpty());
    }
}
