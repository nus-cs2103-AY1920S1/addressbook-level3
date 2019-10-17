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
                ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_MONTH, PREFIX_YEAR, PREFIX_TRANSACTION_TYPE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MONTH, PREFIX_YEAR)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FilterTransactionCommand.MESSAGE_USAGE));
        }

        Month month = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_MONTH).get());
        Year year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
        Optional<Category> category = parseCategory(argMultimap);
        Optional<TransactionType> transactionType = parseType(argMultimap);

        return buildCommand(month, year, category, transactionType);
    }

    private FilterTransactionCommand buildCommand(Month month, Year year, Optional<Category> category,
                                                  Optional<TransactionType> transactionType) {
        FilterTransactionCommand.FilterTransactionCommandBuilder builder =
                new FilterTransactionCommand.FilterTransactionCommandBuilder();

        builder.setMonth(month);
        builder.setYear(year);
        builder.setCategory(category);
        builder.setType(transactionType);

        return builder.build();
    }

    private Optional<Category> parseCategory(ArgumentMultimap argMultimap) {
        return argMultimap
                .getValue(PREFIX_CATEGORY)
                .flatMap(categoryString -> {
                    if (categoryString.equals(Category.CATEGORY_ALL.toString())) {
                        return Optional.of(Category.CATEGORY_ALL);
                    } else {
                        return Optional.of(new Category(categoryString));
                    }
                });

    }

    private Optional<TransactionType> parseType(ArgumentMultimap argMultimap) {
        return argMultimap
                .getValue(PREFIX_TRANSACTION_TYPE)
                .flatMap(typeString -> {
                    if (typeString.equals(TransactionType.TRANSACTION_TYPE_ALL.toString())) {
                        return Optional.of(TransactionType.TRANSACTION_TYPE_ALL);
                    } else {
                        return Optional.of(new TransactionType(typeString));
                    }
                });
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
