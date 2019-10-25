package seedu.deliverymans.logic.parser.restaurant;

import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.deliverymans.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.restaurant.AddFoodCommand;
import seedu.deliverymans.logic.commands.restaurant.AddRatingCommand;
import seedu.deliverymans.logic.commands.restaurant.DeleteFoodCommand;
import seedu.deliverymans.logic.commands.restaurant.EditDetailsCommand;
import seedu.deliverymans.logic.commands.restaurant.ExitEditCommand;
import seedu.deliverymans.logic.commands.universal.HelpCommand;
import seedu.deliverymans.logic.parser.exceptions.ParseException;

/**
 * Parses user input in editing context
 */
public class EditingParser {
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
        case EditDetailsCommand.COMMAND_WORD:
            return new EditDetailsCommandParser().parse(arguments);

        case AddFoodCommand.COMMAND_WORD:
            return new AddFoodCommandParser().parse(arguments);

        case AddRatingCommand.COMMAND_WORD:
            return new AddRatingCommandParser().parse(arguments);

        case DeleteFoodCommand.COMMAND_WORD:
            return new DeleteFoodCommandParser().parse(arguments);

        case ExitEditCommand.COMMAND_WORD:
            return new ExitEditCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
