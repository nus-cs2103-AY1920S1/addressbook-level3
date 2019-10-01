package seedu.address.transaction.model;

import seedu.address.transaction.model.exception.NoSuchIndexException;
import seedu.address.transaction.storage.StorageManager;
import seedu.address.transaction.util.TransactionList;

public class ModelManager implements Model {
    private TransactionList transactionList;
    private StorageManager storage;

    public ModelManager(TransactionList transactionList) {
        this.transactionList = transactionList;
    }

    public ModelManager(StorageManager storage) {
        this.storage = storage;
        try {
            this.transactionList = storage.getTransactionList();
        } catch (Exception e) {
            this.transactionList = new TransactionList();
        }
    }

    @Override
    public TransactionList getTransactionList() {
        return this.transactionList;
    }

    @Override
    public void addTransaction(Transaction trans) {
        transactionList.add(trans);
    }

    @Override
    public Transaction findTransactionByIndex(int index) throws NoSuchIndexException {
        Transaction transaction = transactionList.get(index);
        return transaction;
    }

    @Override
    public void deleteTransaction(int index) {
        transactionList.delete(index - 1);
    }

    @Override
    public void writeInTransactionFile() throws Exception{
        storage.writeFile(transactionList);
    }


}
