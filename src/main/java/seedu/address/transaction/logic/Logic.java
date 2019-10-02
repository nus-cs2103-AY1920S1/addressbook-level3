package seedu.address.transaction.logic;

import seedu.address.transaction.commands.CommandResult;
import seedu.address.transaction.util.TransactionList;

public interface Logic {

    CommandResult execute(String commandText) throws Exception;

    TransactionList getTransactionList() throws Exception;
}
