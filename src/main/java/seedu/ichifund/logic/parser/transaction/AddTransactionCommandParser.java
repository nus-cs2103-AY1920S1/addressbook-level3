package seedu.ichifund.logic.parser.transaction;

import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_TRANSACTION_TYPE;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.ichifund.logic.commands.transaction.AddTransactionCommand;
import seedu.ichifund.logic.parser.ArgumentMultimap;
import seedu.ichifund.logic.parser.ArgumentTokenizer;
import seedu.ichifund.logic.parser.Parser;
import seedu.ichifund.logic.parser.ParserUtil;
import seedu.ichifund.logic.parser.Prefix;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Category;
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
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_AMOUNT, PREFIX_CATEGORY, PREFIX_DAY,
                        PREFIX_MONTH, PREFIX_YEAR, PREFIX_TRANSACTION_TYPE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_AMOUNT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTransactionCommand.MESSAGE_USAGE));
        }

        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Amount amount = ParserUtil.parsePositiveAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Optional<Category> category = parseCategory(argMultimap);
        Optional<Day> day = parseDay(argMultimap);
        Optional<Month> month = parseMonth(argMultimap);
        Optional<Year> year = parseYear(argMultimap);
        Optional<TransactionType> transactionType = parseTransactionType(argMultimap);

        AddTransactionCommand.AddTransactionCommandBuilder builder =
                new AddTransactionCommand.AddTransactionCommandBuilder();
        builder.setDescription(description);
        builder.setAmount(amount);
        builder.setCategory(category);
        builder.setDay(day);
        builder.setMonth(month);
        builder.setYear(year);
        builder.setTransactionType(transactionType);

        return builder.build();
    }

    /**
     * Parses the {@code Month} field in an {@code ArgumentMultimap}, if present, into
     * a {@code Month} object, and wraps it in a {@code Optional} object
     * Else, an empty {@code Optional} object is returned.
     *
     * @throws ParseException if the string in {@code argMultimap} is invalid.
     */
    private Optional<Day> parseDay(ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> dayString = argMultimap.getValue(PREFIX_DAY);
        if (dayString.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(ParserUtil.parseDay(dayString.get()));
        }
    }

    /**
     * Parses the {@code Month} field in an {@code ArgumentMultimap}, if present, into
     * a {@code Month} object, and wraps it in a {@code Optional} object
     * Else, an empty {@code Optional} object is returned.
     *
     * @throws ParseException if the string in {@code argMultimap} is invalid.
     */
    private Optional<Month> parseMonth(ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> monthString = argMultimap.getValue(PREFIX_MONTH);
        if (monthString.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(ParserUtil.parseMonth(monthString.get()));
        }
    }

    /**
     * Parses the {@code Year} field in an {@code ArgumentMultimap}, if present, into
     * a {@code Year} object, and wraps it in a {@code Optional} object
     * Else, an empty {@code Optional} object is returned.
     *
     * @throws ParseException if the string in {@code argMultimap} is invalid.
     */
    private Optional<Year> parseYear(ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> yearString = argMultimap.getValue(PREFIX_YEAR);
        if (yearString.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(ParserUtil.parseYear(yearString.get()));
        }
    }

    /**
     * Parses the {@code Category} field in an {@code ArgumentMultimap}, if present, into
     * a {@code Category} object, and wraps it into an {@code Optional} object.
     * Else, an empty {@code Optional} object is returned.
     *
     * @throws ParseException if the string in {@code argMultimap} is invalid.
     */
    private Optional<Category> parseCategory(ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> categoryString = argMultimap.getValue(PREFIX_CATEGORY);
        if (categoryString.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(ParserUtil.parseCategory(categoryString.get()));
        }
    }

    /**
     * Parses the {@code TransactionType} field in an {@code ArgumentMultimap}, if present, into
     * a {@code TransactionType} object, and wraps it into an {@code Optional} object.
     * Else, an empty {@code Optional} object is returned.
     *
     * @throws ParseException if the string in {@code argMultimap} is invalid.
     */
    private Optional<TransactionType> parseTransactionType(ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> typeString = argMultimap.getValue(PREFIX_TRANSACTION_TYPE);
        if (typeString.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(ParserUtil.parseTransactionType(typeString.get()));
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
