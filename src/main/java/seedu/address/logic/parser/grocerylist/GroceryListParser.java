package seedu.address.logic.parser.grocerylist;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.grocerylist.AddGroceryCommand;
import seedu.address.logic.commands.grocerylist.DeleteGroceryCommand;
import seedu.address.logic.commands.grocerylist.EditGroceryCommand;
import seedu.address.logic.commands.grocerylist.ListGroceryCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
    public Command parseCommand(String userInput) throws ParseException {
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

        case "use":
            return new UseGroceryCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
