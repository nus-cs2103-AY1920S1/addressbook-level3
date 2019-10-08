package seedu.address.transaction.logic;

import seedu.address.transaction.commands.CommandResult;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.util.TransactionList;

public interface Logic {

    CommandResult execute(String commandText) throws Exception;

    void writeIntoTransactionFile() throws Exception;

    void setTransaction(Transaction transaction, Transaction newTransaction) throws Exception;

    TransactionList getTransactionList();
}
