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

import seedu.ichifund.commons.core.index.Index;
import seedu.ichifund.logic.commands.loan.EditLoanCommand;
import seedu.ichifund.logic.commands.loan.EditLoanCommand.EditLoanDescriptor;
import seedu.ichifund.logic.parser.ArgumentMultimap;
import seedu.ichifund.logic.parser.ArgumentTokenizer;
import seedu.ichifund.logic.parser.Parser;
import seedu.ichifund.logic.parser.ParserUtil;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;

/**
 * Parses input arguments and creates a new EditLoanCommand object
 */
public class EditLoanCommandParser implements Parser<EditLoanCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditLoanCommand
     * and returns an EditLoanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditLoanCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_NAME,
                        PREFIX_START_DAY, PREFIX_START_MONTH, PREFIX_START_YEAR,
                        PREFIX_END_DAY, PREFIX_END_MONTH, PREFIX_END_YEAR,
                        PREFIX_DESCRIPTION);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditLoanCommand.MESSAGE_USAGE), pe);
        }

        EditLoanDescriptor editLoanDescriptor = new EditLoanDescriptor();

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editLoanDescriptor.setDescription(ParserUtil
                    .parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editLoanDescriptor.setAmount(ParserUtil.parsePositiveAmount(argMultimap
                    .getValue(PREFIX_AMOUNT).get()));
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editLoanDescriptor.setName(ParserUtil.parseName(argMultimap
                    .getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_START_MONTH).isPresent()) {
            if (argMultimap.getValue(PREFIX_START_YEAR).isPresent()) {
                Day startDay = ParserUtil.parseDay(argMultimap.getValue(PREFIX_START_DAY).get());
                Month startMonth = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_START_MONTH).get());
                Year startYear = ParserUtil.parseYear(argMultimap.getValue(PREFIX_START_YEAR).get());
                Date startDate = constructDate(startDay, startMonth, startYear);
                editLoanDescriptor.setStartDate(startDate);
            }
        }
        if (argMultimap.getValue(PREFIX_END_MONTH).isPresent()) {
            if (argMultimap.getValue(PREFIX_END_YEAR).isPresent()) {
                Day endDay = ParserUtil.parseDay(argMultimap.getValue(PREFIX_END_DAY).get());
                Month endMonth = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_END_MONTH).get());
                Year endYear = ParserUtil.parseYear(argMultimap.getValue(PREFIX_END_YEAR).get());
                Date endDate = constructDate(endDay, endMonth, endYear);
                editLoanDescriptor.setEndDate(endDate);
            }
        }

        if (!editLoanDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditLoanCommand.MESSAGE_NOT_EDITED);
        }

        return new EditLoanCommand(index, editLoanDescriptor);
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
}
