
package seedu.jarvis.logic.commands.finance;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandInverseFailure;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandInverseSuccess;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.address.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.address.AddressBook;
import seedu.jarvis.model.address.ReadOnlyAddressBook;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.financetracker.FinanceTracker;
import seedu.jarvis.model.financetracker.installment.Installment;
import seedu.jarvis.model.financetracker.installment.InstallmentDescription;
import seedu.jarvis.model.financetracker.installment.InstallmentMoneyPaid;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.UserPrefs;
import seedu.jarvis.testutil.ModelStub;
import seedu.jarvis.testutil.finance.InstallmentBuilder;

public class SetInstallmentCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new CcaTracker(), new HistoryManager(), new FinanceTracker(), getTypicalAddressBook(),
                new UserPrefs(), new Planner(), new CoursePlanner());
        model.addInstallment(new InstallmentStub());
        model.addInstallment(new InstallmentStub());
        model.addInstallment(new InstallmentStub());
    }

    /**
     * Verifies that checking {@code SetInstallmentCommand} for the availability of inverse execution returns true.
     */
    @BeforeEach
    public void hasInverseExecution() {
        Installment validInstallment = new InstallmentBuilder().build();
        SetInstallmentCommand setInstallmentCommand = new SetInstallmentCommand(validInstallment);
        assertTrue(setInstallmentCommand.hasInverseExecution());
    }

    @Test
    public void constructor_nullInstallment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SetInstallmentCommand(null));
    }

    @Test
    public void execute_installmentAcceptedByModel_addSuccessful() throws CommandException {
        ModelStubAcceptingInstallmentAdded modelStub = new ModelStubAcceptingInstallmentAdded();
        Installment validInstallment = new InstallmentBuilder().build();

        CommandResult commandResult = new SetInstallmentCommand(validInstallment).execute(modelStub);

        assertEquals(String.format(SetInstallmentCommand.MESSAGE_SUCCESS, validInstallment),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validInstallment), modelStub.installmentsAdded);
    }

    @Test
    public void execute_installmentAlreadyExists_throwsCommandException() {
        ModelStubAcceptingInstallmentAdded modelStub = new ModelStubAcceptingInstallmentAdded();
        modelStub.addInstallment(new InstallmentStub());

        SetInstallmentCommand setInstallmentCommand = new SetInstallmentCommand(new InstallmentStub());

        assertThrows(CommandException.class, () -> setInstallmentCommand.execute(modelStub));
    }

    /**
     * Ensures that {@code CommandException} is thrown if installment to be deleted no longer exists in the
     * finance tracker.
     */
    @Test
    public void executeInverse_installmentToDeleteNonexistent_throwsCommandException() {
        Installment installmentToAdd = new InstallmentBuilder().build();
        SetInstallmentCommand setInstallmentCommand = new SetInstallmentCommand(installmentToAdd);

        String expectedMessage = String.format(SetInstallmentCommand.MESSAGE_SUCCESS,
                installmentToAdd);

        Model expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(),
                model.getFinanceTracker(), model.getAddressBook(), new UserPrefs(),
                model.getPlanner(), model.getCoursePlanner());
        expectedModel.addInstallment(installmentToAdd);

        assertCommandSuccess(setInstallmentCommand, model, expectedMessage, expectedModel);

        String inverseExpectedMessage = String.format(
                SetInstallmentCommand.MESSAGE_INVERSE_INSTALLMENT_NOT_FOUND, installmentToAdd);

        model.deleteInstallment(4);
        expectedModel.deleteInstallment(4);
        assertCommandInverseFailure(setInstallmentCommand, model, inverseExpectedMessage);
    }

    /**
     * Ensures that the {@code CommandResult} with the appropriate message is returned from a successful inverse
     * execution, that the added installment was deleted from the finance tracker.
     * */
    @Test
    public void executeInverse_success() {
        Installment installmentToAdd = new InstallmentBuilder().build();
        SetInstallmentCommand setInstallmentCommand = new SetInstallmentCommand(installmentToAdd);

        String expectedMessage = String.format(SetInstallmentCommand.MESSAGE_SUCCESS,
                installmentToAdd);

        Model expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(),
                model.getFinanceTracker(), model.getAddressBook(), new UserPrefs(),
                model.getPlanner(), model.getCoursePlanner());
        expectedModel.addInstallment(installmentToAdd);

        assertCommandSuccess(setInstallmentCommand, model, expectedMessage, expectedModel);

        String inverseExpectedMessage = String.format(
                SetInstallmentCommand.MESSAGE_INVERSE_SUCCESS_DELETE, installmentToAdd);

        assertCommandInverseSuccess(setInstallmentCommand, model, inverseExpectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Installment spotify = new InstallmentBuilder().withDescription("spotify").build();
        Installment netflix = new InstallmentBuilder().withDescription("netflix").build();
        SetInstallmentCommand addSpotifyCommand = new SetInstallmentCommand(spotify);
        SetInstallmentCommand addNetflixCommand = new SetInstallmentCommand(netflix);

        // same object -> returns true
        assertTrue(addSpotifyCommand.equals(addSpotifyCommand));

        // same values -> returns true
        SetInstallmentCommand addSpotifyCommandCopy = new SetInstallmentCommand(spotify);
        assertTrue(addSpotifyCommand.equals(addSpotifyCommandCopy));

        // different types -> returns false
        assertFalse(addSpotifyCommand.equals(1));

        // null -> returns false
        assertFalse(addSpotifyCommand.equals(null));

        // different purchase -> returns false
        assertFalse(addSpotifyCommand.equals(addNetflixCommand));
    }

    /**
     * A Model stub that contains a single installment.
     */
    private class ModelStubWithInstallment extends ModelStub {
        private final Installment installment;

        ModelStubWithInstallment(Installment installment) {
            requireNonNull(installment);
            this.installment = installment;
        }

        @Override
        public boolean hasInstallment(Installment installment) {
            requireNonNull(installment);
            return this.installment.isSameInstallment(installment);
        }
    }

    /**
     * A Model stub that always accept the installment being added.
     */
    private class ModelStubAcceptingInstallmentAdded extends ModelStub {
        final ArrayList<Installment> installmentsAdded = new ArrayList<>();

        @Override
        public boolean hasInstallment(Installment installment) {
            requireNonNull(installment);
            return installmentsAdded.stream().anyMatch(installment::isSameInstallment);
        }

        @Override
        public void addInstallment(Installment installment) {
            requireNonNull(installment);
            installmentsAdded.add(installment);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    private static class InstallmentStub extends Installment {
        public InstallmentStub() {
            super(new InstallmentDescription("Spotify subscription"), new InstallmentMoneyPaid("9.5"));
        }
    }
}
