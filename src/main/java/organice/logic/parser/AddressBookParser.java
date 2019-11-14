package organice.logic.parser;

import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import organice.logic.commands.AddCommand;
import organice.logic.commands.ClearCommand;
import organice.logic.commands.Command;
import organice.logic.commands.DeleteCommand;
import organice.logic.commands.DoneCommand;
import organice.logic.commands.EditCommand;
import organice.logic.commands.ExactFindCommand;
import organice.logic.commands.ExitCommand;
import organice.logic.commands.FindCommand;
import organice.logic.commands.HelpCommand;
import organice.logic.commands.ListCommand;
import organice.logic.commands.MatchCommand;
import organice.logic.commands.ProcessingCommand;
import organice.logic.commands.ProcessingMarkDoneCommand;
import organice.logic.commands.SortCommand;
import organice.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

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

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ExactFindCommand.COMMAND_WORD:
            return new ExactFindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ProcessingCommand.COMMAND_WORD:
            return new ProcessingCommandParser().parse(arguments);

        case ProcessingMarkDoneCommand.COMMAND_WORD:
            return new ProcessingMarkDoneCommandParser().parse(arguments);

        case MatchCommand.COMMAND_WORD:
            return new MatchCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case DoneCommand.COMMAND_WORD:
            return new DoneCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
