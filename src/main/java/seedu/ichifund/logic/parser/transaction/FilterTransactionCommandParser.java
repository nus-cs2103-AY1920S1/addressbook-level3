package seedu.ichifund.logic.parser.transaction;

import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_TRANSACTION_TYPE;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.ichifund.logic.commands.transaction.FilterTransactionCommand;
import seedu.ichifund.logic.parser.ArgumentMultimap;
import seedu.ichifund.logic.parser.ArgumentTokenizer;
import seedu.ichifund.logic.parser.Parser;
import seedu.ichifund.logic.parser.ParserUtil;
import seedu.ichifund.logic.parser.Prefix;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.TransactionType;

/**
 * Parses input arguments and creates a new FilterTransactionCommand object.
 */
public class FilterTransactionCommandParser implements Parser<FilterTransactionCommand> {
    @Override
    public FilterTransactionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MONTH, PREFIX_YEAR, PREFIX_CATEGORY, PREFIX_TRANSACTION_TYPE);

        if (!areAnyPrefixesPresent(argMultimap, PREFIX_MONTH, PREFIX_YEAR, PREFIX_CATEGORY, PREFIX_TRANSACTION_TYPE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FilterTransactionCommand.MESSAGE_USAGE));
        }

        Optional<Month> month = parseMonth(argMultimap);
        Optional<Year> year = parseYear(argMultimap);
        Optional<Category> category = parseCategory(argMultimap);
        Optional<TransactionType> transactionType = parseType(argMultimap);

        return buildCommand(month, year, category, transactionType);
    }

    private FilterTransactionCommand buildCommand(Optional<Month> month, Optional<Year> year, Optional<Category> category,
                                                  Optional<TransactionType> transactionType) {
        FilterTransactionCommand.FilterTransactionCommandBuilder builder =
                new FilterTransactionCommand.FilterTransactionCommandBuilder();

        builder.setMonth(month);
        builder.setYear(year);
        builder.setCategory(category);
        builder.setType(transactionType);

        return builder.build();
    }

    private Optional<Month> parseMonth(ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> monthString = argMultimap.getValue(PREFIX_MONTH);
        if (monthString.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(ParserUtil.parseMonth(monthString.get()));
        }
    }

    private Optional<Year> parseYear(ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> yearString = argMultimap.getValue(PREFIX_YEAR);
        if (yearString.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(ParserUtil.parseYear(yearString.get()));
        }
    }

    private Optional<Category> parseCategory(ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> categoryString = argMultimap.getValue(PREFIX_CATEGORY);
        if (categoryString.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(ParserUtil.parseCategoryWithAll(categoryString.get()));
        }
    }

    private Optional<TransactionType> parseType(ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> typeString = argMultimap.getValue(PREFIX_TRANSACTION_TYPE);
        if (typeString.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(ParserUtil.parseTransactionTypeWithAll(typeString.get()));
        }
    }

    /**
     * Returns true if at least one of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
