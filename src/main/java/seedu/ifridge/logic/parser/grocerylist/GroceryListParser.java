package seedu.ifridge.logic.parser.grocerylist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.ifridge.commons.core.IFridgeSettings;
import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.HelpCommand;
import seedu.ifridge.logic.commands.defaults.ReminderDefaultCommand;
import seedu.ifridge.logic.commands.grocerylist.AddGroceryCommand;
import seedu.ifridge.logic.commands.grocerylist.DeleteGroceryCommand;
import seedu.ifridge.logic.commands.grocerylist.EditGroceryCommand;
import seedu.ifridge.logic.commands.grocerylist.FindGroceryCommand;
import seedu.ifridge.logic.commands.grocerylist.ListGroceryCommand;
import seedu.ifridge.logic.commands.grocerylist.RedoGroceryCommand;
import seedu.ifridge.logic.commands.grocerylist.ReminderCommand;
import seedu.ifridge.logic.commands.grocerylist.SortGroceryCommand;
import seedu.ifridge.logic.commands.grocerylist.UndoGroceryCommand;
import seedu.ifridge.logic.commands.grocerylist.UseGroceryCommand;
import seedu.ifridge.logic.parser.defaults.ReminderDefaultCommandParser;
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
     * Parses user input into a grocery command for execution.
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

        case DeleteGroceryCommand.COMMAND_WORD:
            return new DeleteGroceryCommandParser().parse(arguments);

        case EditGroceryCommand.COMMAND_WORD:
            return new EditGroceryCommandParser().parse(arguments);

        case FindGroceryCommand.COMMAND_WORD:
            return new FindGroceryCommandParser().parse(arguments);

        case ListGroceryCommand.COMMAND_WORD:
            return new ListGroceryCommand();

        case RedoGroceryCommand.COMMAND_WORD:
            return new RedoGroceryCommand();

        case ReminderCommand.COMMAND_WORD:
            return new ReminderCommandParser().parse(arguments, iFridgeSettings.getNumberOfDays());

        case ReminderDefaultCommand.COMMAND_WORD:
            return new ReminderDefaultCommandParser().parse(arguments);

        case SortGroceryCommand.COMMAND_WORD:
            return new SortGroceryCommandParser().parse(arguments);

        case UndoGroceryCommand.COMMAND_WORD:
            return new UndoGroceryCommand();

        case UseGroceryCommand.COMMAND_WORD:
            return new UseGroceryCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
