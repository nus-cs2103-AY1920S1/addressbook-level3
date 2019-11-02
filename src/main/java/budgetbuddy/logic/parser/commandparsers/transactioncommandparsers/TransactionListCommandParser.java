package budgetbuddy.logic.parser.commandparsers.transactioncommandparsers;

import budgetbuddy.logic.commands.transactioncommands.TransactionListCommand;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.exceptions.ParseException;

/**
 * parses the <code>transaction list</code> command.
 */
public class TransactionListCommandParser implements CommandParser {
    @Override
    public String name() {
        return TransactionListCommand.COMMAND_WORD;
    }

    /**
     * Returns a TransactionListCommand object for execution.
     */
    @Override
    public TransactionListCommand parse(String args) throws ParseException {
        return new TransactionListCommand();
    }
}
