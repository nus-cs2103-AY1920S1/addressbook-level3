package seedu.address.stubs;

import java.util.ArrayList;

import seedu.address.transaction.model.Transaction;

public class TransactionModelStubAcceptingTransactionAdded extends TransactionModelStub {
    final ArrayList<Transaction> transactionsAdded;

    public TransactionModelStubAcceptingTransactionAdded() {
        transactionsAdded = new ArrayList<>();
    }

    public TransactionModelStubAcceptingTransactionAdded(ArrayList<Transaction> arr) {
        this.transactionsAdded = arr;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactionsAdded.add(transaction);
    }

    public ArrayList<Transaction> getTransactionsAdded() {
        return transactionsAdded;
    }

    @Override
    public void deleteTransaction(int index) {
        transactionsAdded.remove(index - 1);
    }

}
