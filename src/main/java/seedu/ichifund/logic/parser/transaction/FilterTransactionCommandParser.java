package seedu.ichifund.logic.parser.transaction;

import seedu.ichifund.logic.commands.transaction.AddTransactionCommand;
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
import seedu.ichifund.model.transaction.Transaction;
import seedu.ichifund.model.transaction.TransactionCategoryPredicate;
import seedu.ichifund.model.transaction.TransactionDatePredicate;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_YEAR;

/**
 * Parses input arguments and creates a new FilterTransactionCommand object.
 */
public class FilterTransactionCommandParser implements Parser<FilterTransactionCommand> {
    @Override
    public FilterTransactionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_MONTH, PREFIX_YEAR);

        if (!arePrefixesPresent(argMultimap, PREFIX_MONTH, PREFIX_YEAR)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FilterTransactionCommand.MESSAGE_USAGE));
        }

        Month month = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_MONTH).get());
        Year year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
        Optional<String> optionalCategoryString = argMultimap.getValue(PREFIX_CATEGORY);

        Predicate<Transaction> predicate;

        if (optionalCategoryString.isPresent()) {
            Category category = ParserUtil.parseCategory(optionalCategoryString.get());
            predicate = new TransactionCategoryPredicate(category).and(new TransactionDatePredicate(month, year));
        } else {
            predicate = new TransactionDatePredicate(month, year);
        }

        return new FilterTransactionCommand(predicate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
