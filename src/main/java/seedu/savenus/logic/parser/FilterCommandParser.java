package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.savenus.logic.commands.FilterCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;

//@@author seanlowjk
/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class FilterCommandParser implements Parser<FilterCommand> {
    /**
     * Parses the given String and returns a SortCommand object for simple execution.
     * @throws ParseException if the String contains duplicate or invalid fields.
     */
    public FilterCommand parse(String args) throws ParseException {
        try {
            List<String> list = new QuantifierParser().parse(args);
            return new FilterCommand(list);
        } catch (ParseException e) {
            String message = e.getMessage();
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, message + "\n" + FilterCommand.EXAMPLE_USAGE));
        }
    }

}
