package seedu.jarvis.logic.parser;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.ExitCommand;
import seedu.jarvis.logic.commands.HelpCommand;

import seedu.jarvis.logic.commands.address.AddAddressCommand;
import seedu.jarvis.logic.commands.address.ClearAddressCommand;
import seedu.jarvis.logic.commands.address.DeleteAddressCommand;
import seedu.jarvis.logic.commands.address.EditAddressCommand;
import seedu.jarvis.logic.commands.address.FindAddressCommand;
import seedu.jarvis.logic.commands.address.ListAddressCommand;
import seedu.jarvis.logic.commands.course.LookUpCommand;
import seedu.jarvis.logic.commands.history.RedoCommand;
import seedu.jarvis.logic.commands.history.UndoCommand;
import seedu.jarvis.logic.parser.address.AddAddressCommandParser;
import seedu.jarvis.logic.parser.address.DeleteAddressCommandParser;
import seedu.jarvis.logic.parser.address.EditAddressCommandParser;
import seedu.jarvis.logic.parser.address.FindAddressCommandParser;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.logic.parser.history.RedoCommandParser;
import seedu.jarvis.logic.parser.history.UndoCommandParser;

/**
 * Parses user input.
 */
public class JarvisParser {

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

        case AddAddressCommand.COMMAND_WORD:
            return new AddAddressCommandParser().parse(arguments);

        case EditAddressCommand.COMMAND_WORD:
            return new EditAddressCommandParser().parse(arguments);

        case DeleteAddressCommand.COMMAND_WORD:
            return new DeleteAddressCommandParser().parse(arguments);

        case ClearAddressCommand.COMMAND_WORD:
            return new ClearAddressCommand();

        case FindAddressCommand.COMMAND_WORD:
            return new FindAddressCommandParser().parse(arguments);

        case ListAddressCommand.COMMAND_WORD:
            return new ListAddressCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommandParser().parse(arguments);

        case RedoCommand.COMMAND_WORD:
            return new RedoCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case LookUpCommand.COMMAND_WORD:
            return new LookUpCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
