package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.note.TitleMatchesKeywordsPredicate;

/**
 * Parses input arguments and creates a new ViewNoteCommand object
 */
public class ViewNoteCommandParser implements Parser<ViewNoteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewNoteCommand
     * and returns a ViewNoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewNoteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewNoteCommand.MESSAGE_USAGE));
        }

        return new ViewNoteCommand(new TitleMatchesKeywordsPredicate(trimmedArgs));
    }

}
