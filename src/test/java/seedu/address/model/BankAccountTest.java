package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTransactions.ALICE;
import static seedu.address.testutil.TypicalTransactions.BENSON;
import static seedu.address.testutil.TypicalTransactions.getTypicalUserState;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.projection.Projection;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.testutil.BankOperationBuilder;

public class BankAccountTest {

    private final BankAccount bankAccount = new BankAccount();

    @Test
    public void constructor() {
        assertEquals(Amount.zero(), bankAccount.getBalance());
        assertEquals(Collections.emptyList(), bankAccount.getTransactionHistory());
        assertEquals(Collections.emptyList(), bankAccount.getBudgetHistory());
        assertEquals(Collections.emptyList(), bankAccount.getProjectionHistory());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bankAccount.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyBankAccount_replacesData() {
        ReadOnlyBankAccount newData = getTypicalUserState().getBankAccount();
        bankAccount.resetData(newData);
        assertEquals(newData, bankAccount);
    }

    // TODO: implement test for budget during copying
    // @Test
    // public  void resetData_withDuplicateBudget_throwsDuplicateBudgetException() {
    //
    // }

    @Test
    public void has_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bankAccount.has((BankAccountOperation) null));
        assertThrows(NullPointerException.class, () -> bankAccount.has((Budget) null));
        assertThrows(NullPointerException.class, () -> bankAccount.has((Projection) null));
    }

    @Test
    public void hasTransaction_transactionNotInBankAccount_returnsFalse() {
        assertFalse(bankAccount.has(ALICE));
    }

    @Test
    public void hasTransaction_transactionInBankAccount_returnsTrue() {
        bankAccount.add(ALICE);
        assertTrue(bankAccount.has(ALICE));
    }

    @Test
    public void hasTransaction_transactionWithSameIdentityFieldsInBankAccount_returnsTrue() {
        bankAccount.add(ALICE);
        BankAccountOperation editedAlice = new BankOperationBuilder(ALICE)
            .build();
        assertTrue(bankAccount.has(editedAlice));
    }

    @Test
    public void getTransactionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
            bankAccount.getTransactionHistory().remove(0));
    }

    @Test
    public void addTransaction_handleBalanceInTransaction() {
        // test if inTransaction adds to balance
        String transactionAmount = "100";
        BankAccountOperation testTransactionIn = new BankOperationBuilder(ALICE).withAmount(transactionAmount).build();
        // inTransaction to add to balance
        bankAccount.add(testTransactionIn);
        assertEquals(bankAccount.getBalance(), testTransactionIn.getAmount());

        // test if two max in transactions can still update amount;
        bankAccount.setBalance(Amount.zero());
        String transactionAmountMax = "999999";
        BankAccountOperation testTransactionInMax1 = new BankOperationBuilder(ALICE)
            .withAmount(transactionAmountMax).build();
        BankAccountOperation testTransactionInMax2 = new BankOperationBuilder(BENSON)
            .withAmount(transactionAmountMax).build();
        bankAccount.add(testTransactionInMax1);
        bankAccount.add(testTransactionInMax2);
        assertEquals(bankAccount.getBalance(), Amount.of(2 * Integer.parseInt(transactionAmountMax)));
    }

    @Test
    public void addTransaction_handleBalanceOutTransaction() {
        // test if outTransaction subtracts from balance
        String transactionAmount = "-100";
        BankAccountOperation testTransactionIn = new BankOperationBuilder(ALICE).withAmount(transactionAmount).build();
        // outTransaction to subtract from balance
        bankAccount.add(testTransactionIn);
        assertEquals(bankAccount.getBalance(), testTransactionIn.getAmount());

        // test if two max out transactions can still update amount;
        bankAccount.setBalance(Amount.zero());
        String transactionAmountMax = "-999999";
        BankAccountOperation testTransactionInMax1 = new BankOperationBuilder(ALICE)
            .withAmount(transactionAmountMax).build();
        BankAccountOperation testTransactionInMax2 = new BankOperationBuilder(BENSON)
            .withAmount(transactionAmountMax).build();
        bankAccount.add(testTransactionInMax1);
        bankAccount.add(testTransactionInMax2);
        assertEquals(bankAccount.getBalance(), Amount.of(2 * Integer.parseInt(transactionAmountMax)));
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
        public ObservableList<Projection> getProjectionHistory() {
            return null;
        }

        @Override
        public boolean has(BankAccountOperation transaction) {
            return false;
        }

        @Override
        public boolean has(Budget budget) {
            return false;
        }

        @Override
        public boolean has(Projection projection) {
            return false;
        }
    }

}
