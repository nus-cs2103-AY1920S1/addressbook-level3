package seedu.weme.logic.parser.contextparser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.exportcommand.ExportClearCommand;
import seedu.weme.logic.commands.exportcommand.ExportCommand;
import seedu.weme.logic.commands.exportcommand.UnstageCommand;
import seedu.weme.logic.commands.generalcommand.HelpCommand;
import seedu.weme.logic.parser.commandparser.exportcommandparser.ExportCommandParser;
import seedu.weme.logic.parser.commandparser.exportcommandparser.UnstageCommandParser;
import seedu.weme.logic.parser.exceptions.ParseException;

/**
 * Parses user input in the export context.
 */
public class ExportParser extends WemeParser {

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform to the expected format
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

        case UnstageCommand.COMMAND_WORD:
            return new UnstageCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case ExportClearCommand.COMMAND_WORD:
            return new ExportClearCommand();

        default:
            return super.parseCommand(userInput);
        }
    }
}
