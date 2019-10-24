package seedu.jarvis.logic.commands.finance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.logic.commands.CommandTestUtil.INSTALL_NETFLIX;
import static seedu.jarvis.logic.commands.CommandTestUtil.INSTALL_SPOTIFY;
import static seedu.jarvis.logic.commands.CommandTestUtil.VALID_DESC_EARPHONES;
import static seedu.jarvis.logic.commands.CommandTestUtil.VALID_DESC_SPOTIFY;
import static seedu.jarvis.logic.commands.CommandTestUtil.VALID_MONEY_EARPHONES;
import static seedu.jarvis.logic.commands.CommandTestUtil.VALID_MONEY_SPOTIFY;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandInverseSuccess;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_INSTALLMENT;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_SECOND_INSTALLMENT;
import static seedu.jarvis.testutil.address.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.commands.finance.EditInstallmentCommand.EditInstallmentDescriptor;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.financetracker.FinanceTracker;
import seedu.jarvis.model.financetracker.installment.Installment;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.UserPrefs;
import seedu.jarvis.testutil.finance.EditInstallmentDescriptorBuilder;
import seedu.jarvis.testutil.finance.InstallmentBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditInstallmentCommand.
 */
public class EditInstallmentCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new CcaTracker(), new HistoryManager(), new FinanceTracker(), getTypicalAddressBook(),
                new UserPrefs(), new Planner(), new CoursePlanner());
        model.addInstallment(new InstallmentBuilder().build());
        model.addInstallment(new InstallmentBuilder().withDescription("Headphones").build());
        model.addInstallment(new InstallmentBuilder().build());
    }

    /**
     * Verifies that checking {@code EditAddressCommand} for the availability of inverse execution returns true.
     */
    @Test
    public void hasInverseExecution() {
        Installment editedInstallment = new InstallmentBuilder().build();
        EditInstallmentDescriptor descriptor =
                new EditInstallmentDescriptorBuilder(editedInstallment).build();
        EditInstallmentCommand editAddressCommand = new EditInstallmentCommand(INDEX_FIRST_INSTALLMENT, descriptor);
        assertTrue(editAddressCommand.hasInverseExecution());
    }

    @Test
    public void execute_allFieldsList_success() {
        Installment editedInstallment =
                new InstallmentBuilder()
                        .withDescription(VALID_DESC_SPOTIFY)
                        .withMoneySpent(VALID_MONEY_SPOTIFY)
                        .build();
        EditInstallmentDescriptor descriptor =
                new EditInstallmentDescriptorBuilder(editedInstallment)
                        .build();
        EditInstallmentCommand editInstallmentCommand = new EditInstallmentCommand(INDEX_FIRST_INSTALLMENT, descriptor);

        String expectedMessage = String.format(EditInstallmentCommand.MESSAGE_EDIT_INSTALLMENT_SUCCESS,
                editedInstallment);
        Model expectedModel = new ModelManager(new CcaTracker(), new HistoryManager(), new FinanceTracker(),
                getTypicalAddressBook(), new UserPrefs(), new Planner(), new CoursePlanner());

        expectedModel.addInstallment(new InstallmentBuilder().build());
        expectedModel.addInstallment(new InstallmentBuilder().withDescription("Headphones").build());
        expectedModel.addInstallment(new InstallmentBuilder().build());
        expectedModel.setInstallment(model.getFilteredInstallmentList().get(0), editedInstallment);

        assertCommandSuccess(editInstallmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsList_success() throws CommandException {
        Index indexLastInstallment = Index.fromOneBased(model.getFilteredInstallmentList().size());
        Installment lastInstallment = model.getInstallment(indexLastInstallment.getOneBased());

        InstallmentBuilder installmentInList = new InstallmentBuilder(lastInstallment);
        Installment editedInstallment = installmentInList
                .withDescription(
                        VALID_DESC_EARPHONES)
                .withMoneySpent(
                        VALID_MONEY_EARPHONES)
                .build();

        EditInstallmentDescriptor descriptor =
                new EditInstallmentDescriptorBuilder()
                        .withDescription(VALID_DESC_EARPHONES)
                        .withSubscriptionFee(VALID_MONEY_EARPHONES)
                        .build();

        EditInstallmentCommand editInstallmentCommand = new EditInstallmentCommand(indexLastInstallment, descriptor);
        String expectedMessage = String.format(EditInstallmentCommand.MESSAGE_EDIT_INSTALLMENT_SUCCESS,
                editedInstallment);

        Model expectedModel = new ModelManager(new CcaTracker(), new HistoryManager(), new FinanceTracker(),
                getTypicalAddressBook(), new UserPrefs(), new Planner(), new CoursePlanner());
        expectedModel.addInstallment(new InstallmentBuilder().build());
        expectedModel.addInstallment(new InstallmentBuilder().withDescription("Headphones").build());
        expectedModel.addInstallment(new InstallmentBuilder().build());

        expectedModel.setInstallment(lastInstallment, editedInstallment);

        assertCommandSuccess(editInstallmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldsList_failure() throws CommandException {
        EditInstallmentCommand editInstallmentCommand = new EditInstallmentCommand(INDEX_FIRST_INSTALLMENT,
                new EditInstallmentDescriptor());
        Installment editedInstallment = model.getInstallment(INDEX_FIRST_INSTALLMENT.getOneBased());

        String expectedMessage = String.format(EditInstallmentCommand.MESSAGE_DUPLICATE_INSTALLMENT,
                editedInstallment);

        Model expectedModel = new ModelManager(new CcaTracker(), new HistoryManager(), new FinanceTracker(),
                getTypicalAddressBook(), new UserPrefs(), new Planner(), new CoursePlanner());

        expectedModel.addInstallment(new InstallmentBuilder().build());
        expectedModel.addInstallment(new InstallmentBuilder().withDescription("Headphones").build());
        expectedModel.addInstallment(new InstallmentBuilder().build());

        assertCommandFailure(editInstallmentCommand, model, expectedMessage);
    }

    @Test
    public void execute_duplicateInstallmentList_failure() throws CommandException {
        Installment firstInstallment = model.getInstallment(INDEX_FIRST_INSTALLMENT.getOneBased());
        EditInstallmentDescriptor descriptor =
                new EditInstallmentDescriptorBuilder(firstInstallment).build();
        EditInstallmentCommand editInstallmentCommand = new EditInstallmentCommand(INDEX_FIRST_INSTALLMENT,
                descriptor);

        assertCommandFailure(editInstallmentCommand, model, EditInstallmentCommand.MESSAGE_DUPLICATE_INSTALLMENT);
    }

    @Test
    public void execute_invalidInstallmentIndexList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInstallmentList().size() + 1);
        EditInstallmentDescriptor descriptor = new EditInstallmentDescriptorBuilder()
                .withDescription(VALID_DESC_EARPHONES)
                .build();
        EditInstallmentCommand editInstallmentCommand = new EditInstallmentCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editInstallmentCommand, model, Messages.MESSAGE_INVALID_INSTALLMENT_DISPLAYED_INDEX);
    }

    /**
     * Ensures that the {@code CommandResult} with the appropriate message is returned from a successful inverse
     * execution, that the edited installment had its edits reversed back in the finance tracker.
     */
    @Test
    public void executeInverse_success() {
        Installment originalInstallment = model
                .getFilteredInstallmentList()
                .get(INDEX_SECOND_INSTALLMENT.getZeroBased());

        Installment editedInstallment = new InstallmentBuilder()
                .withDescription(
                        VALID_DESC_EARPHONES)
                .withMoneySpent(
                        VALID_MONEY_EARPHONES)
                .build();

        EditInstallmentDescriptor descriptor =
                new EditInstallmentDescriptorBuilder()
                        .withDescription(VALID_DESC_EARPHONES)
                        .withSubscriptionFee(VALID_MONEY_EARPHONES)
                        .build();

        EditInstallmentCommand editInstallmentCommand = new EditInstallmentCommand(INDEX_SECOND_INSTALLMENT,
                descriptor);
        String expectedMessage = String.format(EditInstallmentCommand.MESSAGE_EDIT_INSTALLMENT_SUCCESS,
                editedInstallment);
        Model expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(),
                model.getFinanceTracker(), model.getAddressBook(), new UserPrefs(),
                model.getPlanner(), model.getCoursePlanner());
        expectedModel.setInstallment(originalInstallment, editedInstallment);

        assertCommandSuccess(editInstallmentCommand, model, expectedMessage, expectedModel);
        String inverseExpectedMessage = String.format(
                EditInstallmentCommand.MESSAGE_INVERSE_SUCCESS_REVERSE, originalInstallment);

        expectedModel.setInstallment(editedInstallment, originalInstallment);
        assertCommandInverseSuccess(editInstallmentCommand, model, inverseExpectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final EditInstallmentCommand standardCommand =
                new EditInstallmentCommand(INDEX_FIRST_INSTALLMENT, INSTALL_NETFLIX);

        // same values -> returns true
        EditInstallmentDescriptor copyDescriptor =
                new EditInstallmentCommand.EditInstallmentDescriptor(INSTALL_NETFLIX);
        EditInstallmentCommand commandWithSameValues = new EditInstallmentCommand(INDEX_FIRST_INSTALLMENT,
                copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditInstallmentCommand(INDEX_SECOND_INSTALLMENT, INSTALL_NETFLIX)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditInstallmentCommand(INDEX_FIRST_INSTALLMENT, INSTALL_SPOTIFY)));
    }

}
