package seedu.ichifund.logic.parser.repeater;

import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_YEAR;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH_END_OFFSET;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH_START_OFFSET;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_YEAR;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_TRANSACTION_TYPE;

import java.util.stream.Stream;

import seedu.ichifund.logic.commands.repeater.AddRepeaterCommand;
import seedu.ichifund.logic.parser.ArgumentMultimap;
import seedu.ichifund.logic.parser.ArgumentTokenizer;
import seedu.ichifund.logic.parser.Parser;
import seedu.ichifund.logic.parser.ParserUtil;
import seedu.ichifund.logic.parser.Prefix;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.repeater.MonthOffset;
import seedu.ichifund.model.repeater.Repeater;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.TransactionType;


/**
 * Parses input arguments and creates a new AddRepeaterCommand object.
 */
public class AddRepeaterCommandParser implements Parser<AddRepeaterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddRepeaterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddRepeaterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_AMOUNT, PREFIX_CATEGORY,
                        PREFIX_TRANSACTION_TYPE, PREFIX_MONTH_START_OFFSET, PREFIX_MONTH_END_OFFSET,
                        PREFIX_START_DAY, PREFIX_START_MONTH, PREFIX_START_YEAR,
                        PREFIX_END_DAY, PREFIX_END_MONTH, PREFIX_END_YEAR);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_AMOUNT, PREFIX_CATEGORY,
                        PREFIX_TRANSACTION_TYPE, PREFIX_MONTH_START_OFFSET, PREFIX_MONTH_END_OFFSET,
                        PREFIX_START_DAY, PREFIX_START_MONTH, PREFIX_START_YEAR,
                        PREFIX_END_DAY, PREFIX_END_MONTH, PREFIX_END_YEAR)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddRepeaterCommand.MESSAGE_USAGE));
        }

        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Amount amount = ParserUtil.parsePositiveAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Category category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
        TransactionType transactionType = ParserUtil.parseTransactionType(argMultimap
                .getValue(PREFIX_TRANSACTION_TYPE).get());
        MonthOffset monthStartOffset = ParserUtil.parseMonthOffset(argMultimap
                .getValue(PREFIX_MONTH_START_OFFSET).get());
        MonthOffset monthEndOffset = ParserUtil.parseMonthOffset(argMultimap
                .getValue(PREFIX_MONTH_END_OFFSET).get());

        Day startDay = ParserUtil.parseDay(argMultimap.getValue(PREFIX_START_DAY).get());
        Month startMonth = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_START_MONTH).get());
        Year startYear = ParserUtil.parseYear(argMultimap.getValue(PREFIX_START_YEAR).get());
        Date startDate = constructDate(startDay, startMonth, startYear);

        Day endDay = ParserUtil.parseDay(argMultimap.getValue(PREFIX_END_DAY).get());
        Month endMonth = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_END_MONTH).get());
        Year endYear = ParserUtil.parseYear(argMultimap.getValue(PREFIX_END_YEAR).get());
        Date endDate = constructDate(endDay, endMonth, endYear);

        Repeater repeater = new Repeater(description, amount, category, transactionType,
                monthStartOffset, monthEndOffset, startDate, endDate);

        return new AddRepeaterCommand(repeater);
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
