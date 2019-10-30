package seedu.weme.logic.parser.contextparser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.generalcommand.HelpCommand;
import seedu.weme.logic.commands.memecommand.MemeAddCommand;
import seedu.weme.logic.commands.memecommand.MemeArchiveCommand;
import seedu.weme.logic.commands.memecommand.MemeArchivesCommand;
import seedu.weme.logic.commands.memecommand.MemeClearCommand;
import seedu.weme.logic.commands.memecommand.MemeDeleteCommand;
import seedu.weme.logic.commands.memecommand.MemeDislikeCommand;
import seedu.weme.logic.commands.memecommand.MemeEditCommand;
import seedu.weme.logic.commands.memecommand.MemeFindCommand;
import seedu.weme.logic.commands.memecommand.MemeLikeCommand;
import seedu.weme.logic.commands.memecommand.MemeListCommand;
import seedu.weme.logic.commands.memecommand.MemeStageCommand;
import seedu.weme.logic.commands.memecommand.MemeUnarchiveCommand;
import seedu.weme.logic.parser.commandparser.memecommandparser.MemeAddCommandParser;
import seedu.weme.logic.parser.commandparser.memecommandparser.MemeArchiveCommandParser;
import seedu.weme.logic.parser.commandparser.memecommandparser.MemeDeleteCommandParser;
import seedu.weme.logic.parser.commandparser.memecommandparser.MemeDislikeCommandParser;
import seedu.weme.logic.parser.commandparser.memecommandparser.MemeEditCommandParser;
import seedu.weme.logic.parser.commandparser.memecommandparser.MemeFindCommandParser;
import seedu.weme.logic.parser.commandparser.memecommandparser.MemeLikeCommandParser;
import seedu.weme.logic.parser.commandparser.memecommandparser.MemeStageCommandParser;
import seedu.weme.logic.parser.commandparser.memecommandparser.MemeUnarchiveCommandParser;
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

        final String commandWord = matcher.group(COMMAND_WORD);
        final String arguments = matcher.group(ARGUMENTS);
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

        case MemeLikeCommand.COMMAND_WORD:
            return new MemeLikeCommandParser().parse(arguments);

        case MemeDislikeCommand.COMMAND_WORD:
            return new MemeDislikeCommandParser().parse(arguments);

        case MemeStageCommand.COMMAND_WORD:
            return new MemeStageCommandParser().parse(arguments);

        case MemeArchivesCommand.COMMAND_WORD:
            return new MemeArchivesCommand();

        case MemeArchiveCommand.COMMAND_WORD:
            return new MemeArchiveCommandParser().parse(arguments);

        case MemeUnarchiveCommand.COMMAND_WORD:
            return new MemeUnarchiveCommandParser().parse(arguments);

        default:
            return super.parseCommand(userInput);
        }
    }
}
