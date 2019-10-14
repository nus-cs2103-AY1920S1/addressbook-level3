package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.GenReportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.IdentificationNumber;

//@@author bernicechio
/**
 * Parses input bodyID and creates a new GenReportCommand object.
 */
public class GenReportCommandParser implements Parser<GenReportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GenReportCommand
     * and returns a GenReportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenReportCommand parse(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenReportCommand.MESSAGE_USAGE));
        }

        IdentificationNumber idNum = ParserUtil.parseIdentificationNumber(args);

        return new GenReportCommand(idNum);
    }
}
