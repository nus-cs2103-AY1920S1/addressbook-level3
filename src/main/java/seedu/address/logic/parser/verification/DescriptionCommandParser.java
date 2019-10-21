package seedu.address.logic.parser.verification;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODULE;

import seedu.address.logic.commands.verification.DescriptionCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;

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
        String parsedArgs = args.strip().toUpperCase();
        if ("".equals(parsedArgs)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DescriptionCommand.MESSAGE_USAGE));
        } else if (!ModuleCode.isValidCode(parsedArgs)) {
            throw new ParseException(MESSAGE_INVALID_MODULE);
        } else {
            return new DescriptionCommand(parsedArgs);
        }
    }
}
