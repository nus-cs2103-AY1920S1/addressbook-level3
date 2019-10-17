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
import seedu.jarvis.model.financetracker.purchase.Purchase;
import seedu.jarvis.testutil.ModelStub;
import seedu.jarvis.testutil.finance.PurchaseBuilder;

public class SetPaidCommandTest {

    /**
     * Verifies that checking {@code PaidCommand} for the availability of inverse execution returns true.
     */
    @BeforeEach
    public void hasInverseExecution() {
        Purchase validPurchase = new PurchaseBuilder().build();
        SetPaidCommand paidCommand = new SetPaidCommand(validPurchase);
        assertTrue(paidCommand.hasInverseExecution());
    }

    @Test
    public void constructor_nullPurchase_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SetPaidCommand(null));
    }

    @Test
    public void execute_purchaseAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPurchaseAdded modelStub = new ModelStubAcceptingPurchaseAdded();
        Purchase validPurchase = new PurchaseBuilder().build();

        CommandResult commandResult = new SetPaidCommand(validPurchase).execute(modelStub);

        assertEquals(String.format(SetPaidCommand.MESSAGE_SUCCESS, validPurchase), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPurchase), modelStub.purchasesAdded);
    }

    @Test
    public void equals() {
        Purchase movie = new PurchaseBuilder().withDescription("movie ticket").build();
        Purchase karaoke = new PurchaseBuilder().withDescription("karaoke night").build();
        SetPaidCommand addMovieCommand = new SetPaidCommand(movie);
        SetPaidCommand addKaraokeCommand = new SetPaidCommand(karaoke);

        // same object -> returns true
        assertTrue(addMovieCommand.equals(addMovieCommand));

        // same values -> returns true
        SetPaidCommand addMovieCommandCopy = new SetPaidCommand(movie);
        assertTrue(addMovieCommand.equals(addMovieCommandCopy));

        // different types -> returns false
        assertFalse(addMovieCommand.equals(1));

        // null -> returns false
        assertFalse(addMovieCommand.equals(null));

        // different purchase -> returns false
        assertFalse(addMovieCommand.equals(addKaraokeCommand));
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPurchase extends ModelStub {
        private final Purchase purchase;

        ModelStubWithPurchase(Purchase purchase) {
            requireNonNull(purchase);
            this.purchase = purchase;
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPurchaseAdded extends ModelStub {
        final ArrayList<Purchase> purchasesAdded = new ArrayList<>();

        @Override
        public void addPurchase(Purchase purchase) {
            requireNonNull(purchase);
            purchasesAdded.add(purchase);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
