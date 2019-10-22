package budgetbuddy.logic.parser.commandparsers.accountcommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import budgetbuddy.logic.commands.FindCommand;
import budgetbuddy.logic.commands.accountcommands.AccountFindCommand;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.account.NameHasKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class AccountFindCommandParser implements CommandParser<AccountFindCommand> {
    @Override
    public String name() {
        return FindCommand.COMMAND_WORD;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AccountFindCommand
     * and returns a AccountFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AccountFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccountFindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new AccountFindCommand(new NameHasKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}

