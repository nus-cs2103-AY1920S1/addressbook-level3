package budgetbuddy.logic.parser.commandparsers.accountcommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import budgetbuddy.logic.commands.accountcommands.AccountAddCommand;
import budgetbuddy.logic.parser.ArgumentMultimap;
import budgetbuddy.logic.parser.ArgumentTokenizer;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.Prefix;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.transaction.TransactionList;

/**
 * Parses input arguments and creates a new AccountAddCommand object.
 */
public class AccountAddCommandParser implements CommandParser<AccountAddCommand> {
    @Override
    public String name() {
        return AccountAddCommand.COMMAND_WORD;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AccountAddCommand
     * and returns an AccountAddCommand object for execution.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public AccountAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccountAddCommand.MESSAGE_USAGE));
        }

        Name name = CommandParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        TransactionList transactionList = new TransactionList();

        Account account = new Account(name, transactionList);

        return new AccountAddCommand(account);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
