package seedu.address.logic.parser.commandparsers.transactioncommandparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACCOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.transaction.TransactionAddCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.CommandParser;
import seedu.address.logic.parser.CommandParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.account.Account;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Description;
import seedu.address.model.attributes.Direction;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Transaction;

/**
 * Parses input and
 */
public class TransactionAddCommandParser implements CommandParser<TransactionAddCommand> {
    @Override
    public String name() {
        return TransactionAddCommand.COMMAND_WORD;
    }


    /**
     * Parses the given {@code String} of arguments in the context of the TransactionAddCommand
     * and returns an TransactionAddCommand object for execution.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    @Override
    public TransactionAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_ACCOUNT,
                        PREFIX_CATEGORY, PREFIX_DATE);

        String directionString = argMultimap.getPreamble().toUpperCase();
        if (!arePrefixesPresent(argMultimap, PREFIX_AMOUNT)
                || !(directionString.equals(Direction.IN.toString())
                || directionString.equals(Direction.OUT.toString()))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    TransactionAddCommand.MESSAGE_USAGE));
        }
        Direction direction = Direction.valueOf(directionString.toUpperCase());

        Amount amount = CommandParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());

        Optional<String> optionalDescription = argMultimap.getValue(PREFIX_DESCRIPTION);
        Description description = optionalDescription.isPresent()
                ? CommandParserUtil.parseDescription(optionalDescription.get())
                : new Description("");

        Optional<String> optionalAccount = argMultimap.getValue(PREFIX_ACCOUNT);
        Account account = optionalAccount.isPresent()
                ? CommandParserUtil.parseAccount(optionalAccount.get())
                : Account.getDefaultAccount();

        Optional<String> optionalCategory = argMultimap.getValue(PREFIX_CATEGORY);
        Category category = optionalCategory.isPresent()
                ? CommandParserUtil.parseCategory(optionalCategory.get())
                : new Category("");

        /**
         * Return current date when optionalDate is not present
         */
        Optional<String> optionalDate = argMultimap.getValue(PREFIX_DATE);
        Date date = optionalDate.isPresent()
                ? CommandParserUtil.parseDate(optionalDate.get())
                : new Date();

        Transaction transaction = new Transaction(date, amount, direction, description, account, category);

        return new TransactionAddCommand(transaction);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
