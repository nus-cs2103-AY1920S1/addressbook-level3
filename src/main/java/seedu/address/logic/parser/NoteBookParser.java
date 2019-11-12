package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddNotesCommand;
import seedu.address.logic.commands.ClearNoteCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteNoteCommand;
import seedu.address.logic.commands.EditNoteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindNoteCommand;
import seedu.address.logic.commands.GoToCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListNoteCommand;
import seedu.address.logic.commands.OpenNoteCommand;
import seedu.address.logic.commands.RedoNoteCommand;
import seedu.address.logic.commands.SortNoteCommand;
import seedu.address.logic.commands.UndoNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses user input.
 */
public class NoteBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile(
            "(?<commandWord>\\S+)(?<arguments>.*)", Pattern.DOTALL);


    /**
     * Parses user input into command for execution.
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

        case AddNotesCommand.COMMAND_WORD:
            return new AddNoteCommandParser().parse(arguments);

        case EditNoteCommand.COMMAND_WORD:
            return new EditNoteCommandParser().parse(arguments);

        case DeleteNoteCommand.COMMAND_WORD:
            return new DeleteNoteCommandParser().parse(arguments);

        case OpenNoteCommand.COMMAND_WORD:
            return new OpenNoteCommandParser().parse(arguments);

        case ClearNoteCommand.COMMAND_WORD:
            return new ClearNoteCommand();

        case FindNoteCommand.COMMAND_WORD:
            return new FindNoteCommandParser().parse(arguments);

        case ListNoteCommand.COMMAND_WORD:
            return new ListNoteCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case GoToCommand.COMMAND_WORD:
            return new GoToCommandParser().parse(arguments);

        case SortNoteCommand.COMMAND_WORD:
            return new SortNoteCommandParser().parse(arguments);

        case UndoNoteCommand.COMMAND_WORD:
            return new UndoNoteCommand();

        case RedoNoteCommand.COMMAND_WORD:
            return new RedoNoteCommand();
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
