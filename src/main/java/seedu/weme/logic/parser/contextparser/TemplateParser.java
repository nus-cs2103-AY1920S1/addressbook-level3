package seedu.weme.logic.parser.contextparser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.generalcommand.HelpCommand;
import seedu.weme.logic.commands.templatecommand.TemplateAddCommand;
import seedu.weme.logic.commands.templatecommand.TemplateDeleteCommand;
import seedu.weme.logic.commands.templatecommand.TemplateEditCommand;
import seedu.weme.logic.parser.commandparser.templatecommandparser.TemplateAddCommandParser;
import seedu.weme.logic.parser.commandparser.templatecommandparser.TemplateDeleteCommandParser;
import seedu.weme.logic.parser.commandparser.templatecommandparser.TemplateEditCommandParser;
import seedu.weme.logic.parser.exceptions.ParseException;

/**
 * Parses user input in the templates context.
 */
public class TemplateParser extends WemeParser {

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case TemplateAddCommand.COMMAND_WORD:
            return new TemplateAddCommandParser().parse(arguments);

        case TemplateDeleteCommand.COMMAND_WORD:
            return new TemplateDeleteCommandParser().parse(arguments);

        case TemplateEditCommand.COMMAND_WORD:
            return new TemplateEditCommandParser().parse(arguments);

        default:
            return super.parseCommand(userInput);
        }
    }
}
