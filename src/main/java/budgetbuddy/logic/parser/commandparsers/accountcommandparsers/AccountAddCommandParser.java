package budgetbuddy.logic.parser.commandparsers.accountcommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;
import java.util.stream.Stream;

import budgetbuddy.logic.commands.accountcommands.AccountAddCommand;
import budgetbuddy.logic.parser.ArgumentMultimap;
import budgetbuddy.logic.parser.ArgumentTokenizer;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.Prefix;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Description;
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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESCRIPTION);

        checkArgumentMultimap(argMultimap);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccountAddCommand.MESSAGE_USAGE));
        }

        Name name = CommandParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        Optional<String> optionalDescription = argMultimap.getValue(PREFIX_DESCRIPTION);
        Description description = optionalDescription.isPresent()
                ? CommandParserUtil.parseDescription(optionalDescription.get())
                : new Description("");

        TransactionList transactionList = new TransactionList();

        Account account = new Account(name, description, transactionList);

        return new AccountAddCommand(account);
    }

    /**
     * Checks if the given {@code ArgumentMultimap} is valid for parsing into a {@code AccountAddCommand}.
     * @throws ParseException If the {@code argMultimap} is invalid.
     */
    private void checkArgumentMultimap(ArgumentMultimap argMultimap) throws ParseException {

        if (argMultimap.getValueCount(PREFIX_NAME) > 1
                || argMultimap.getValueCount(PREFIX_DESCRIPTION) > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccountAddCommand.MESSAGE_USAGE));
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
