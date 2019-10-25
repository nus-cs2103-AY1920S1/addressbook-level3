package seedu.ifridge.logic.parser.grocerylist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.ifridge.commons.core.IFridgeSettings;
import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.HelpCommand;
import seedu.ifridge.logic.commands.defaults.ListDefaultCommand;
import seedu.ifridge.logic.commands.defaults.ReminderDefaultCommand;
import seedu.ifridge.logic.commands.defaults.SortDefaultCommand;
import seedu.ifridge.logic.commands.grocerylist.AddGroceryCommand;
import seedu.ifridge.logic.commands.grocerylist.DeleteGroceryCommand;
import seedu.ifridge.logic.commands.grocerylist.EditGroceryCommand;
import seedu.ifridge.logic.commands.grocerylist.ListGroceryCommand;
import seedu.ifridge.logic.commands.grocerylist.ReminderCommand;
import seedu.ifridge.logic.parser.defaults.ListDefaultCommandParser;
import seedu.ifridge.logic.parser.defaults.ReminderDefaultCommandParser;
import seedu.ifridge.logic.parser.defaults.SortDefaultCommandParser;
import seedu.ifridge.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class GroceryListParser {

    /**
     * Used for initial separation of command word and args.
     */
    public static final String LIST_TYPE_WORD = "glist";
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, IFridgeSettings iFridgeSettings) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case AddGroceryCommand.COMMAND_WORD:
            return new AddGroceryCommandParser().parse(arguments);

        case ListGroceryCommand.COMMAND_WORD:
            return new ListGroceryCommand();

        case EditGroceryCommand.COMMAND_WORD:
            return new EditGroceryCommandParser().parse(arguments);

        case DeleteGroceryCommand.COMMAND_WORD:
            return new DeleteGroceryCommandParser().parse(arguments);

        case ReminderCommand.COMMAND_WORD:
            return new ReminderCommandParser().parse(arguments, iFridgeSettings.getNumberOfDays());

        case "use":
            return new UseGroceryCommandParser().parse(arguments);

        case ReminderDefaultCommand.COMMAND_WORD:
            return new ReminderDefaultCommandParser().parse(arguments);

        case SortDefaultCommand.COMMAND_WORD:
            return new SortDefaultCommandParser().parse(arguments);

        case ListDefaultCommand.COMMAND_WORD:
            return new ListDefaultCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
