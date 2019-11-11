package seedu.address.logic.parser.gui;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.GUI_THEME_PATTERN;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.commons.core.GuiTheme;
import seedu.address.logic.commands.gui.ChangeThemeCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ChangeThemeCommand object
 */
public class ChangeThemeCommandParser implements Parser<ChangeThemeCommand> {

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
    public ChangeThemeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, GUI_THEME_PATTERN);

        if (!arePatternsPresent(argMultimap, GUI_THEME_PATTERN)
                || argMultimap.getNumberOfArgsForPattern(GUI_THEME_PATTERN) != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangeThemeCommand.MESSAGE_USAGE));
        }
        String input = argMultimap.getValue(GUI_THEME_PATTERN).get().toLowerCase();
        for (GuiTheme guiTheme : GuiTheme.values()) {
            if (guiTheme.getModeName().equals(input)) {
                return new ChangeThemeCommand(guiTheme);
            }
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            ChangeThemeCommand.MESSAGE_USAGE));
    }
}
