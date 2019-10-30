//@@author shutingy
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_THEME;
import static seedu.address.logic.commands.SetThemeCommand.BLUETHEME;
import static seedu.address.logic.commands.SetThemeCommand.DARKTHEME;
import static seedu.address.logic.commands.SetThemeCommand.HACKERTHEME;
import static seedu.address.logic.commands.SetThemeCommand.LIGHTTHEME;
import static seedu.address.logic.commands.SetThemeCommand.NUSTHEME;
import static seedu.address.logic.commands.SetThemeCommand.PINKTHEME;

import seedu.address.logic.commands.SetThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SetThemeCommand object
 */
public class SetThemeCommandParser implements Parser<SetThemeCommand> {

    @Override
    public SetThemeCommand parse(String userInput) throws ParseException {
        String styleSheet = null;
        String color = null;
        String trimedInput = userInput.trim();
        if (trimedInput.equalsIgnoreCase("light")) {
            styleSheet = LIGHTTHEME;
        } else if (trimedInput.equalsIgnoreCase("dark")) {
            styleSheet = DARKTHEME;
        } else if (trimedInput.equalsIgnoreCase("pink")) {
            styleSheet = PINKTHEME;
        } else if (trimedInput.equalsIgnoreCase("blue")) {
            styleSheet = BLUETHEME;
        } else if (trimedInput.equalsIgnoreCase("hacker")) {
            styleSheet = HACKERTHEME;
        } else if (trimedInput.equalsIgnoreCase("nus")) {
            styleSheet = NUSTHEME;
        }
        if (styleSheet == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_THEME));
        }

        return new SetThemeCommand(styleSheet);
    }
}
