package seedu.address.logic.parser.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.storage.EditTitleCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditTitleCommand object.
 */
public class EditTitleCommandParser implements Parser<EditTitleCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditTitleCommand and returns an EditTitleCommand object for
     * execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public EditTitleCommand parse(String args) throws ParseException {
        String studyPlanName = args.trim();
        if (studyPlanName.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTitleCommand.MESSAGE_USAGE));
        }

        return new EditTitleCommand(studyPlanName);
    }

}
