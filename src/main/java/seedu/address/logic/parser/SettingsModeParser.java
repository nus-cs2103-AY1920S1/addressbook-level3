package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.app.AddCommand;
import seedu.address.logic.commands.app.ClearCommand;
import seedu.address.logic.commands.app.DeleteCommand;
import seedu.address.logic.commands.app.EditCommand;
import seedu.address.logic.commands.app.ExitCommand;
import seedu.address.logic.commands.app.FindCommand;
import seedu.address.logic.commands.app.HelpCommand;
import seedu.address.logic.commands.app.ListCommand;
import seedu.address.logic.commands.game.GuessCommand;
import seedu.address.logic.commands.game.SkipCommand;
import seedu.address.logic.commands.game.StopCommand;
import seedu.address.logic.commands.settings.DifficultyCommand;
import seedu.address.logic.commands.switches.BankCommand;
import seedu.address.logic.commands.switches.HomeCommand;
import seedu.address.logic.commands.switches.LoadScreenCommand;
import seedu.address.logic.commands.switches.StartCommand;
import seedu.address.logic.commands.switches.SwitchToSettingsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class SettingsModeParser extends ModeParser {

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
    @Override
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

        case DifficultyCommand.COMMAND_WORD:
            return new DifficultyCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
