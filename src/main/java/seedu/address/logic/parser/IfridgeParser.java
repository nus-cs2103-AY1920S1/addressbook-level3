package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.groceryList.GroceryListParser;
import seedu.address.logic.parser.templateList.TemplateListParser;
import seedu.address.logic.parser.wastelist.WasteListParser;

/**
 * Parses user input.
 */
public class IfridgeParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<listType>\\S+)(?<others>.*)");

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

        final String listType = matcher.group("listType");
        final String others = matcher.group("others");
        switch (listType) {
        case GroceryListParser.LIST_TYPE_WORD:
            return new GroceryListParser().parseCommand(others);
        case TemplateListParser.LIST_TYPE_WORD:
            return new TemplateListParser().parseCommand(others);
        case WasteListParser.LIST_TYPE_WORD:
            return new WasteListParser().parseCommand(others);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
