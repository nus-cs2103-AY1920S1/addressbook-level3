package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GetStudentGradesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author weikiat97
/**
 * Parses input arguments and creates a new GetStudentGradesCommand object
 */
public class GetStudentGradesCommandParser implements Parser<GetStudentGradesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GetStudentGradesCommand
     * and returns a GetStudentGradesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GetStudentGradesCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new GetStudentGradesCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetStudentGradesCommand.MESSAGE_USAGE), pe);
        }
    }

}
