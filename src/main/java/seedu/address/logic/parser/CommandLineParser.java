package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.HashMap;
import java.util.ServiceLoader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class CommandLineParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern ONEWORD_REGEX = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Used for two-word commands like <code>account edit</code>.
     */
    private static final Pattern TWOWORD_REGEX = Pattern.compile("(?<commandWord>\\S+\\s\\S+)(?<arguments>.*)");

    /**
     * Contains a map of command names to parsers.
     */
    private final HashMap<String, CommandParser> commandParsers;

    /**
     * Creates a command line parser. Commands are found by searching for
     * all classes implementing the interface {@link CommandParser}.
     */
    public CommandLineParser() {
        commandParsers = loadParsers();
    }

    /**
     * Parses user input into command for execution.
     *
     * @param rawUserInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String rawUserInput) throws ParseException {
        final String userInput = rawUserInput.trim();
        CommandParser parser = null;

        Matcher matcher = TWOWORD_REGEX.matcher(userInput);
        if (matcher.matches()) {
            parser = commandParsers.get(matcher.group("commandWord").toLowerCase());
        }

        if (parser == null) {
            matcher = ONEWORD_REGEX.matcher(userInput);
            if (!matcher.matches()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
            }
            parser = commandParsers.get(matcher.group("commandWord").toLowerCase());
        }

        if (parser == null) {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

        final String arguments = matcher.group("arguments");
        return parser.parse(arguments);
    }

    /**
     * Loads all command parsers reachable from the system class loader.
     *
     * @return A map of command names to parsers
     */
    private static HashMap<String, CommandParser> loadParsers() {
        HashMap<String, CommandParser> ret = new HashMap<>();
        for (CommandParser parser : ServiceLoader.load(CommandParser.class, null)) {
            String name = parser.name().toLowerCase();
            if (ret.containsKey(name)) {
                throw new IllegalArgumentException("Duplicate command name.");
            }
            ret.put(name, parser);
        }
        return ret;
    }
}
