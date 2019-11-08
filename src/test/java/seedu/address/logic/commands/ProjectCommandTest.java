package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyBankAccount;
import seedu.address.model.category.Category;
import seedu.address.model.projection.Projection;
import seedu.address.model.stubs.BankAccountStub;
import seedu.address.model.stubs.ModelStub;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.UniqueTransactionList;
import seedu.address.model.util.Date;
import seedu.address.testutil.BankOperationBuilder;


class ProjectCommandTest {
    @Test
    void constructor_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ProjectCommand(null));
    }

    @Test
    void executeProjectCommand_zeroTransactions_throwsCommandException() {
        List<BankAccountOperation> transactions = new ArrayList<>();
        ModelStubWithTransactions modelStub = new ModelStubWithTransactions(transactions);
        Date date = new Date("12122019");

        ProjectCommand projectCommand = new ProjectCommand(date);
        assertThrows(CommandException.class, () -> projectCommand.execute(modelStub));
    }

    @Test
    void executeProjectCommand_oneTransaction_throwsCommandException() {
        BankAccountOperation transaction = new BankOperationBuilder()
                .withCategories("Food")
                .withAmount("100")
                .withDate("10102019")
                .build();
        ModelStubWithTransaction modelStub = new ModelStubWithTransaction(transaction);
        Date date = new Date("12122019");

        ProjectCommand projectCommand = new ProjectCommand(date);
        assertThrows(CommandException.class, () -> projectCommand.execute(modelStub));
    }

    @Test
    void executeProjectCommand_noCategory_categoryGeneralByDefault() {
        Date date = new Date("12122019");

        ProjectCommand projectCommand = new ProjectCommand(date);
        assertEquals(projectCommand.getCategory(), Category.GENERAL);
    }

    /**
     * A Model stub that contains a single transaction.
     */
    private static class ModelStubWithTransaction extends ModelStub {
        private final BankAccountOperation transaction;

        ModelStubWithTransaction(BankAccountOperation transaction) {
            requireNonNull(transaction);
            this.transaction = transaction;
        }

        @Override
        public boolean has(BankAccountOperation transaction) {
            requireNonNull(transaction);
            return this.transaction.isSameTransaction(transaction);
        }

        @Override
        public ReadOnlyBankAccount getBankAccount() {
            ReadOnlyBankAccountStub stub = new ReadOnlyBankAccountStub();
            stub.setTransactions(List.of(transaction));
            return stub;
        }
    }

    /**
     * A Model stub that contains transactions.
     */
    private static class ModelStubWithTransactions extends ModelStub {
        private ReadOnlyBankAccountStub readOnlyBankAccountStub;

        ModelStubWithTransactions(List<BankAccountOperation> transactions) {
            this.readOnlyBankAccountStub = new ReadOnlyBankAccountStub();
            readOnlyBankAccountStub.setTransactions(transactions);
        }

        @Override
        public ReadOnlyBankAccount getBankAccount() {
            return readOnlyBankAccountStub;
        }

        @Override
        public boolean has(Projection projection) {
            return false;
        }
    }

    private static class ReadOnlyBankAccountStub extends BankAccountStub {
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
