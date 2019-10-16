package seedu.address.logic.parser.templateList;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.templateList.AddTemplateListCommand;
import seedu.address.logic.commands.templateList.ClearTemplateListCommand;
import seedu.address.logic.commands.templateList.DeleteTemplateListCommand;
import seedu.address.logic.commands.templateList.EditTemplateListCommand;
import seedu.address.logic.commands.templateList.ListTemplateListCommand;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class TemplateListParser {

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

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
