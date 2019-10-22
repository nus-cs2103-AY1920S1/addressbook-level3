package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ReadCardCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.card.Description;


/**
 * Parses input arguments and creates a new EditCommand object
 */
public class ReadCardCommandParser implements Parser<ReadCardCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReadCardCommand
     * and returns an ReadCardCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReadCardCommand parse(String args) throws ParseException {
        try {
            Description description = ParserUtil.parseCardDescription(args);
            return new ReadCardCommand(description);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReadCardCommand.MESSAGE_USAGE), pe);
        }
    }
}
