package seedu.ichifund.logic.parser.loan;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_YEAR;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_YEAR;

import java.util.stream.Stream;

import seedu.ichifund.logic.commands.loan.AddLoanCommand;
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
import seedu.ichifund.model.loan.Loan;
import seedu.ichifund.model.loan.LoanId;
import seedu.ichifund.model.loan.Name;

/**
 * Passes user input to the appropriate Parser for commands related to the loan feature.
 */
public class AddLoanCommandParser implements Parser<AddLoanCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddLoanCommand
     * and returns an AddLoanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLoanCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_NAME,
                        PREFIX_START_DAY, PREFIX_START_MONTH, PREFIX_START_YEAR,
                        PREFIX_END_MONTH, PREFIX_END_YEAR, PREFIX_END_DAY,
                        PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_AMOUNT) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLoanCommand.MESSAGE_USAGE));
        }



        LoanId loanId = new LoanId(""); // assignment done in LoanId
        Amount amount = ParserUtil.parsePositiveAmount(argMultimap.getValue(PREFIX_AMOUNT).get());


        Name name = null;
        Description description = null;

        Day endday = null;
        Month endmonth = null;
        Year endyear = null;

        Day startday = null;
        Month startmonth = null;
        Year startyear = null;

        if (argMultimap.getValue(PREFIX_START_MONTH).isPresent()
                && argMultimap.getValue(PREFIX_START_YEAR).isPresent()
                && argMultimap.getValue(PREFIX_START_DAY).isPresent()) {
            startday = ParserUtil.parseDay(argMultimap.getValue(PREFIX_START_DAY).get());
            startmonth = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_START_MONTH).get());
            startyear = ParserUtil.parseYear(argMultimap.getValue(PREFIX_START_YEAR).get());
        }

        if (argMultimap.getValue(PREFIX_END_MONTH).isPresent()
                && argMultimap.getValue(PREFIX_END_YEAR).isPresent()
                && argMultimap.getValue(PREFIX_END_DAY).isPresent()) {
            endday = ParserUtil.parseDay(argMultimap.getValue(PREFIX_END_DAY).get());
            endmonth = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_END_MONTH).get());
            endyear = ParserUtil.parseYear(argMultimap.getValue(PREFIX_END_YEAR).get());
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        }

        Date endDate = constructDate(startday, startmonth, startyear);
        Date startDate = constructDate(endday, endmonth, endyear);

        Loan loan = new Loan(loanId, amount, name, startDate, endDate, description);

        return new AddLoanCommand(loan);
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
    /*
    *//**
     * Parses the {@code Month} field in an {@code ArgumentMultimap}, if present, into
     * a {@code Month} object, and wraps it in an object
     * Else, an empty object is returned.
     *
     * @throws ParseException if the string in {@code argMultimap} is invalid.
     *//*
    private Day parseDay(ArgumentMultimap argMultimap) throws ParseException {
        String dayString = argMultimap.getValue(PREFIX_END_DAY);
        if (dayString.isEmpty()) {
            return empty();
        } else {
            return ParserUtil.parseDay(dayString.get());
        }
    }

    */
    /**
     * Parses the {@code Month} field in an {@code ArgumentMultimap}, if present, into
     * a {@code Month} object, and wraps it in an object
     * Else, an empty object is returned.
     *
     * @throws ParseException if the string in {@code argMultimap} is invalid.
     *//*
    private Month parseMonth(ArgumentMultimap argMultimap) throws ParseException {
        String monthString = argMultimap.getValue(PREFIX_END_MONTH);
        if (monthString.isEmpty()) {
            return empty();
        } else {
            return ParserUtil.parseMonth(monthString.get());
        }
    }

    *//**
     * Parses the {@code Year} field in an {@code ArgumentMultimap}, if present, into
     * a {@code Year} object, and wraps it in an object
     * Else, an empty object is returned.
     *
     * @throws ParseException if the string in {@code argMultimap} is invalid.
     *//*
    private Year parseYear(ArgumentMultimap argMultimap) throws ParseException {
        String yearString = argMultimap.getValue(PREFIX_END_YEAR);
        if (yearString.isEmpty()) {
            return empty();
        } else {
            return ParserUtil.parseYear(yearString.get());
        }
    }

    *//**
     * Parses the {@code Name} field in an {@code ArgumentMultimap}, if present, into
     * a {@code Name} object, and wraps it into an object.
     * Else, an empty object is returned.
     *
     * @throws ParseException if the string in {@code argMultimap} is invalid.
     *//*
    private Name parseName(ArgumentMultimap argMultimap) throws ParseException {
        String nameString = argMultimap.getValue(PREFIX_NAME);
        if (nameString.isEmpty()) {
            return empty();
        } else {
            return ParserUtil.parseName(nameString.get());
        }
    }
    */

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
