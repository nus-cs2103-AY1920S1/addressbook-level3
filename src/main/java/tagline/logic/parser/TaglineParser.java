package tagline.logic.parser;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tagline.logic.commands.Command;
import tagline.logic.commands.ExitCommand;
import tagline.logic.commands.HelpCommand;
import tagline.logic.commands.contact.ContactCommand;
import tagline.logic.parser.contact.ContactCommandParser;
import tagline.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class TaglineParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandKey>\\S+)(?<commandStr>.*)");

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

        case ExitCommand.COMMAND_KEY:
            return new ExitCommand();

        case HelpCommand.COMMAND_KEY:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
