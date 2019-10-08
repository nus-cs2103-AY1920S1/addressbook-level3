package seedu.address.transaction.logic;

import seedu.address.transaction.commands.SortAmountCommand;
import seedu.address.transaction.commands.SortCommand;
import seedu.address.transaction.commands.SortDateCommand;
import seedu.address.transaction.commands.SortNameCommand;
import seedu.address.transaction.commands.SortResetCommand;
import seedu.address.transaction.logic.exception.NoSuchSortException;
import seedu.address.transaction.ui.TransactionMessages;

public class SortCommandParser {

    public static SortCommand parse(String args) throws NoSuchSortException{
       String[] argsArr = args.split(" ");
       if (argsArr[1].equals("date")) {
           return new SortDateCommand();
       } else if (argsArr[1].equals("name")) {
           return new SortNameCommand();
       } else if (argsArr[1].equals("amount")) {
           return new SortAmountCommand();
       } else if (argsArr[1].equals("reset")) {
           return new SortResetCommand();
       } else {
           throw new NoSuchSortException(TransactionMessages.NO_SUCH_SORT_COMMAND);
       }
    }
}
