package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;

import seedu.address.model.BankAccount;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyBankAccount;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.model.transaction.Transaction;

public class AddCommandTest {

    // TODO: Refactor into InCommandTest
    /*
    @Test
    public void constructor_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }
     */

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getBankAccountFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBankAccountFilePath(Path bankAccountFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void handleOperation(BankAccountOperation transaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void handleOperation(LedgerOperation operation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBudget(Budget budget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBankAccount(ReadOnlyBankAccount newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyBankAccount getBankAccount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoBankAccount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoBankAccount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoBankAccount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoBankAccount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitBankAccount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTransactions(List<BankAccountOperation> transactionHistory) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTransaction(BankAccountOperation transaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTransaction(BankAccountOperation target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTransaction(BankAccountOperation target, BankAccountOperation editedTransaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public FilteredList<BankAccountOperation> getFilteredTransactionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTransactionList(Predicate<BankAccountOperation> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Budget> getFilteredBudgetList() {
            return null;
        }
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
        public boolean hasTransaction(BankAccountOperation transaction) {
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
        public boolean hasTransaction(BankAccountOperation transaction) {
            requireNonNull(transaction);
            return transactionsAdded.stream().anyMatch(transaction::isSameTransaction);
        }

        @Override
        public void handleOperation(LedgerOperation operation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBudget(Budget budget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyBankAccount getBankAccount() {
            return new BankAccount();
        }
    }

}
