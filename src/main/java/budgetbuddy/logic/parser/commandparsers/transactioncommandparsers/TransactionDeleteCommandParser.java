package budgetbuddy.logic.parser.commandparsers.transactioncommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.transactioncommands.TransactionDeleteCommand;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;

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
        try {
            Index index = CommandParserUtil.parseIndex(args);
            return new TransactionDeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TransactionDeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
