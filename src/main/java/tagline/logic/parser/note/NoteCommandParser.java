// @@author shiweing
package tagline.logic.parser.note;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tagline.logic.commands.Command;
import tagline.logic.commands.HelpCommand;
import tagline.logic.commands.note.ClearNoteCommand;
import tagline.logic.commands.note.CreateNoteCommand;
import tagline.logic.commands.note.DeleteNoteCommand;
import tagline.logic.commands.note.EditNoteCommand;
import tagline.logic.commands.note.ListNoteCommand;
import tagline.logic.commands.note.TagNoteCommand;
import tagline.logic.commands.note.UntagNoteCommand;
import tagline.logic.parser.exceptions.ParseException;

/**
 * Parses user input for note commands.
 */
public class NoteCommandParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?s)(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into note command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case CreateNoteCommand.COMMAND_WORD:
            return new CreateNoteParser().parse(arguments);

        case DeleteNoteCommand.COMMAND_WORD:
            return new DeleteNoteParser().parse(arguments);

        case EditNoteCommand.COMMAND_WORD:
            return new EditNoteParser().parse(arguments);

        case ListNoteCommand.COMMAND_WORD:
            return new ListNoteParser().parse(arguments);

        case TagNoteCommand.COMMAND_WORD:
            return new TagNoteParser().parse(arguments);

        case UntagNoteCommand.COMMAND_WORD:
            return new UntagNoteParser().parse(arguments);

        case ClearNoteCommand.COMMAND_WORD:
            return new ClearNoteCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
