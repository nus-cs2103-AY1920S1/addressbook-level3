package budgetbuddy.logic.parser.commandparsers.accountcommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static java.util.Objects.requireNonNull;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.accountcommands.AccountSwitchCommand;
import budgetbuddy.logic.parser.ArgumentMultimap;
import budgetbuddy.logic.parser.ArgumentTokenizer;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;

/**
 * Parses the <code>switch</code> command.
 */
public class AccountSwitchCommandParser implements CommandParser<AccountSwitchCommand> {
    @Override
    public String name() {
        return AccountSwitchCommand.COMMAND_WORD;
    }

    @Override
    public AccountSwitchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args);

        if (argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccountSwitchCommand.MESSAGE_USAGE));
        }
        Index accountIndex = CommandParserUtil.parseIndex(argMultiMap.getPreamble());
        return new AccountSwitchCommand(accountIndex);
    }
}
