package seedu.address.logic.parser.verification;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.verification.DescriptionCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DescriptionCommand object
 */
public class DescriptionCommandParser implements Parser<DescriptionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DescriptionCommand and returns a DescriptionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DescriptionCommand parse(String args) throws ParseException {
        if ("".equals(args)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DescriptionCommand.MESSAGE_USAGE));
        } else {
            return new DescriptionCommand(args.strip().toUpperCase());
        }
    }
}
