package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.sun.javafx.collections.ObservableListWrapper;

import javafx.collections.ObservableList;

import seedu.address.model.BankAccount;
import seedu.address.model.ReadOnlyBankAccount;
import seedu.address.model.projection.Projection;
import seedu.address.model.stubs.ModelStub;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.model.transaction.Transaction;
import seedu.address.testutil.BankOperationBuilder;

public class OutCommandTest {

    @Test
    public void constructor_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new OutCommand(null));
    }

    @Test
    public void executeTransactionAcceptedByModeladdSuccessful() throws Exception {
        ModelStubAcceptingTransactionAdded modelStub = new ModelStubAcceptingTransactionAdded();
        BankAccountOperation validTransaction = new BankOperationBuilder().build();

        OutCommand outCommand = new OutCommand(validTransaction);
        CommandResult commandResult = outCommand.execute(modelStub);

        assertEquals(String.format(OutCommand.MESSAGE_SUCCESS, validTransaction), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTransaction), modelStub.transactionsAdded);
    }

    @Test
    public void equals() {
        BankAccountOperation firstTransaction = new BankOperationBuilder()
            .withDescription("Milk")
            .withCategories("Food")
            .withAmount("100")
            .withDate("10102019")
            .build();
        BankAccountOperation secondTransaction = new BankOperationBuilder()
            .withDescription("Coke")
            .withCategories("Drinks")
            .withAmount("80")
            .withDate("10102019")
            .build();
        OutCommand addFirstCommand = new OutCommand(firstTransaction);
        OutCommand addSecondCommand = new OutCommand(secondTransaction);

        // same object -> returns true
        assertTrue(addFirstCommand.equals(addFirstCommand));

        // same values -> returns true
        OutCommand addFirstCommandCopy = new OutCommand(firstTransaction);
        assertTrue(addFirstCommand.equals(addFirstCommandCopy));

        // different types -> returns false
        assertFalse(addFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(addFirstCommand.equals(addSecondCommand));
    }


    /**
     * A Model stub that contains a single transaction.
     */
    private class ModelStubWithTransaction extends ModelStub {
        private final Transaction transaction;

        ModelStubWithTransaction(Transaction transaction) {
            requireNonNull(transaction);
            this.transaction = transaction;
        }

        @Override
        public boolean has(BankAccountOperation transaction) {
            requireNonNull(transaction);
            return this.transaction.isSameTransaction(transaction);
        }
    }

    /**
     * A Model stub that always accept the transaction being added.
     */
    private class ModelStubAcceptingTransactionAdded extends ModelStub {
        final ArrayList<BankAccountOperation> transactionsAdded = new ArrayList<>();

        @Override
        public boolean has(BankAccountOperation transaction) {
            requireNonNull(transaction);
            return transactionsAdded.stream().anyMatch(transaction::isSameTransaction);
        }

        @Override
        public void add(LedgerOperation operation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void add(BankAccountOperation transaction) {
            addTransaction(transaction);
        }

        private void addTransaction(BankAccountOperation transaction) {
            requireNonNull(transaction);
            transactionsAdded.add(transaction);
        }

        @Override
        public void commitUserState() {
            return;
        }

        @Override
        public ReadOnlyBankAccount getBankAccount() {
            return new BankAccount();
        }

        @Override
        public ObservableList<Projection> getFilteredProjectionsList() {
            return new ObservableListWrapper<>(new ArrayList<>());
        }
    }
}
