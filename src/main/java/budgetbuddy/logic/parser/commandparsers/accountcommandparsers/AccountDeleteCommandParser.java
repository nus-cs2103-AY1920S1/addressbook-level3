package budgetbuddy.logic.parser.commandparsers.accountcommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import budgetbuddy.logic.commands.accountcommands.AccountDeleteCommand;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.attributes.Name;

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
            Name name = CommandParserUtil.parseName(args);
            return new AccountDeleteCommand(name);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccountDeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
