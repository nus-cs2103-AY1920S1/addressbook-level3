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
import seedu.jarvis.testutil.ModelStub;
import seedu.jarvis.model.address.AddressBook;
import seedu.jarvis.model.address.ReadOnlyAddressBook;
import seedu.jarvis.model.financetracker.Purchase;
import seedu.jarvis.testutil.PurchaseBuilder;

public class PaidCommandTest {

    /**
     * Verifies that checking {@code PaidCommand} for the availability of inverse execution returns true.
     */
    @BeforeEach
    public void hasInverseExecution() {
        Purchase validPurchase = new PurchaseBuilder().build();
        PaidCommand paidCommand = new PaidCommand(validPurchase);
        assertTrue(paidCommand.hasInverseExecution());
    }

    @Test
    public void constructor_nullPurchase_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PaidCommand(null));
    }

    @Test
    public void execute_purchaseAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPurchaseAdded modelStub = new ModelStubAcceptingPurchaseAdded();
        Purchase validPurchase = new PurchaseBuilder().build();

        CommandResult commandResult = new PaidCommand(validPurchase).execute(modelStub);

        assertEquals(String.format(PaidCommand.MESSAGE_SUCCESS, validPurchase), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPurchase), modelStub.purchasesAdded);
    }

    @Test
    public void equals() {
        Purchase movie = new PurchaseBuilder().withDescription("movie ticket").build();
        Purchase karaoke = new PurchaseBuilder().withDescription("karaoke night").build();
        PaidCommand addMovieCommand = new PaidCommand(movie);
        PaidCommand addKaraokeCommand = new PaidCommand(karaoke);

        // same object -> returns true
        assertTrue(addMovieCommand.equals(addMovieCommand));

        // same values -> returns true
        PaidCommand addMovieCommandCopy = new PaidCommand(movie);
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

        @Override
        public boolean hasPurchase(Purchase purchase) {
            requireNonNull(purchase);
            return this.purchase.isSamePurchase(purchase);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPurchaseAdded extends ModelStub {
        final ArrayList<Purchase> purchasesAdded = new ArrayList<>();

        @Override
        public boolean hasPurchase(Purchase purchase) {
            requireNonNull(purchase);
            return purchasesAdded.stream().anyMatch(purchase::isSamePurchase);
        }

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
