package seedu.jarvis.logic.commands.finance;

import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_INSTALLMENT;
import static seedu.jarvis.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.financetracker.FinanceTracker;
import seedu.jarvis.model.financetracker.Installment;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.userprefs.UserPrefs;

public class RemoveInstallmentCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new HistoryManager(), new FinanceTracker(), getTypicalAddressBook(), new UserPrefs());
        model.addInstallment(new InstallmentStub());
        model.addInstallment(new InstallmentStub());
        model.addInstallment(new InstallmentStub());
    }

    /**
     * Verifies that checking {@code RemoveInstallmentCommand} for the availability of inverse execution returns true.
     */
    @Test
    public void hasInverseExecution() {
        RemoveInstallmentCommand removeInstallmentCommand = new RemoveInstallmentCommand(INDEX_FIRST_INSTALLMENT);
        Assertions.assertTrue(removeInstallmentCommand.hasInverseExecution());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Installment installmentToDelete = model.getInstallment(INDEX_FIRST_INSTALLMENT.getOneBased());
        RemoveInstallmentCommand removeInstallmentCommand = new RemoveInstallmentCommand(INDEX_FIRST_INSTALLMENT);

        String expectedMessage = String.format(RemoveInstallmentCommand.MESSAGE_DELETE_INSTALLMENT_SUCCESS,
                installmentToDelete);

        ModelManager expectedModel = new ModelManager(model.getHistoryManager(), model.getFinanceTracker(),
                model.getAddressBook(), new UserPrefs());
        expectedModel.deleteInstallment(INDEX_FIRST_INSTALLMENT.getOneBased());

        assertCommandSuccess(removeInstallmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getInstallmentList().size() + 1);
        RemoveInstallmentCommand removeInstallmentCommand = new RemoveInstallmentCommand(outOfBoundIndex);

        assertCommandFailure(removeInstallmentCommand, model, Messages.MESSAGE_INVALID_INSTALLMENT_DISPLAYED_INDEX);
    }

    private static class InstallmentStub extends Installment {
        public InstallmentStub() {
            super("Spotify subscription", 9.5);
        }
    }
}
