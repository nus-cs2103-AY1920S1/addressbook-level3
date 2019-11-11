package seedu.ichifund.logic.parser.repeater;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_YEAR;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH_END_OFFSET;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH_START_OFFSET;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_YEAR;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_TRANSACTION_TYPE;

import seedu.ichifund.commons.core.index.Index;
import seedu.ichifund.logic.commands.repeater.EditRepeaterCommand;
import seedu.ichifund.logic.commands.repeater.EditRepeaterCommand.EditRepeaterDescriptor;
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
 * Parses input arguments and creates a new EditRepeaterCommand object
 */
public class EditRepeaterCommandParser implements Parser<EditRepeaterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditRepeaterCommand
     * and returns an EditRepeaterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditRepeaterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_DESCRIPTION, PREFIX_AMOUNT, PREFIX_CATEGORY, PREFIX_TRANSACTION_TYPE,
                PREFIX_MONTH_START_OFFSET, PREFIX_MONTH_END_OFFSET, PREFIX_START_MONTH, PREFIX_START_YEAR,
                PREFIX_END_MONTH, PREFIX_END_YEAR);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRepeaterCommand.MESSAGE_USAGE),
                    pe);
        }

        EditRepeaterDescriptor editRepeaterDescriptor = new EditRepeaterDescriptor();
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editRepeaterDescriptor.setDescription(ParserUtil.parseDescription(
                        argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editRepeaterDescriptor.setAmount(ParserUtil.parsePositiveAmount(
                        argMultimap.getValue(PREFIX_AMOUNT).get()));
        }
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            editRepeaterDescriptor.setCategory(ParserUtil.parseCategory(
                        argMultimap.getValue(PREFIX_CATEGORY).get()));
        }
        if (argMultimap.getValue(PREFIX_TRANSACTION_TYPE).isPresent()) {
            editRepeaterDescriptor.setTransactionType(ParserUtil.parseTransactionType(
                        argMultimap.getValue(PREFIX_TRANSACTION_TYPE).get()));
        }
        if (argMultimap.getValue(PREFIX_MONTH_START_OFFSET).isPresent()) {
            editRepeaterDescriptor.setMonthStartOffset(ParserUtil.parseMonthOffset(
                        argMultimap.getValue(PREFIX_MONTH_START_OFFSET).get()));
        }
        if (argMultimap.getValue(PREFIX_MONTH_END_OFFSET).isPresent()) {
            editRepeaterDescriptor.setMonthEndOffset(ParserUtil.parseMonthOffset(
                        argMultimap.getValue(PREFIX_MONTH_END_OFFSET).get()));
        }
        if (argMultimap.getValue(PREFIX_START_MONTH).isPresent()) {
            if (argMultimap.getValue(PREFIX_START_YEAR).isPresent()) {
                Month startMonth = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_START_MONTH).get());
                Year startYear = ParserUtil.parseYear(argMultimap.getValue(PREFIX_START_YEAR).get());
                Date startDate = constructDate(new Day("1"), startMonth, startYear);
                editRepeaterDescriptor.setStartDate(startDate);
            }
        }
        if (argMultimap.getValue(PREFIX_END_MONTH).isPresent()) {
            if (argMultimap.getValue(PREFIX_END_YEAR).isPresent()) {
                Month endMonth = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_END_MONTH).get());
                Year endYear = ParserUtil.parseYear(argMultimap.getValue(PREFIX_END_YEAR).get());
                Date endDate = constructDate(new Day("1"), endMonth, endYear);
                editRepeaterDescriptor.setEndDate(endDate);
            }
        }

        if (!editRepeaterDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditRepeaterCommand.MESSAGE_NOT_EDITED);
        }

        return new EditRepeaterCommand(index, editRepeaterDescriptor);
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
