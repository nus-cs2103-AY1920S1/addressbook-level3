package seedu.billboard.logic.parser;

import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.billboard.logic.commands.AddRecurrenceCommand;
import seedu.billboard.logic.commands.ListRecurrenceCommand;
import seedu.billboard.logic.commands.RecurrenceCommand;
import seedu.billboard.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class RecurrenceCommandParser implements Parser<RecurrenceCommand> {

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
    public RecurrenceCommand parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecurrenceCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        /*case AddRecurrenceCommand.COMMAND_WORD:
            return new AddRecurrenceCommandParser().parse(arguments);
        case RemoveOccurenceCommand.COMMAND_WORD:
            return new RemoveTagCommandParser().parse(arguments);*/

        case ListRecurrenceCommand.COMMAND_WORD:
            return new ListRecurrenceCommand();

        case AddRecurrenceCommand.COMMAND_WORD:
            return new AddRecurrenceCommandParser().parse(arguments);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecurrenceCommand.MESSAGE_USAGE));
        }

    }
}
