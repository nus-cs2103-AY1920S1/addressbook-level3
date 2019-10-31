package seedu.weme.logic.parser.contextparser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.createcommand.AbortCreationCommand;
import seedu.weme.logic.commands.createcommand.CreateCommand;
import seedu.weme.logic.commands.createcommand.TextAddCommand;
import seedu.weme.logic.commands.createcommand.TextDeleteCommand;
import seedu.weme.logic.commands.createcommand.TextEditCommand;
import seedu.weme.logic.commands.generalcommand.HelpCommand;
import seedu.weme.logic.parser.commandparser.createcommandparser.CreateCommandParser;
import seedu.weme.logic.parser.commandparser.createcommandparser.TextAddCommandParser;
import seedu.weme.logic.parser.commandparser.createcommandparser.TextDeleteCommandParser;
import seedu.weme.logic.parser.commandparser.createcommandparser.TextEditCommandParser;
import seedu.weme.logic.parser.exceptions.ParseException;

/**
 * Parses user input in the create context.
 */
public class CreateParser extends WemeParser {

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

        final String commandWord = matcher.group(COMMAND_WORD);
        final String arguments = matcher.group(ARGUMENTS);

        switch (commandWord) {

        case TextAddCommand.COMMAND_WORD:
            return new TextAddCommandParser().parse(arguments);

        case AbortCreationCommand.COMMAND_WORD:
            return new AbortCreationCommand();

        case CreateCommand.COMMAND_WORD:
            return new CreateCommandParser().parse(arguments);

        case TextEditCommand.COMMAND_WORD:
            return new TextEditCommandParser().parse(arguments);

        case TextDeleteCommand.COMMAND_WORD:
            return new TextDeleteCommandParser().parse(arguments);

        default:
            return super.parseCommand(userInput);
        }
    }
}
