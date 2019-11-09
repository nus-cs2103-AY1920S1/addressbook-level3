package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SubmitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author atharvjoshi
/**
 * Parses input arguments and creates a new SubmitCommand object
 */
public class SubmitCommandParser implements Parser<SubmitCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SubmitCommand
     * and returns a SubmitCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SubmitCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SubmitCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SubmitCommand.MESSAGE_USAGE), pe);
        }
    }

}
