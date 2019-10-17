package seedu.jarvis.logic.commands.finance;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.model.address.AddressBook;
import seedu.jarvis.model.address.ReadOnlyAddressBook;
import seedu.jarvis.model.financetracker.installment.Installment;
import seedu.jarvis.model.financetracker.installment.InstallmentDescription;
import seedu.jarvis.testutil.ModelStub;
import seedu.jarvis.testutil.finance.InstallmentBuilder;

public class SetInstallmentCommandTest {

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
    public void execute_installmentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingInstallmentAdded modelStub = new ModelStubAcceptingInstallmentAdded();
        Installment validInstallment = new InstallmentBuilder().build();

        CommandResult commandResult = new SetInstallmentCommand(validInstallment).execute(modelStub);

        assertEquals(String.format(SetInstallmentCommand.MESSAGE_SUCCESS, validInstallment),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validInstallment), modelStub.installmentsAdded);
    }

    @Test
    public void equals() {
        Installment spotify = new InstallmentBuilder().withDescription(new InstallmentDescription("spotify")).build();
        Installment netflix = new InstallmentBuilder().withDescription(new InstallmentDescription("netflix")).build();
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
     * A Model stub that contains a single person.
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
     * A Model stub that always accept the person being added.
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
}
