package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyBankAccount;
import seedu.address.model.stubs.BankAccountStub;
import seedu.address.model.stubs.ModelStub;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.UniqueTransactionList;
import seedu.address.model.util.Date;
import seedu.address.testutil.BankOperationBuilder;



public class ProjectCommandTest {
    @Test
    public void constructor_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ProjectCommand(null));
    }

    @Test
    public void executeProjectCommand_oneTransaction_throwsCommandException() throws Exception {
        BankAccountOperation transaction = new BankOperationBuilder()
                .withCategories("Food")
                .withAmount("100")
                .withDate("10102019")
                .build();
        List<BankAccountOperation> transactions = new ArrayList<>();
        transactions.add(transaction);
        ModelStubWithTransactions modelStub = new ModelStubWithTransactions(transactions);
        Date date = new Date("12122019");

        ProjectCommand projectCommand = new ProjectCommand(date);
        assertThrows(CommandException.class, () -> projectCommand.execute(modelStub));
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
     * A Model stub that contains 10 transactions.
     */
    private class ModelStubWithTransactions extends ModelStub {
        private ReadOnlyBankAccountStub readOnlyBankAccountStub;

        ModelStubWithTransactions(List<BankAccountOperation> transactions) {
            this.readOnlyBankAccountStub = new ReadOnlyBankAccountStub();
            readOnlyBankAccountStub.setTransactions(transactions);
        }

        @Override
        public ReadOnlyBankAccount getBankAccount() {
            return readOnlyBankAccountStub;
        }
    }

    private class ReadOnlyBankAccountStub extends BankAccountStub {
        private UniqueTransactionList transactions;

        ReadOnlyBankAccountStub() {
            this.transactions = new UniqueTransactionList();
        }

        void setTransactions(List<BankAccountOperation> transactions) {
            this.transactions.setTransactions(transactions);
        }

        @Override
        public ObservableList<BankAccountOperation> getTransactionHistory() {
            return transactions.asUnmodifiableObservableList();
        }

        @Override
        public ObservableList<BankAccountOperation> getSortedTransactionHistory(Comparator<BankAccountOperation> t) {
            return getTransactionHistory().sorted(t);
        }
    }
}
