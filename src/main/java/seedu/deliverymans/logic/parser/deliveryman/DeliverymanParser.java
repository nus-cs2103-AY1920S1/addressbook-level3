package seedu.deliverymans.logic.parser.deliveryman;

import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.deliverymans.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.deliveryman.DeliverymanAddCommand;
import seedu.deliverymans.logic.commands.deliveryman.DeliverymanAssignCommand;
import seedu.deliverymans.logic.commands.deliveryman.DeliverymanDeleteCommand;
import seedu.deliverymans.logic.commands.deliveryman.DeliverymanEditCommand;
import seedu.deliverymans.logic.commands.deliveryman.DeliverymanEnterRecordCommand;
import seedu.deliverymans.logic.commands.deliveryman.DeliverymanGetStatisticsCommand;
import seedu.deliverymans.logic.commands.deliveryman.DeliverymanListStatusCommand;
import seedu.deliverymans.logic.commands.deliveryman.DeliverymanStatusSwitchCommand;
import seedu.deliverymans.logic.commands.universal.HelpCommand;
import seedu.deliverymans.logic.parser.exceptions.ParseException;

/**
 * (to be added)
 */
public class DeliverymanParser {
    /**
     * Used for initial separation of command word and args.
     */
    public static final String COMMAND_WORD = "-deliverymen";
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
        case DeliverymanAddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case DeliverymanAssignCommand.COMMAND_WORD:
            return new AssignCommandParser().parse(arguments);

        case DeliverymanDeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeliverymanEditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeliverymanGetStatisticsCommand.COMMAND_WORD:
            return new DeliverymanGetStatisticsCommand();

        case DeliverymanListStatusCommand.COMMAND_WORD:
            return new DeliverymanListStatusCommand();

        case DeliverymanEnterRecordCommand.COMMAND_WORD:
            return new EnterRecordCommandParser().parse(arguments);

        case DeliverymanStatusSwitchCommand.COMMAND_WORD:
            return new StatusSwitchCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
