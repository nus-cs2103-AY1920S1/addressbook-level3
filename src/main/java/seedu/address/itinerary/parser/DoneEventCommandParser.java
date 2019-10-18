package seedu.address.itinerary.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.itinerary.commands.DoneEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DoneEventCommandParser implements Parser<DoneEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DoneCommand
     * and returns a DoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DoneEventCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DoneEventCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneEventCommand.MESSAGE_USAGE), pe);
        }
    }

}
