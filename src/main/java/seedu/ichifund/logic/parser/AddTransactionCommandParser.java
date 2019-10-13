package seedu.ichifund.logic.parser;

import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_TRANSACTION_TYPE;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.stream.Stream;

import seedu.ichifund.logic.commands.AddTransactionCommand;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.model.Amount;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.Transaction;
import seedu.ichifund.model.transaction.TransactionType;


/**
 * Parses input arguments and creates a new AddTransactionCommand object.
 */
public class AddTransactionCommandParser implements Parser<AddTransactionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddTransactionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTransactionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_DAY,
                        PREFIX_MONTH, PREFIX_YEAR, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argMultimap, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_DAY,
                PREFIX_MONTH, PREFIX_YEAR, PREFIX_CATEGORY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTransactionCommand.MESSAGE_USAGE));
        }

        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Category category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
        Day day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).get());
        Month month = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_MONTH).get());
        Year year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
        TransactionType transactionType = ParserUtil.parseTransactionType(argMultimap
                .getValue(PREFIX_TRANSACTION_TYPE).get());

        Date date = constructDate(day, month, year);

        Transaction transaction = new Transaction(description, amount, category, date, transactionType);

        return new AddTransactionCommand(transaction);
    }

    /**
     * Returns a {@code Date} object from the {@code day}, {@code month} and {@code year}.
     *
     * @param day The {@code Day} of the year to be returned.
     * @param month The {@code Month} of the year to be returned.
     * @param year The {@code Year} of the year to be returned.
     * @return A {@code Date} object composed of {@code day}, {@code month} and {@code year}
     * @throws ParseException If day does not match month and year.
     */
    private static Date constructDate(Day day, Month month, Year year) throws ParseException {
        if (Date.isValidDate(day, month, year)) {
            return new Date(day, month, year);
        } else {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
