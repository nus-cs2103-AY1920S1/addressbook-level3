package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.savenus.logic.commands.SortCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given String and returns a SortCommand object for simple execution.
     * @throws ParseException if the String contains duplicate or invalid fields.
     */
    public SortCommand parse(String args) throws ParseException {
        try {
            List<String> list = new FieldParser().parse(args);
            return new SortCommand(list);
        } catch (ParseException e) {
            String message = e.getMessage();
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, message + "\n" + SortCommand.EXAMPLE_USAGE));
        }
    }
}
