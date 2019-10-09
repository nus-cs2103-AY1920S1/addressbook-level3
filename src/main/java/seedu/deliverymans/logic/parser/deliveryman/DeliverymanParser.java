package seedu.deliverymans.logic.parser.deliveryman;

import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.deliverymans.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.deliveryman.AssignCommand;
import seedu.deliverymans.logic.commands.deliveryman.EditCommand;
import seedu.deliverymans.logic.commands.deliveryman.ListAvailCommand;
import seedu.deliverymans.logic.commands.deliveryman.ListCommand;
import seedu.deliverymans.logic.commands.deliveryman.SortCommand;
import seedu.deliverymans.logic.commands.deliveryman.StatusCommand;
import seedu.deliverymans.logic.commands.universal.HelpCommand;
import seedu.deliverymans.logic.parser.exceptions.ParseException;

/**
 * (to be added)
 */
public class DeliverymanParser {
    /**
     * Used for initial separation of command word and args.
     */
    public static final String COMMAND_WORD = "deliveryman";
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
        // case add??

        case AssignCommand.COMMAND_WORD:
            return new AssignCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand(arguments);

        case ListAvailCommand.COMMAND_WORD:
            return new ListAvailCommand(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommand(arguments);

        case StatusCommand.COMMAND_WORD:
            return new StatusCommand(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
