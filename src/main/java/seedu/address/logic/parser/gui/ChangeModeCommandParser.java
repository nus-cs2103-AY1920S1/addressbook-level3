package seedu.address.logic.parser.gui;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.GUI_MODE_PATTERN;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.commons.core.GuiMode;
import seedu.address.logic.commands.gui.ChangeModeCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ChangeModeCommand object
 */
public class ChangeModeCommandParser implements Parser<ChangeModeCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePatternsPresent(ArgumentMultimap argumentMultimap, Pattern... patterns) {
        return Stream.of(patterns).allMatch(pattern -> argumentMultimap.getValue(pattern).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the CollapseCommand
     * and returns an CollapseCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeModeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, GUI_MODE_PATTERN);

        if (!arePatternsPresent(argMultimap, GUI_MODE_PATTERN)
                || argMultimap.getNumberOfArgsForPattern(GUI_MODE_PATTERN) != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangeModeCommand.MESSAGE_USAGE));
        }
        String input = argMultimap.getValue(GUI_MODE_PATTERN).get().toLowerCase();
        for (GuiMode guiMode : GuiMode.values()) {
            if (guiMode.getModeName().equals(input)) {
                return new ChangeModeCommand(guiMode);
            }
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            ChangeModeCommand.MESSAGE_USAGE));
    }
}
