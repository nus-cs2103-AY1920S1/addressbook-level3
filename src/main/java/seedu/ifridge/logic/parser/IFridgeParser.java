package seedu.ifridge.logic.parser;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.ifridge.commons.core.IFridgeSettings;
import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.ExitCommand;
import seedu.ifridge.logic.commands.HelpCommand;
import seedu.ifridge.logic.parser.exceptions.ParseException;
import seedu.ifridge.logic.parser.grocerylist.GroceryListParser;
import seedu.ifridge.logic.parser.templatelist.TemplateListParser;
import seedu.ifridge.logic.parser.wastelist.WasteListParser;

/**
 * Parses user input.
 */
public class IFridgeParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<listType>\\S+)(?<listCommand>.*)");

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

        final String listType = matcher.group("listType");
        final String listCommand = matcher.group("listCommand");
        switch (listType) {
        case GroceryListParser.LIST_TYPE_WORD:
            return new GroceryListParser().parseCommand(listCommand, iFridgeSettings);

        case TemplateListParser.LIST_TYPE_WORD:
            return new TemplateListParser().parseCommand(listCommand);

        case WasteListParser.LIST_TYPE_WORD:
            return new WasteListParser().parseCommand(listCommand);

        case ShoppingListParser.LIST_TYPE_WORD:
            return new ShoppingListParser().parseCommand(listCommand);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
