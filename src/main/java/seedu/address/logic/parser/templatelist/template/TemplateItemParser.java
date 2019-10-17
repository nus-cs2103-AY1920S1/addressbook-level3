package seedu.address.logic.parser.templatelist.template;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.templatelist.template.AddTemplateItemCommand;
import seedu.address.logic.commands.templatelist.template.DeleteTemplateItemCommand;
import seedu.address.logic.commands.templatelist.template.EditTemplateItemCommand;
import seedu.address.logic.commands.templatelist.template.ListTemplateItemCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class TemplateItemParser {

    /**
     * Used for initial separation of command word and args.
     */
    public static final String LIST_TYPE_WORD = "template";
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

        case AddTemplateItemCommand.COMMAND_WORD:
            return new AddTemplateItemCommandParser().parse(arguments);

        case EditTemplateItemCommand.COMMAND_WORD:
            return new EditTemplateItemCommandParser().parse(arguments);

        case DeleteTemplateItemCommand.COMMAND_WORD:
            return new DeleteTemplateItemCommandParser().parse(arguments);

        case ListTemplateItemCommand.COMMAND_WORD:
            return new ListTemplateItemCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
