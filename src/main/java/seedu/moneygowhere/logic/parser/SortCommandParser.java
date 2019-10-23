package seedu.moneygowhere.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.LinkedHashSet;

import seedu.moneygowhere.logic.commands.SortCommand;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.logic.sorting.SortAttribute;
import seedu.moneygowhere.logic.sorting.SortField;
import seedu.moneygowhere.logic.sorting.SortOrder;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_COST, PREFIX_REMARK, PREFIX_TAG);

        LinkedHashSet<SortField> fields = new LinkedHashSet<>();
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            SortOrder order = convert(argMultimap.getValue(PREFIX_DATE).get());
            fields.add(new SortField(SortAttribute.DATE, order));
        }
        if (argMultimap.getValue(PREFIX_COST).isPresent()) {
            SortOrder order = convert(argMultimap.getValue(PREFIX_COST).get());
            fields.add(new SortField(SortAttribute.COST, order));
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            SortOrder order = convert(argMultimap.getValue(PREFIX_NAME).get());
            fields.add(new SortField(SortAttribute.NAME, order));
        }
        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            SortOrder order = convert(argMultimap.getValue(PREFIX_REMARK).get());
            fields.add(new SortField(SortAttribute.REMARK, order));
        }

        if (fields.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand(fields);
    }

    /**
     * Converts an order string to a valid SortOrder.
     * @param order Order
     * @return SortOrder enum
     * @throws ParseException If the order input is invalid
     */
    private static SortOrder convert(String order) throws ParseException {
        switch (order.toUpperCase()) {
        case "DESC":
            return SortOrder.DESCENDING;
        case "ASC":
            return SortOrder.ASCENDING;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
}
