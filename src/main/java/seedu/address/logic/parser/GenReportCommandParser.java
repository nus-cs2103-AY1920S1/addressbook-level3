package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.GenReportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class GenReportCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the GenReportCommand
     * and returns a GenReportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenReportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenReportCommand.MESSAGE_USAGE));
        }

        String bodyID = trimmedArgs;
        //throw exception if invalid bodyID ie not found in list
        return new GenReportCommand();  //pass in body as parameter
    }
}
