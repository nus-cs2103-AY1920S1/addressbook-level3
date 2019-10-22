package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.appcommands.HelpCommand;
import seedu.address.logic.commands.loadcommands.CreateCommand;
import seedu.address.logic.commands.loadcommands.ExportCommand;
import seedu.address.logic.commands.loadcommands.ImportCommand;
import seedu.address.logic.commands.loadcommands.RemoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class LoadModeParser extends ModeParser {

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

        /*
        Step 10.
        Additional commands to be done
        Have 2 separate user modes: Game, Normal
         */

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case CreateCommand.COMMAND_WORD:
            return new CreateCommandParser().parse(arguments);

        case RemoveCommand.COMMAND_WORD:
            return new RemoveCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
