package seedu.address.calendar.parser;

import seedu.address.address.logic.commands.HelpCommand;
import seedu.address.calendar.model.Calendar;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public abstract class Parser {
    /**
     * Used for initial separation of command word and args.
     */
    protected static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command<Calendar> parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        return parseCommand(commandWord, arguments);
    }

    protected abstract Command<Calendar> parseCommand(String commandWord, String arguments) throws ParseException;
}
