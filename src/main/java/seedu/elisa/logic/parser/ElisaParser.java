package seedu.elisa.logic.parser;

import static seedu.elisa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.elisa.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.elisa.logic.commands.ClearCommand;
import seedu.elisa.logic.commands.CloseCommand;
import seedu.elisa.logic.commands.Command;
import seedu.elisa.logic.commands.DeleteCommand;
import seedu.elisa.logic.commands.DoneCommand;
import seedu.elisa.logic.commands.DownCommand;
import seedu.elisa.logic.commands.EditCommand;
import seedu.elisa.logic.commands.ExitCommand;
import seedu.elisa.logic.commands.FindCommand;
import seedu.elisa.logic.commands.JokeCommand;
import seedu.elisa.logic.commands.OpenCommand;
import seedu.elisa.logic.commands.PriorityCommand;
import seedu.elisa.logic.commands.RedoCommand;
import seedu.elisa.logic.commands.ShowCommand;
import seedu.elisa.logic.commands.SnoozeCommand;
import seedu.elisa.logic.commands.SortCommand;
import seedu.elisa.logic.commands.ThemeCommand;
import seedu.elisa.logic.commands.UndoCommand;
import seedu.elisa.logic.commands.UpCommand;
import seedu.elisa.logic.parser.exceptions.ParseException;
import seedu.elisa.model.ElisaCommandHistory;

/**
 * Parses user input.
 */
public class ElisaParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord>\\S+)(?<description>[^-]*)(?<flags>.*)");

    private ElisaCommandHistory elisaCommandHistory;

    public ElisaParser(ElisaCommandHistory elisaCommandHistory) {
        this.elisaCommandHistory = elisaCommandHistory;
    }

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
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        final String commandWord = matcher.group("commandWord");
        final String description = matcher.group("description");
        final String flags = " " + matcher.group("flags");

        switch (commandWord) {

        case "task":
            return new AddTaskCommandParser().parse(description, flags);

        case "event":
            return new AddEventCommandParser().parse(description, flags);

        case "reminder":
            return new AddReminderCommandParser().parse(description, flags);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(description, flags);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(description, flags);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(description, flags);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand(elisaCommandHistory);

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand(elisaCommandHistory);

        case ShowCommand.COMMAND_WORD:
            return new ShowCommandParser().parse(description, flags);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(description, flags);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case PriorityCommand.COMMAND_WORD:
            return new PriorityCommandParser().parse(description, flags);

        case DoneCommand.COMMAND_WORD:
            return new DoneCommandParser().parse(description, flags);

        case JokeCommand.COMMAND_WORD:
            return new JokeCommand();

        case ThemeCommand.COMMAND_WORD:
            return new ThemeCommand(description);

        case UpCommand.COMMAND_WORD:
            return new UpCommand(description);

        case DownCommand.COMMAND_WORD:
            return new DownCommand(description);

        case OpenCommand.COMMAND_WORD:
            return new OpenCommandParser().parse(description, flags);

        case CloseCommand.COMMAND_WORD:
            return new CloseCommand();

        case SnoozeCommand.COMMAND_WORD:
            return new SnoozeCommandParser().parse(description, flags);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
