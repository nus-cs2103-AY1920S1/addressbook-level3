package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.Category;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.TransactionPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object.
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
            PREFIX_MONTH, PREFIX_YEAR, PREFIX_CATEGORY, PREFIX_NAME);

        if (!areAnyPrefixesPresent(argMultimap, PREFIX_CATEGORY, PREFIX_NAME, PREFIX_MONTH, PREFIX_YEAR)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        Optional<Set<Category>> categorySet = parseCategories(argMultimap);
        Optional<Integer> month = parseMonth(argMultimap);
        Optional<Integer> year = parseYear(argMultimap);
        Optional<Description> description = parseDescription(argMultimap);

        return new FilterCommand(new TransactionPredicate(categorySet, month, year, description));
    }

    /**
     * Returns true if at least one of the prefixes contains non-empty {@code Optional} values
     * in the given {@code ArgumentMultimap}.
     */
    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        requireAllNonNull(argumentMultimap, prefixes);
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the {@code Description} field in an {@code ArgumentMultimap}.
     *
     * @throws ParseException if the string in {@code argMultimap} is invalid.
     */
    private Optional<Description> parseDescription(ArgumentMultimap argMultimap) throws ParseException {
        requireNonNull(argMultimap);
        Optional<String> desString = argMultimap.getValue(PREFIX_NAME);
        if (desString.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(ParserUtil.parseDescription(desString.get()));
        }
    }

    /**
     * Parses the {@code Category} field in an {@code ArgumentMultimap}.
     *
     * @throws ParseException if the string in {@code argMultimap} is invalid.
     */
    private Optional<Set<Category>> parseCategories(ArgumentMultimap argMultimap) throws ParseException {
        requireNonNull(argMultimap);
        List<String> categoryString = argMultimap.getAllValues(PREFIX_CATEGORY);
        if (categoryString.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(ParserUtil.parseCategories(categoryString));
        }
    }

    /**
     * Parses the {@code Month} field in an {@code ArgumentMultimap}.
     *
     * @throws ParseException if the string in {@code argMultimap} is invalid.
     */
    private Optional<Integer> parseMonth(ArgumentMultimap argMultimap) throws ParseException {
        requireNonNull(argMultimap);
        Optional<String> monthString = argMultimap.getValue(PREFIX_MONTH);
        if (monthString.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(ParserUtil.parseMonth(monthString.get()));
        }
    }

    /**
     * Parses the {@code Year} field in an {@code ArgumentMultimap}.
     *
     * @throws ParseException if the string in {@code argMultimap} is invalid.
     */
    private Optional<Integer> parseYear(ArgumentMultimap argMultimap) throws ParseException {
        requireNonNull(argMultimap);
        Optional<String> yearString = argMultimap.getValue(PREFIX_YEAR);
        if (yearString.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(ParserUtil.parseYear(yearString.get()));
        }
    }
}
