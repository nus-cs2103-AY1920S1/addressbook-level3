package seedu.address.stubs;

import java.util.function.Predicate;

import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.util.TransactionList;

public class TransactionModelStub implements Model {
    @Override
    public void addTransaction(Transaction transaction) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Transaction findTransactionInFilteredListByIndex(int index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteTransaction(int index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public TransactionList getTransactionList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTransaction(Transaction transactionToEdit, Transaction editedTransaction) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasTransaction(Transaction editedTransaction) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateIndexes() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortByDate() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortByName() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortByAmount() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortReset() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public TransactionList getFilteredList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updatePredicate(Predicate<Transaction> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void resetPredicate() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteAllTransactionOfPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasTransactionWithName(String name) {
        throw new AssertionError("This method should not be called.");
    }
}
