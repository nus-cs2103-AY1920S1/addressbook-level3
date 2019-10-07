package seedu.address.transaction.util;

import java.util.ArrayList;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.model.exception.NoSuchIndexException;
import seedu.address.transaction.ui.TransactionMessages;

public class TransactionList {
    private ArrayList<Transaction> tList;

    public TransactionList() {
         tList = new ArrayList<>();
    }

    public TransactionList(ArrayList<Transaction> tList) {
        this.tList = tList;
    }


    public Transaction get(int index) throws NoSuchIndexException {
        if (index >= tList.size()) {
            throw new NoSuchIndexException(TransactionMessages.NO_SUCH_INDEX_TRANSACTION);
        } else {
            return tList.get(index);
        }
    }

    public void add(Transaction transaction) {
        tList.add(transaction);
    }

    public void delete(int index) {
        tList.remove(index);
    }

    public int size() {
        return tList.size();
    }

    public void set(int i, Transaction transaction) {
        tList.set(i, transaction);
    }
}
