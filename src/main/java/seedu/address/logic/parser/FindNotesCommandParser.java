package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.note.FindNotesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.note.NotesContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindNotesCommand object
 */
public class FindNotesCommandParser implements Parser<FindNotesCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindNotesCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindNotesCommand.MESSAGE_USAGE));
        }

        String[] moduleCodeKeywords = trimmedArgs.split("\\s+");

        return new FindNotesCommand(new NotesContainsKeywordsPredicate(Arrays.asList(moduleCodeKeywords)));
    }

}
