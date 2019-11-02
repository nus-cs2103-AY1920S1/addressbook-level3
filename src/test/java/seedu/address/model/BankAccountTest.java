package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTransactions.ALICE;
import static seedu.address.testutil.TypicalTransactions.getTypicalUserState;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.exceptions.DuplicateTransactionException;
import seedu.address.testutil.BankOperationBuilder;

public class BankAccountTest {

    private final UserState userState = new UserState();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), userState.getBankAccount().getTransactionHistory());
        assertEquals(Collections.emptyList(), userState.getBankAccount().getBudgetHistory());
        assertEquals(Collections.emptyList(), userState.getLedger().getLedgerHistory());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> userState.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyBankAccount_replacesData() {
        UserState newData = getTypicalUserState();
        userState.resetData(newData);
        assertEquals(newData, userState);
    }

    @Test
    public void resetData_withDuplicateTransactions_throwsDuplicateTransactionException() {
        // Two transactions with the same identity fields
        BankAccountOperation editedAlice = new BankOperationBuilder(ALICE)
                .build();
        List<BankAccountOperation> newTransactions = Arrays.asList(ALICE, editedAlice);
        BankAccountStub newData = new BankAccountStub(newTransactions);
        UserState newUserState = new UserState(newData, new Ledger());

        assertThrows(DuplicateTransactionException.class, () -> userState.resetData(newUserState));
    }

    // TODO: implement test for budget during copying
    // @Test
    // public  void resetData_withDuplicateBudget_throwsDuplicateBudgetException() {
    //
    // }

    @Test
    public void hasTransaction_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> userState.hasTransaction(null));
    }

    @Test
    public void hasTransaction_transactionNotInBankAccount_returnsFalse() {
        assertFalse(userState.hasTransaction(ALICE));
    }

    @Test
    public void hasTransaction_transactionInBankAccount_returnsTrue() {
        userState.addOperation(ALICE);
        assertTrue(userState.hasTransaction(ALICE));
    }

    @Test
    public void hasTransaction_transactionWithSameIdentityFieldsInBankAccount_returnsTrue() {
        userState.addOperation(ALICE);
        BankAccountOperation editedAlice = new BankOperationBuilder(ALICE)
                .build();
        assertTrue(userState.hasTransaction(editedAlice));
    }

    @Test
    public void getTransactionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                userState.getBankAccount().getTransactionHistory().remove(0));
    }

    /**
     * A stub ReadOnlyBankAccount whose transactions list can violate interface constraints.
     */
    private static class BankAccountStub implements ReadOnlyBankAccount {
        private final ObservableList<BankAccountOperation> transactions = FXCollections.observableArrayList();
        private final ObservableList<Budget> budget = FXCollections.observableArrayList();
        private Amount balance = Amount.zero();

        BankAccountStub(Collection<BankAccountOperation> transactions) {
            this.transactions.setAll(transactions);
        }

        @Override
        public ObservableList<BankAccountOperation> getTransactionHistory() {
            return transactions;
        }

        @Override
        public ObservableList<BankAccountOperation> getSortedTransactionHistory(Comparator<BankAccountOperation> t) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Amount getBalance() {
            return balance;
        }

        @Override
        public ObservableList<Budget> getBudgetHistory() {
            return budget;
        }

        @Override
        public boolean hasTransaction(BankAccountOperation transaction) {
            return false;
        }

        @Override
        public boolean hasBudget(Budget budget) {
            return false;
        }
    }

}
