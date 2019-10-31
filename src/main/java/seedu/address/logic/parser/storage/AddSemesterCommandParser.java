package seedu.address.logic.parser.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.storage.AddSemesterCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.semester.SemesterName;

/**
 * Parses input arguments and creates a new AddSemesterCommand object.
 */
public class AddSemesterCommandParser implements Parser<AddSemesterCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddSemesterCommand and returns an AddSemesterCommand object for
     * execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddSemesterCommand parse(String args) throws ParseException {
        String semesterName = args.trim().toUpperCase();

        if (semesterName.isEmpty() || !SemesterName.isValidSemesterName(semesterName)
                || SemesterName.isMainstreamSemester(semesterName)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSemesterCommand.MESSAGE_USAGE));
        }

        return new AddSemesterCommand(SemesterName.valueOf(semesterName));
    }
}
