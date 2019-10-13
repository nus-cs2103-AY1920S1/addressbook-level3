package seedu.weme.logic.parser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.HelpCommand;
import seedu.weme.logic.commands.MemeAddCommand;
import seedu.weme.logic.commands.MemeClearCommand;
import seedu.weme.logic.commands.MemeDeleteCommand;
import seedu.weme.logic.commands.MemeEditCommand;
import seedu.weme.logic.commands.MemeFindCommand;
import seedu.weme.logic.commands.MemeListCommand;
import seedu.weme.logic.parser.exceptions.ParseException;

/**
 * Parses user input in the meme context.
 */
public class MemeParser extends WemeParser {

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

        case MemeAddCommand.COMMAND_WORD:
            return new MemeAddCommandParser().parse(arguments);

        case MemeEditCommand.COMMAND_WORD:
            return new MemeEditCommandParser().parse(arguments);

        case MemeDeleteCommand.COMMAND_WORD:
            return new MemeDeleteCommandParser().parse(arguments);

        case MemeClearCommand.COMMAND_WORD:
            return new MemeClearCommand();

        case MemeFindCommand.COMMAND_WORD:
            return new MemeFindCommandParser().parse(arguments);

        case MemeListCommand.COMMAND_WORD:
            return new MemeListCommand();

        default:
            return super.parseCommand(userInput);
        }
    }
}
