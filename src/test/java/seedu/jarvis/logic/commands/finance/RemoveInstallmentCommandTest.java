package seedu.jarvis.logic.commands.finance;

import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandInverseFailure;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandInverseSuccess;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.model.financetracker.FinanceTrackerModel.PREDICATE_SHOW_ALL_INSTALLMENTS;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_INSTALLMENT;
import static seedu.jarvis.testutil.address.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.financetracker.FinanceTracker;
import seedu.jarvis.model.financetracker.installment.Installment;
import seedu.jarvis.model.financetracker.installment.InstallmentDescription;
import seedu.jarvis.model.financetracker.installment.InstallmentMoneyPaid;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.UserPrefs;
import seedu.jarvis.testutil.finance.InstallmentBuilder;

public class RemoveInstallmentCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new CcaTracker(), new HistoryManager(), new FinanceTracker(), getTypicalAddressBook(),
                new UserPrefs(), new Planner(), new CoursePlanner());
        model.addInstallment(new InstallmentBuilder().build());
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
    public void execute_validIndexUnfilteredList_success() throws CommandException {
        Installment installmentToDelete = model.getInstallment(INDEX_FIRST_INSTALLMENT.getOneBased());
        RemoveInstallmentCommand removeInstallmentCommand = new RemoveInstallmentCommand(INDEX_FIRST_INSTALLMENT);

        String expectedMessage = String.format(RemoveInstallmentCommand.MESSAGE_DELETE_INSTALLMENT_SUCCESS,
                installmentToDelete);

        ModelManager expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(),
                model.getFinanceTracker(), model.getAddressBook(), new UserPrefs(),
                model.getPlanner(), model.getCoursePlanner());
        expectedModel.deleteInstallment(INDEX_FIRST_INSTALLMENT.getOneBased());

        assertCommandSuccess(removeInstallmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInstallmentList().size() + 1);
        RemoveInstallmentCommand removeInstallmentCommand = new RemoveInstallmentCommand(outOfBoundIndex);

        assertCommandFailure(removeInstallmentCommand, model, Messages.MESSAGE_INVALID_INSTALLMENT_DISPLAYED_INDEX);
    }

    /**
     * Ensures that {@code CommandException} is thrown if re-adding the installment that was deleted will be in conflict
     * with existing installment in the finance tracker.
     */
    @Test
    public void executeInverse_installmentToAddAlreadyExist_throwsCommandException() {
        Installment installmentToDelete = model
                .getFilteredInstallmentList()
                .get(INDEX_FIRST_INSTALLMENT.getZeroBased());
        RemoveInstallmentCommand removeInstallmentCommand = new RemoveInstallmentCommand(INDEX_FIRST_INSTALLMENT);

        String expectedMessage = String.format(RemoveInstallmentCommand.MESSAGE_DELETE_INSTALLMENT_SUCCESS,
                installmentToDelete);

        Model expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(),
                model.getFinanceTracker(), model.getAddressBook(), new UserPrefs(),
                model.getPlanner(), model.getCoursePlanner());
        expectedModel.deleteInstallment(installmentToDelete);

        assertCommandSuccess(removeInstallmentCommand, model, expectedMessage, expectedModel);

        String inverseExpectedMessage = String.format(
                RemoveInstallmentCommand.MESSAGE_INVERSE_INSTALLMENT_TO_ADD_ALREADY_EXIST, installmentToDelete);

        model.addInstallment(installmentToDelete);
        assertCommandInverseFailure(removeInstallmentCommand, model, inverseExpectedMessage);
    }

    /**
     * Ensures that the {@code CommandResult} with the appropriate message is returned from a successful inverse
     * execution, that the deleted installment was added back to the finance tracker.
     */
    @Test
    public void executeInverse_success() {
        Installment installmentToDelete = model
                .getFilteredInstallmentList()
                .get(INDEX_FIRST_INSTALLMENT.getZeroBased());
        RemoveInstallmentCommand removeInstallmentCommand = new RemoveInstallmentCommand(INDEX_FIRST_INSTALLMENT);

        String expectedMessage = String.format(RemoveInstallmentCommand.MESSAGE_DELETE_INSTALLMENT_SUCCESS,
                installmentToDelete);
        Model expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(),
                model.getFinanceTracker(), model.getAddressBook(), new UserPrefs(),
                model.getPlanner(), model.getCoursePlanner());
        expectedModel.deleteInstallment(installmentToDelete);
        assertCommandSuccess(removeInstallmentCommand, model, expectedMessage, expectedModel);

        String inverseExpectedMessage = String.format(
                RemoveInstallmentCommand.MESSAGE_INVERSE_SUCCESS_ADD, installmentToDelete);
        model.deleteInstallment(installmentToDelete);

        expectedModel.addInstallment(INDEX_FIRST_INSTALLMENT.getZeroBased(), installmentToDelete);
        expectedModel.updateFilteredInstallmentList(PREDICATE_SHOW_ALL_INSTALLMENTS);
        assertCommandInverseSuccess(removeInstallmentCommand, model, inverseExpectedMessage, expectedModel);
    }

    private static class InstallmentStub extends Installment {
        public InstallmentStub() {
            super(new InstallmentDescription("Spotify subscription"), new InstallmentMoneyPaid("9.5"));
        }
    }
}
