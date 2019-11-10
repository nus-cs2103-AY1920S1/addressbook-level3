package seedu.address.logic.parser.verification;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.verification.CheckCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CheckCommand object
 */
public class CheckCommandParser implements Parser<CheckCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * DescriptionCommand and returns a DescriptionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public CheckCommand parse(String args) throws ParseException {
        String parsedArgs = args.strip().toUpperCase();
        if ("".equals(parsedArgs)) {
            return new CheckCommand("ALL");
        } else if ("ALL".equals(parsedArgs) || "FOCUS".equals(parsedArgs)
                || "MCS".equals(parsedArgs) || "CORE".equals(parsedArgs)) {
            return new CheckCommand(parsedArgs);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE));
        }
    }
}
