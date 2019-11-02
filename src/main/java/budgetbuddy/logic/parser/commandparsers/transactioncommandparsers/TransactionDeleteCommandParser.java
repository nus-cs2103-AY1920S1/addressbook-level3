package budgetbuddy.logic.parser.commandparsers.transactioncommandparsers;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.transactioncommands.TransactionDeleteCommand;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input and creates a new TransactionDeleteCommand
 */
public class TransactionDeleteCommandParser implements CommandParser<TransactionDeleteCommand> {
    @Override
    public String name() {
        return TransactionDeleteCommand.COMMAND_WORD;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the TransactionDeleteCommand
     * and returns a TransactionDeleteCommand object for execution.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    @Override
    public TransactionDeleteCommand parse(String args) throws ParseException {
        Index index = CommandParserUtil.parseIndex(args);
        return new TransactionDeleteCommand(index);
    }
}
