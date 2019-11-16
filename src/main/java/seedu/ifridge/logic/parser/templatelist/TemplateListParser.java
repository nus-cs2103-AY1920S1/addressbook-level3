package seedu.ifridge.logic.parser.templatelist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.HelpCommand;
import seedu.ifridge.logic.commands.templatelist.AddTemplateListCommand;
import seedu.ifridge.logic.commands.templatelist.ClearTemplateListCommand;
import seedu.ifridge.logic.commands.templatelist.DeleteTemplateListCommand;
import seedu.ifridge.logic.commands.templatelist.EditTemplateListCommand;
import seedu.ifridge.logic.commands.templatelist.ListTemplateListCommand;
import seedu.ifridge.logic.commands.templatelist.RedoTemplateCommand;
import seedu.ifridge.logic.commands.templatelist.UndoTemplateCommand;
import seedu.ifridge.logic.parser.exceptions.ParseException;
import seedu.ifridge.logic.parser.templatelist.template.TemplateItemParser;

/**
 * Parses user input.
 */
public class TemplateListParser {

    /**
     * Used for initial separation of command word and args.
     */
    public static final String LIST_TYPE_WORD = "tlist";
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
        case AddTemplateListCommand.COMMAND_WORD:
            return new AddTemplateListCommandParser().parse(arguments);

        case EditTemplateListCommand.COMMAND_WORD:
            return new EditTemplateListCommandParser().parse(arguments);

        case DeleteTemplateListCommand.COMMAND_WORD:
            return new DeleteTemplateListCommandParser().parse(arguments);

        case ClearTemplateListCommand.COMMAND_WORD:
            return new ClearTemplateListCommand();

        case ListTemplateListCommand.COMMAND_WORD:
            return new ListTemplateListCommand();

        case TemplateItemParser.LIST_TYPE_WORD:
            return new TemplateItemParser().parseCommand(arguments);

        case UndoTemplateCommand.COMMAND_WORD:
            return new UndoTemplateCommand();

        case RedoTemplateCommand.COMMAND_WORD:
            return new RedoTemplateCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
