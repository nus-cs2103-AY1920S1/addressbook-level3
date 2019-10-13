package seedu.ichifund.logic.parser;

import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH;
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
import seedu.ichifund.model.person.Person;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.Transaction;



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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTransactionCommand.MESSAGE_USAGE));
        }

        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Category category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
        Day day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).get());
        Month month = ParserUtil.parseMonth(argMultimap.getAllValues(PREFIX_MONTH));
        Year year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
        Date date = new Date(day, month, year);

        Transaction transaction = new Transaction(description, amount, category, date);

        return new AddTransactionCommand(transaction);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
