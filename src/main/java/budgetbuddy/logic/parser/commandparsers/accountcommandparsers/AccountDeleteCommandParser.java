package budgetbuddy.logic.parser.commandparsers.accountcommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.accountcommands.AccountDeleteCommand;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LoanDeleteCommand object.
 */
public class AccountDeleteCommandParser implements CommandParser<AccountDeleteCommand> {
    @Override
    public String name() {
        return AccountDeleteCommand.COMMAND_WORD;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AccountDeleteCommand parse(String args) throws ParseException {
        try {
            Index index = CommandParserUtil.parseIndex(args);
            return new AccountDeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccountDeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
