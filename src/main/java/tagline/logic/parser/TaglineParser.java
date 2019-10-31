package tagline.logic.parser;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tagline.logic.commands.Command;
import tagline.logic.commands.ExitCommand;
import tagline.logic.commands.HelpCommand;
import tagline.logic.commands.contact.ContactCommand;
import tagline.logic.commands.group.GroupCommand;
import tagline.logic.commands.note.NoteCommand;
import tagline.logic.commands.tag.TagCommand;
import tagline.logic.parser.contact.ContactCommandParser;
import tagline.logic.parser.exceptions.ParseException;
import tagline.logic.parser.group.GroupCommandParser;
import tagline.logic.parser.note.NoteCommandParser;
import tagline.logic.parser.tag.TagCommandParser;

/**
 * Parses user input.
 */
public class TaglineParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?s)(?<commandKey>\\S+)(?<commandStr>.*)");

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

        final String commandKey = matcher.group("commandKey");
        final String commandStr = matcher.group("commandStr");
        switch (commandKey) {

        case ContactCommand.COMMAND_KEY:
            return new ContactCommandParser().parseCommand(commandStr);

        case NoteCommand.COMMAND_KEY:
            return new NoteCommandParser().parseCommand(commandStr);

        case GroupCommand.COMMAND_KEY:
            return new GroupCommandParser().parseCommand(commandStr);

        case TagCommand.COMMAND_KEY:
            return new TagCommandParser().parseCommand(commandStr);

        case ExitCommand.COMMAND_KEY:
            return new ExitCommand();

        case HelpCommand.COMMAND_KEY:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses user input into command for execution, with a list of filled prompts.
     *
     * @param userInput full user input string
     * @param filledPrompts list of filled prompts from the user
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, List<Prompt> filledPrompts) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandKey = matcher.group("commandKey");
        final String commandStr = matcher.group("commandStr");

        switch (commandKey) {

        case ContactCommand.COMMAND_KEY:
            return new ContactCommandParser().parseCommand(commandStr, filledPrompts);

        case NoteCommand.COMMAND_KEY:
            return new NoteCommandParser().parseCommand(commandStr, filledPrompts);

        case GroupCommand.COMMAND_KEY:
            //Currently doesn't support prompts
            return new GroupCommandParser().parseCommand(commandStr);

        case ExitCommand.COMMAND_KEY:
            return new ExitCommand();

        case HelpCommand.COMMAND_KEY:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
