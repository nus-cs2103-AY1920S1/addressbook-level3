package seedu.elisa.logic.parser;

import static seedu.elisa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.elisa.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.elisa.logic.commands.ClearCommand;
import seedu.elisa.logic.commands.ClearScreenCommand;
import seedu.elisa.logic.commands.CloseCommand;
import seedu.elisa.logic.commands.Command;
import seedu.elisa.logic.commands.ContinueCommand;
import seedu.elisa.logic.commands.DeleteCommand;
import seedu.elisa.logic.commands.DoneCommand;
import seedu.elisa.logic.commands.DownCommand;
import seedu.elisa.logic.commands.EditCommand;
import seedu.elisa.logic.commands.ExitCommand;
import seedu.elisa.logic.commands.FindCommand;
import seedu.elisa.logic.commands.GameCommand;
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
    protected static final Pattern BASIC_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord>\\S+)(?<description>[^-]*)(?<flags>.*)");

    protected ElisaCommandHistory elisaCommandHistory;

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
        final Matcher matcher = inputToMatcher(userInput);

        final String commandWord = matcher.group("commandWord");
        final String description = matcher.group("description");
        final String flags = " " + matcher.group("flags");

        return parseCommandHelper(commandWord, description, flags);
    }

    /**
     * Converts the user input into the matcher to easily find the command word, description and flags.
     * @param userInput the input from the user.
     * @return the Matcher object of the user's input.
     * @throws ParseException if the matcher is not of a valid format.
     */
    protected Matcher inputToMatcher(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, " This doesn't match anything!"));
        }
        return matcher;
    }

    /**
     * Helper function that does the actual creation of the command.
     * @param commandWord the command word from the user's input.
     * @param description the description that comes with the user's input.
     * @param flags the flags that comes with the user's input.
     * @return the command that is to be executed based on the user's input.
     * @throws ParseException if the command word is not found.
     */
    protected Command parseCommandHelper(String commandWord, String description, String flags) throws ParseException {
        switch (commandWord.toLowerCase()) {

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

        case "continue":
        case ContinueCommand.COMMAND_WORD:
            return new ContinueCommandParser().parse(description, flags);

        case JokeCommand.COMMAND_WORD:
            return new JokeCommand();

        case ThemeCommand.COMMAND_WORD:
            return new ThemeCommand(description);

        case UpCommand.COMMAND_WORD:
            return new UpCommand();

        case DownCommand.COMMAND_WORD:
            return new DownCommand();

        case ClearScreenCommand.COMMAND_WORD:
            return new ClearScreenCommand();

        case OpenCommand.COMMAND_WORD:
            return new OpenCommandParser().parse(description, flags);

        case CloseCommand.COMMAND_WORD:
            return new CloseCommand();

        case SnoozeCommand.COMMAND_WORD:
            return new SnoozeCommandParser().parse(description, flags);

        case GameCommand.COMMAND_WORD:
            return new GameCommandParser().parse(description, "");

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
