package seedu.ifridge.logic.parser;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.HelpCommand;
import seedu.ifridge.logic.commands.shoppinglist.AddShoppingCommand;
import seedu.ifridge.logic.commands.shoppinglist.AddTemplateShoppingCommand;
import seedu.ifridge.logic.commands.shoppinglist.BoughtShoppingCommand;
import seedu.ifridge.logic.commands.shoppinglist.DeleteShoppingCommand;
import seedu.ifridge.logic.commands.shoppinglist.EditShoppingCommand;
import seedu.ifridge.logic.commands.shoppinglist.ListShoppingCommand;
import seedu.ifridge.logic.commands.shoppinglist.MergeShoppingCommand;
import seedu.ifridge.logic.commands.shoppinglist.RedoShoppingCommand;
import seedu.ifridge.logic.commands.shoppinglist.UndoShoppingCommand;
import seedu.ifridge.logic.commands.shoppinglist.UrgentShoppingCommand;
import seedu.ifridge.logic.parser.exceptions.ParseException;
import seedu.ifridge.logic.parser.shoppinglist.AddShoppingCommandParser;
import seedu.ifridge.logic.parser.shoppinglist.AddTemplateShoppingCommandParser;
import seedu.ifridge.logic.parser.shoppinglist.BoughtShoppingCommandParser;
import seedu.ifridge.logic.parser.shoppinglist.DeleteShoppingCommandParser;
import seedu.ifridge.logic.parser.shoppinglist.EditShoppingCommandParser;
import seedu.ifridge.logic.parser.shoppinglist.UrgentShoppingCommandParser;

/**
 * Parses user input.
 */
public class ShoppingListParser {

    /**
     * Used for initial separation of command word and args.
     */
    public static final String LIST_TYPE_WORD = "slist";
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

        case AddShoppingCommand.COMMAND_WORD:
            return new AddShoppingCommandParser().parse(arguments);

        case AddTemplateShoppingCommand.COMMAND_WORD:
            return new AddTemplateShoppingCommandParser().parse(arguments);

        case EditShoppingCommand.COMMAND_WORD:
            return new EditShoppingCommandParser().parse(arguments);

        case DeleteShoppingCommand.COMMAND_WORD:
            return new DeleteShoppingCommandParser().parse(arguments);

        case BoughtShoppingCommand.COMMAND_WORD:
            return new BoughtShoppingCommandParser().parse(arguments);

        case ListShoppingCommand.COMMAND_WORD:
            return new ListShoppingCommand();

        case UrgentShoppingCommand.COMMAND_WORD:
            return new UrgentShoppingCommandParser().parse(arguments);

        case MergeShoppingCommand.COMMAND_WORD:
            return new MergeShoppingCommand();

        case UndoShoppingCommand.COMMAND_WORD:
            return new UndoShoppingCommand();

        case RedoShoppingCommand.COMMAND_WORD:
            return new RedoShoppingCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
