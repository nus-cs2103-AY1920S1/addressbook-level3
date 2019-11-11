package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.comparator.AmountComparator;
import seedu.address.logic.comparator.DateComparator;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of argument in the context of the SortCommand
     * and returns a SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.toLowerCase().equals("date/a")) {
            return new SortCommand(new DateComparator());
        } else if (trimmedArgs.toLowerCase().equals("amount/a")) {
            return new SortCommand(new AmountComparator());
        } else if (trimmedArgs.toLowerCase().equals("date/d")) {
            return new SortCommand(new DateComparator().reversed());
        } else if (trimmedArgs.toLowerCase().equals("amount/d")) {
            return new SortCommand(new AmountComparator().reversed());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

}
