package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.address.logic.commands.expense.AddExpenseCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.expense.AddExpenseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Timekeeper;
import seedu.address.model.category.Category;
import seedu.address.model.expense.Description;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Price;
import seedu.address.model.expense.Timestamp;
import seedu.address.model.expense.util.UniqueIdentifierGenerator;


/**
 * Parses input arguments and creates a new AddExpenseCommand object
 */
public class AddExpenseCommandParser implements Parser<AddExpenseCommand> {

    public static final List<Prefix> REQUIRED_PREFIXES = Collections.unmodifiableList(List.of(
            PREFIX_DESCRIPTION, PREFIX_PRICE, PREFIX_CATEGORY
    ));

    public static final List<Prefix> OPTIONAL_PREFIXES = Collections.unmodifiableList(List.of(
            PREFIX_TIMESTAMP
    ));

    /**
     * Parses the given {@code String} of arguments in the context of the AddExpenseCommand
     * and returns an AddExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddExpenseCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_PRICE, PREFIX_CATEGORY, PREFIX_TIMESTAMP);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_PRICE, PREFIX_CATEGORY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_USAGE));
        }

        if (hasRepeatedPrefixes(argMultimap, PREFIX_DESCRIPTION, PREFIX_PRICE, PREFIX_CATEGORY, PREFIX_TIMESTAMP)) {
            throw new ParseException(MESSAGE_REPEATED_PREFIX_COMMAND);
        }

        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Price price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get());
        Category category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
        boolean isTimestampPresent = argMultimap.getValue(PREFIX_TIMESTAMP).isPresent();

        if (isTimestampPresent) {
            Timestamp timestamp = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_TIMESTAMP).get());
            if (Timekeeper.isFutureTimestamp(timestamp)) {
                throw new ParseException("Expense cannot be in the future");
            }
            Expense expense = new Expense(description, price, category, timestamp,
                    UniqueIdentifierGenerator.generateRandomUniqueIdentifier());
            return new AddExpenseCommand(expense);

        } else {
            Expense expense = new Expense(description, price, category,
                    UniqueIdentifierGenerator.generateRandomUniqueIdentifier());
            return new AddExpenseCommand(expense);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if none of the prefixes are repeated
     * {@code ArgumentMultimap}.
     */
    private static boolean hasRepeatedPrefixes(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return !(Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getAllValues(prefix).size() <= 1));
    }

}
