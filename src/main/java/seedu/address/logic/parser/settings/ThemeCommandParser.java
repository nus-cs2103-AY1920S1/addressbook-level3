package seedu.address.logic.parser.settings;

import seedu.address.logic.commands.game.GuessCommand;
import seedu.address.logic.commands.settings.HintsCommand;
import seedu.address.logic.commands.settings.ThemeCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appsettings.ThemeEnum;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new GuessCommand object
 */
public class ThemeCommandParser implements Parser<ThemeCommand> {

    @Override
    public ThemeCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GuessCommand.MESSAGE_USAGE));
        }
        ThemeEnum theme;
        switch (trimmedArgs.toUpperCase()) {
        case "LIGHT":
            theme = ThemeEnum.LIGHT;
            break;
        case "DARK":
            theme = ThemeEnum.DARK;
            break;
        default:
            throw new ParseException("No such option: " + trimmedArgs.toUpperCase());
        }
        return new ThemeCommand(theme);
    }
}
