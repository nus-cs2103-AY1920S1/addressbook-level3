package seedu.address.logic.parser.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.storage.DeleteSemesterCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.semester.SemesterName;

/**
 * Parses input arguments and creates a new DeleteSemesterCommand object.
 */
public class DeleteSemesterCommandParser implements Parser<DeleteSemesterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteSemesterCommand
     * and returns a DeleteSemesterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteSemesterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        SemesterName semesterName = null;
        try {
            semesterName = SemesterName.valueOf(trimmedArgs.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteSemesterCommand.MESSAGE_USAGE));
        }

        return new DeleteSemesterCommand(semesterName);
    }

}
